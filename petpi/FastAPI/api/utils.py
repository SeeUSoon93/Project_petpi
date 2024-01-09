import base64
from io import BytesIO
import tensorflow as tf

def encode_image_to_base64(image_array):
    buffered = BytesIO()
    image = tf.keras.preprocessing.image.array_to_img(image_array)
    image.save(buffered, format="JPEG")
    return base64.b64encode(buffered.getvalue()).decode('utf-8')
