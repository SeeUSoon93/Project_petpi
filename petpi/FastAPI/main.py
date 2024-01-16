from fastapi import UploadFile, File
from fastapi.responses import JSONResponse, FileResponse

from api.api_handle import predict_image
from api.cors import create_app
# python3 -m uvicorn main:app --reload --host 0.0.0.0 --port 9091

app = create_app()

@app.get("/main")
def go_main():
    return FileResponse('index.html')

@app.post("/predict")
async def predict(file: UploadFile = File(...)):
    try:        
        result = await predict_image(file)
        return result
    except Exception as e:
        print(f"An error occurred: {str(e)}")
        return JSONResponse(status_code=500, content={"message": "500error"})