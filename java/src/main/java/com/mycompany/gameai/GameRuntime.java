package com.mycompany.gameai;

import java.util.Random;
import com.google.gson.Gson;

public class GameRuntime {

    public static void main(String[] args) {

        Random random = new Random();
        Gson gson = new Gson();

        Player player = new Player();

        // iniciar estado do jogo
        GameAIController.reset();

        int tick = 0;

        while (true) {

            tick++;
            if (tick > 200) break;
            // ==============================
            // SIMULAR AÇÕES DO PLAYER
            // ==============================
            int acao = random.nextInt(3);

            if (acao == 0) {
                player.atacar();
                System.out.println("Player atacou");
            } else if (acao == 1) {
                int distancia = random.nextInt(10) + 1;
                player.mover(distancia);
                System.out.println("Player moveu " + distancia);
            } else {
                int dano = random.nextInt(5) + 1;
                player.receberDano(dano);
                System.out.println("Player recebeu dano: " + dano);
            }

            // ==============================
            // A CADA X TICKS → IA ATUA
            // ==============================
            if (tick % 20 == 0) {

                System.out.println("\n=== ANALISANDO COMPORTAMENTO ===");

                double[] vetor = player.gerarVetorML();

                String resposta = MLClient.enviarDados(vetor);

                MLResponse obj = gson.fromJson(resposta, MLResponse.class);

                System.out.println("Tipo detectado: " + obj.tipo);

                // IA ajusta o jogo
                GameAIController.ajustar(obj.tipo);
                player.resetStats();
            }

            // ==============================
            // ⏱️ Pausa (simula tempo real)
            // ==============================
            try {
                Thread.sleep(500); // 0.5 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}