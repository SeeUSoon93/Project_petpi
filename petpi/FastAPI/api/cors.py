from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

def create_app():
    app = FastAPI()
    origins = ["http://localhost:9090", "http://localhost:9091"]
    
    app.add_middleware(
        CORSMiddleware,
        allow_origins=["*"], # 나중에 구체적인 주소로 수정
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"],
    )
    
    return app
