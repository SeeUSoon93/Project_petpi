import numpy as np
import tensorflow as tf
from tensorflow.keras.preprocessing.image import img_to_array, load_img
from tensorflow.keras.applications.inception_v3 import preprocess_input
from tensorflow.image import resize_with_pad as resize
from io import BytesIO
from api.utils import encode_image_to_base64
from api.prediction_models import unet_models, inception_models, class_labels
import cv2

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

        prediction_overlay = img_array.copy()
        prediction_overlay[consensus_mask == 0] = [0, 0, 0]
        inception_size = (299, 299)
        prediction_overlay_resized = resize(prediction_overlay, inception_size[0], inception_size[1])
        prediction_overlay_preprocessed = preprocess_input(prediction_overlay_resized)
        prediction_overlay_batch = np.expand_dims(prediction_overlay_preprocessed, axis=0)

        all_predictions = []

        for model in inception_models:
            prediction_probabilities = model.predict(prediction_overlay_batch)
            all_predictions.append(prediction_probabilities)

        average_predictions = np.mean(all_predictions, axis=0)
        predicted_labels = np.argmax(average_predictions, axis=1)
        print(predicted_labels)
        predicted_class_label = [class_labels[index] for index in predicted_labels]

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
             "overlay": encode_image_to_base64(overlay_with_contours),
             "img": encode_image_to_base64(img_array)
             }
    except Exception as e:
        print(f"An error occurred: {str(e)}")        
        raise e