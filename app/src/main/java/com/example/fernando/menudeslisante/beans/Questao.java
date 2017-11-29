package com.example.fernando.menudeslisante.beans;

/**
 * Created by fernando on 01/06/2017.
 */
public class Questao {
    private int queCodigo;
    private String queEnunciado;
    private int que_temCodigo;

    public int getque_temCodigo() {
        return que_temCodigo;
    }

    public void setque_temCodigo(int que_temCodigo) {
        this.que_temCodigo = que_temCodigo;
    }

    public int getqueCodigo() {
        return queCodigo;
    }

    public void setqueCodigo(int queCodigo) {
        this.queCodigo = queCodigo;
    }

    public String getqueEnunciado() {
        return queEnunciado;
    }

    public void setqueEnunciado(String queEnunciado) {
        this.queEnunciado = queEnunciado;
    }

    @Override
    public String toString() {
        return "Questao{" +
                "queCodigo=" + queCodigo +
                ", queEnunciado='" + queEnunciado + '\'' +
                ", que_temCodigo='" + que_temCodigo + '\''+
        '}';
    }
}
