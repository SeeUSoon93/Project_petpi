from fastapi import FastAPI, WebSocket
from fastapi.responses import JSONResponse, FileResponse
from fastapi.middleware.cors import CORSMiddleware

# python3 -m uvicorn main:app --reload --host 0.0.0.0 --port 9091

app = FastAPI()
# CORS설정
origins = ["http://localhost:9090",
           "http://localhost:9091"]
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/")
def go_main():
    return FileResponse('./static/index.html')