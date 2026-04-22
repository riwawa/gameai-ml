import pandas as pd
from sklearn.cluster import KMeans
from sklearn.preprocessing import StandardScaler
import joblib

# carregar dados
df = pd.read_csv("dados.csv")

features = ["agressividade", "exploracao", "cautela"]
X = df[features]

# normalizar
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# treinar modelo
kmeans = KMeans(n_clusters=3, random_state=42)
kmeans.fit(X_scaled)

# salvar modelo + scaler
joblib.dump(kmeans, "kmeans_model.pkl")
joblib.dump(scaler, "scaler.pkl")

print("Modelo salvo com sucesso!")
