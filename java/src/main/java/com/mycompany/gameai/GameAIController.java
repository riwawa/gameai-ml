package com.mycompany.gameai;

public class GameAIController {

    // ==============================
    // Estado do jogo (valores atuais)
    // ==============================
    public static double enemyDamage = 10;
    public static double spawnRate = 1.0;
    public static double mapSize = 100;
    public static double playerRegen = 1.0;

    // ==============================
    // Valores base (referência)
    // ==============================
    private static final double BASE_ENEMY_DAMAGE = 10;
    private static final double BASE_SPAWN_RATE = 1.0;
    private static final double BASE_MAP_SIZE = 100;
    private static final double BASE_PLAYER_REGEN = 1.0;

    // ==============================
    // Controle de comportamento
    // ==============================
    private static String ultimoTipo = "";

    // ==============================
    // Reset do jogo
    // ==============================
    public static void reset() {
        enemyDamage = BASE_ENEMY_DAMAGE;
        spawnRate = BASE_SPAWN_RATE;
        mapSize = BASE_MAP_SIZE;
        playerRegen = BASE_PLAYER_REGEN;
        ultimoTipo = "";
    }

    // ==============================
    // Ajuste de dificuldade (IA)
    // ==============================
    public static void ajustar(String tipo) {

        // detectar mudança de comportamento
        if (!tipo.equals(ultimoTipo)) {
            System.out.println("\n[IA] Mudança de comportamento detectada: " + tipo);
            ultimoTipo = tipo;
        }

        switch (tipo) {

            case "agressivo":
                enemyDamage = suavizar(enemyDamage, BASE_ENEMY_DAMAGE * 1.3, 50);
                spawnRate = suavizar(spawnRate, BASE_SPAWN_RATE + 0.5, 5);
                break;

            case "explorador":
                mapSize = suavizar(mapSize, BASE_MAP_SIZE * 1.5, 500);
                spawnRate = suavizar(spawnRate, BASE_SPAWN_RATE + 0.2, 5);
                break;

            case "defensivo":
                enemyDamage = suavizar(enemyDamage, BASE_ENEMY_DAMAGE * 0.7, 50);
                playerRegen = suavizar(playerRegen, BASE_PLAYER_REGEN + 0.5, 10);
                break;

            default:
                System.out.println("[IA] Tipo desconhecido");
                break;
        }

        mostrarEstado();
    }

    // ==============================
    // Função de suavização
    // ==============================
    private static double suavizar(double atual, double alvo, double limite) {
        double novo = atual * 0.9 + alvo * 0.1;
        return Math.min(novo, limite);
    }

    // ==============================
    // Debug do estado do jogo
    // ==============================
    public static void mostrarEstado() {
        System.out.println("\n=== ESTADO DO JOGO ===");
        System.out.println("Dano inimigo: " + String.format("%.2f", enemyDamage));
        System.out.println("Spawn rate: " + String.format("%.2f", spawnRate));
        System.out.println("Tamanho do mapa: " + String.format("%.2f", mapSize));
        System.out.println("Regeneração do jogador: " + String.format("%.2f", playerRegen));
        System.out.println("======================\n");
    }
}