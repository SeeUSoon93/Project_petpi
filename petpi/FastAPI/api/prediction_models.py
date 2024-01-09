from tensorflow.keras.models import load_model

unet_model_path = ['model/unet01.h5',
              'model/unet02.h5',
              'model/unet03.h5'
              ]

inception_model_path = ['model/inceptionResNetV2_model_11.h5',
              'model/inceptionResNetV2_model_07.h5',
              'model/inceptionResNetV2_model_10.h5'
              ]

unet_models = [load_model(path)for path in unet_model_path]
inception_models = [load_model(path)for path in inception_model_path]

class_labels = {
        0: 'A1_구진/플라크',
        1: 'A2_비듬/각질/상피성잔고리',
        2: 'A3_태선화/과다색소침착',
        3: 'A4_농포/여드름',
        4: 'A5_미란/궤양',
        5: 'A6_결정/종괴'
    }