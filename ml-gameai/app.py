from fastapi import FastAPI
import joblib
import numpy as np

app = FastAPI()

# carregar modelo UMA VEZ
kmeans = joblib.load("kmeans_model.pkl")
scaler = joblib.load("scaler.pkl")

tipos = {
    0: "agressivo",
    1: "explorador",
    2: "defensivo"
}

@app.post("/predict")
def predict(data: dict):
    vetor = np.array([[
        data["agressividade"],
        data["exploracao"],
        data["cautela"]
    ]])

    # normalizar
    vetor_scaled = scaler.transform(vetor)

    cluster = int(kmeans.predict(vetor_scaled)[0])

    return {
        "cluster": cluster,
        "tipo": tipos[cluster]
    }