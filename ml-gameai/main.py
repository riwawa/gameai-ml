import pandas as pd
from sklearn.cluster import KMeans
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import silhouette_score
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

# ==============================
# Carregar dados
# ==============================
try:
    df = pd.read_csv("dados.csv")
    print("Dataset carregado com sucesso!\n")
except FileNotFoundError:
    print("Erro: dados.csv não encontrado.")
    exit()

print(df.head())

# ==============================
# Selecionar features
# ==============================
features = ["agressividade", "exploracao", "cautela"]
X = df[features]

# ==============================
# Normalizar dados
# ==============================
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# ==============================
# Treinar modelo
# ==============================
kmeans = KMeans(n_clusters=3, random_state=42)
kmeans.fit(X_scaled)

# adicionar clusters
df["cluster"] = kmeans.labels_

print("\nDados com clusters:")
print(df.head())

# ==============================
# Analisar clusters
# ==============================
print("\nCentros dos clusters (normalizados):")
for i, centro in enumerate(kmeans.cluster_centers_):
    print(f"Cluster {i}: {centro}")

# ==============================
# Avaliação do modelo
# ==============================
score = silhouette_score(X_scaled, kmeans.labels_)
print(f"\nSilhouette Score: {score:.3f}")

# ==============================
# Visualização 3D
# ==============================
fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')

ax.scatter(
    df["agressividade"],
    df["exploracao"],
    df["cautela"],
    c=df["cluster"]
)

ax.set_xlabel("Agressividade")
ax.set_ylabel("Exploração")
ax.set_zlabel("Cautela")
plt.title("Clusters de Jogadores")

plt.show()

# ==============================
# Prever novo jogador
# ==============================
novo_jogador = [[0.7, 0.2, 0.3]]

# ⚠️ precisa normalizar também!
novo_jogador_scaled = scaler.transform(novo_jogador)

cluster_pred = kmeans.predict(novo_jogador_scaled)

print("\nNovo jogador pertence ao cluster:", cluster_pred[0])

# ==============================
# Estatísticas
# ==============================
print("\nResumo estatístico:")
print(df.describe())