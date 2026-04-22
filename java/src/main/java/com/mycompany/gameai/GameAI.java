package com.mycompany.gameai;
import com.mycompany.gameai.Player;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GameAI {

    public static void main(String[] args) {

        Random random = new Random();

        try {
            FileWriter writer = new FileWriter("dados.csv", false);

            // cabeçalho
            writer.append("agressividade,exploracao,cautela\n");

            // quantidade por tipo
            int quantidade = 50;

            // AGRESSIVOS
            for (int i = 0; i < quantidade; i++) {
                Player player = new Player();
                simularAgressivo(player, random);
                salvarPlayer(writer, player);
            }

            // EXPLORADORES
            for (int i = 0; i < quantidade; i++) {
                Player player = new Player();
                simularExplorador(player, random);
                salvarPlayer(writer, player);
            }

            // DEFENSIVOS
            for (int i = 0; i < quantidade; i++) {
                Player player = new Player();
                simularDefensivo(player, random);
                salvarPlayer(writer, player);
            }

            writer.flush();
            writer.close();

            System.out.println("Dataset gerado com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ==============================
    // SIMULAÇÕES
    // ==============================

    public static void simularAgressivo(Player player, Random random) {
        for (int i = 0; i < 100; i++) {

            int chance = random.nextInt(100);

            if (chance < 70) {
                player.atacar();
            } else if (chance < 90) {
                player.mover(random.nextInt(10) + 1);
            } else {
                player.receberDano(random.nextInt(5) + 1);
            }
        }
    }

    public static void simularExplorador(Player player, Random random) {
        for (int i = 0; i < 100; i++) {

            int chance = random.nextInt(100);

            if (chance < 20) {
                player.atacar();
            } else if (chance < 70) {
                player.mover(random.nextInt(10) + 1);
            } else {
                player.receberDano(random.nextInt(5) + 1);
            }
        }
    }

    public static void simularDefensivo(Player player, Random random) {
        for (int i = 0; i < 100; i++) {

            int chance = random.nextInt(100);

            if (chance < 20) {
                player.atacar();
            } else if (chance < 50) {
                player.mover(random.nextInt(10) + 1);
            } else {
                player.receberDano(random.nextInt(2) + 1); // menos dano
            }
        }
    }

    
    // ==============================
    // SALVAR csv
    // ==============================

    public static void salvarPlayer(FileWriter writer, Player player) throws IOException {
        double[] v = player.gerarVetorML();

        writer.append(v[0] + "," + v[1] + "," + v[2] + "\n");
    }
}