import cv2
import numpy as np
import base64
import tensorflow as tf

from PIL import Image
from io import BytesIO

from tensorflow.keras.preprocessing.image import img_to_array

def preprocess_image(contents):        
        img = Image.open(BytesIO(contents))
        img = img.resize((256, 256))
    
        img_array = img_to_array(img)
        img_array = img_array / 255.0
        img_batch = np.expand_dims(img_array, axis=0)

        return img_array, img_batch

def draw_mask_on_image(img_array, consensus_mask):
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
        
        return overlay_with_contours

def encode_image_to_base64(image_array):
    buffered = BytesIO()
    image = tf.keras.preprocessing.image.array_to_img(image_array)
    image.save(buffered, format="JPEG")
    return base64.b64encode(buffered.getvalue()).decode('utf-8')