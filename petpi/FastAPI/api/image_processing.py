import numpy as np
import tensorflow as tf
from tensorflow.keras.preprocessing.image import img_to_array, load_img
from tensorflow.keras.applications.inception_v3 import preprocess_input
from tensorflow.image import resize_with_pad as resize
from io import BytesIO
from utils import encode_image_to_base64
from prediction_models import unet_models, inception_model, class_labels

async def predict_image(file):
    try:
        contents = await file.read()
        img = load_img(BytesIO(contents), target_size=(256, 256))
    
        img_array = img_to_array(img)
        img_array = img_array / 255.0
        img_batch = np.expand_dims(img_array, axis=0)
        masks = [model.predict(img_batch)[0].squeeze() for model in unet_models]
        masks = np.array(masks)
        threshold = 2 / len(unet_models)
        consensus_mask = np.where(np.sum(masks > 0.5, axis=0) >= threshold, 1, 0)

        overlay = img_array.copy()
        overlay[consensus_mask == 0] = [0, 0, 0]
        inception_size = (299, 299)
        overlay_resized = resize(overlay, inception_size[0], inception_size[1])
        overlay_preprocessed = preprocess_input(overlay_resized)
        overlay_batch = np.expand_dims(overlay_preprocessed, axis=0)

        result = inception_model.predict(overlay_batch)
        percentages = result[0] * 100

        for i, percentage in enumerate(percentages):
            print(f"Class {i}: {percentage:.2f}%")

        predicted_label = np.argmax(result, axis=1)[0]
        predicted_class_label = class_labels.get(predicted_label, "Unknown")    

        return {
             "predicted_class_label": predicted_class_label,
             "overlay": encode_image_to_base64(overlay),
             "img": encode_image_to_base64(img_array)
             }
    except Exception as e:
        print(f"An error occurred: {str(e)}")        
        raise e