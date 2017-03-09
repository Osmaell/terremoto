package br.com.osmael.terremoto.entity;

/**
 * Created by root on 07/03/17.
 */

public class Terremoto {

    /** Magnitude do terremoto (tamanho) */
    private double magnitude;

    /** É a localização da cidade do terremoto */
    private String localizacao;

    /** Tempo em mili-segundos */
    private long tempoEmMilisegundos;

    public Terremoto(double magnitude, String localizacao, long tempoEmMilisegundos) {
        this.magnitude = magnitude;
        this.localizacao = localizacao;
        this.tempoEmMilisegundos = tempoEmMilisegundos;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public long getTempoEmMilisegundos() {
        return tempoEmMilisegundos;
    }

    @Override
    public String toString() {
        return this.localizacao;
    }

}