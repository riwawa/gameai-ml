package com.mycompany.gameai;

public class Player {

    private String nome;
    private int life = 100;
    private int position;
    private int speed;

    // estatísticas para ML
    private int ataquesRealizados;
    private int distanciaPercorrida;
    private int danoRecebido;
    private int tempoDeJogo;

    // ==============================
    // AÇÕES
    // ==============================

    public void atacar() {
        this.ataquesRealizados++;
        this.tempoDeJogo++;
    }

    public void mover(int distancia) {
        this.distanciaPercorrida += distancia;
        this.tempoDeJogo++;
    }

    public void receberDano(int dano) {
        this.danoRecebido += dano;
        this.life -= dano;

        if (this.life < 0) {
            this.life = 0; // evita negativo
        }

        this.tempoDeJogo++;
    }

    // ==============================
    // FEATURES ML
    // ==============================

    public double calcularAgressividade() {
        if (tempoDeJogo == 0) return 0;
        return (double) ataquesRealizados / tempoDeJogo;
    }

    public double calcularExploracao() {
        if (tempoDeJogo == 0) return 0;
        return (double) distanciaPercorrida / tempoDeJogo;
    }

    public double calcularCautela() {
        int vidaMax = 100;
        return 1.0 - ((double) danoRecebido / vidaMax);
    }

    public double[] gerarVetorML() {
        return new double[]{
            calcularAgressividade(),
            calcularExploracao(),
            calcularCautela()
        };
    }

    // ==============================
    // RESET DE ESTATÍSTICAS
    // ==============================

    public void resetStats() {
        this.ataquesRealizados = 0;
        this.distanciaPercorrida = 0;
        this.danoRecebido = 0;
        this.tempoDeJogo = 0;
    }
}