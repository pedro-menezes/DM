package com.example.fernando.menudeslisante.beans;

/**
 * Created by fernando on 01/06/2017.
 */
public class Alternativa {
    private int altCodigo;
    private String altEnunciado;
    private int altCorreta;
    private int alt_queCodigo;

    public int getAltCodigo() {
        return altCodigo;
    }

    public void setAltCodigo(int altCodigo) {
        this.altCodigo = altCodigo;
    }

    public String getAltEnunciado() {
        return altEnunciado;
    }

    public void setAltEnunciado(String altEnunciado) {
        this.altEnunciado = altEnunciado;
    }

    public int getAltCorreta() {
        return altCorreta;
    }

    public void setAltCorreta(int altCorreta) {
        this.altCorreta = altCorreta;
    }

    public int getAlt_queCodigo() {
        return alt_queCodigo;
    }

    public void setAlt_queCodigo(int alt_queCodigo) {
        this.alt_queCodigo = alt_queCodigo;
    }

    @Override
    public String toString() {
        return "altstao{" +
                "altCodigo=" + altCodigo +
                ", altEnunciado='" + altEnunciado + '\'' +
                ", alt_queCodigo='" + alt_queCodigo + '\'' +
                ", altCorreta='" + altCorreta + '\'' +
                '}';
    }
}
