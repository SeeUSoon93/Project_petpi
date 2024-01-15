import numpy as np
import tensorflow as tf
from tensorflow.image import resize_with_pad as resize
from tensorflow.keras.applications.inception_resnet_v2 import preprocess_input
from tensorflow.keras.models import load_model

unet_model_path = ['model/unet01.h5', 'model/unet02.h5', 'model/unet03.h5']
unet_models = [load_model(path)for path in unet_model_path]

inception_model = load_model('model/multi_136_02.h5')

class_labels = {
        0: 'A1_구진/플라크',
        1: 'A3_태선화/과다색소침착',
        2: 'A6_결정/종괴'
    }

def predict_with_unet(img_batch):
        masks = [model.predict(img_batch)[0].squeeze() for model in unet_models]
        masks = np.array(masks)
        threshold = 2.5 / len(unet_models)
        consensus_mask = np.where(np.sum(masks > 0.5, axis=0) >= threshold, 1, 0)

        return consensus_mask

def predict_with_inception(img_array, consensus_mask):        
        y_indices, x_indices = np.where(consensus_mask == 1)
        if len(y_indices) == 0 or len(x_indices) == 0:
            return {"error": "No object detected"}

        x_min, x_max = x_indices.min(), x_indices.max()
        y_min, y_max = y_indices.min(), y_indices.max()
        cropped_img = img_array[y_min:y_max, x_min:x_max]
        resized_img = resize(cropped_img, 299, 299, method=tf.image.ResizeMethod.NEAREST_NEIGHBOR)
        resized_img = np.array(resized_img)

        img_array = tf.image.resize(img_array, [299, 299])
        img_array = np.array(img_array)
        inception_input = preprocess_input(np.expand_dims(img_array, axis=0))
        
        prediction_probabilities = inception_model.predict(inception_input)
        print(prediction_probabilities)
        predicted_label = np.argmax(prediction_probabilities, axis=1)[0]
        predicted_class_label = int(predicted_label)
        predicted_class_label = class_labels[predicted_class_label]
        print(predicted_class_label)
        return resized_img, predicted_class_label