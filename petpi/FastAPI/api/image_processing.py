import numpy as np
import tensorflow as tf
from PIL import Image
from tensorflow.keras.preprocessing.image import img_to_array
from tensorflow.keras.applications.inception_resnet_v2 import preprocess_input
from tensorflow.image import resize_with_pad as resize
from io import BytesIO
from api.utils import encode_image_to_base64
from api.prediction_models import unet_models, inception_model, class_labels
import cv2

async def predict_image(file):
    try:
        contents = await file.read()
        img = Image.open(BytesIO(contents))
        img = img.resize((256, 256))
    
        img_array = img_to_array(img)
        img_array = img_array / 255.0
        img_batch = np.expand_dims(img_array, axis=0)
        
        masks = [model.predict(img_batch)[0].squeeze() for model in unet_models]
        masks = np.array(masks)
        threshold = 2.5 / len(unet_models)
        consensus_mask = np.where(np.sum(masks > 0.5, axis=0) >= threshold, 1, 0)
        
        # Inception_Model에 넣을 이미지 처리
        y_indices, x_indices = np.where(consensus_mask == 1)
        if len(y_indices) == 0 or len(x_indices) == 0:
            return {"error": "No object detected"}

        x_min, x_max = x_indices.min(), x_indices.max()
        y_min, y_max = y_indices.min(), y_indices.max()
        cropped_img = img_array[y_min:y_max, x_min:x_max]
        resized_img = resize(cropped_img, 299, 299, method=tf.image.ResizeMethod.NEAREST_NEIGHBOR)
        resized_img = np.array(resized_img)

        inception_input = preprocess_input(np.expand_dims(resized_img, axis=0))
        
        prediction_probabilities = inception_model.predict(inception_input)
        print(prediction_probabilities)
        predicted_label = np.argmax(prediction_probabilities, axis=1)[0]
        predicted_class_label = int(predicted_label)

        predicted_class_label = class_labels[predicted_class_label]
        print(predicted_class_label)

        # 이미지 위에 마스크 그리기 
        transparent_blue = [0.0, 0.0, 1.0, 0.1]
        contour_color = (255, 0, 0)

        overlay = img_array.copy()
        for i in range(overlay.shape[0]):
            for j in range(overlay.shape[1]):
                if consensus_mask[i, j] == 1:                    
                    overlay[i, j] = overlay[i, j] * (1 - transparent_blue[3]) + np.array(transparent_blue[:3]) * transparent_blue[3]

        mask_cv2 = (consensus_mask * 255).astype(np.uint8)
        contours, _ = cv2.findContours(mask_cv2, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

        overlay_bgr = cv2.cvtColor((overlay * 255).astype(np.uint8), cv2.COLOR_RGB2BGR)
        cv2.drawContours(overlay_bgr, contours, -1, contour_color, 2) 
        overlay_with_contours = cv2.cvtColor(overlay_bgr, cv2.COLOR_BGR2RGB) / 255.0

        return {
             "predicted_class_label": predicted_class_label,
             "overlay": encode_image_to_base64(cropped_img),
             "img": encode_image_to_base64(img_array)
             }
    except Exception as e:
        print(f"An error occurred: {str(e)}")        
        raise e