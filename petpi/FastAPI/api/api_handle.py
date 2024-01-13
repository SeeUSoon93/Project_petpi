from api.img_processing import *
from api.predict import *

async def predict_image(file):
    try:    
        img_array, img_batch = preprocess_image(file)
        consensus_mask = predict_with_unet(img_batch)
        resized_img, predicted_class_label = predict_with_inception(img_array, consensus_mask)
        overlay_with_contours = draw_mask_on_image(img_array, consensus_mask)

        return {
             "predicted_class_label": predicted_class_label,
             "overlay": encode_image_to_base64(resized_img),
             "img": encode_image_to_base64(img_array)
             }
    except Exception as e:
        raise e