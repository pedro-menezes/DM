package com.example.fernando.menudeslisante.beans;

/**
 * Created by fernando on 01/06/2017.
 */
public class Tema {
    private int temCodigo;
    private String temNome;

    public Tema(int temCodigo, String temNome) {
        this.temCodigo = temCodigo;
        this.temNome = temNome;
    }

    public Tema() {
    }

    public int gettemCodigo() {
        return temCodigo;
    }

    public void settemCodigo(int temCodigo) {
        this.temCodigo = temCodigo;
    }

    public String gettemNome() {
        return temNome;
    }

    public void settemNome(String temNome) {
        this.temNome = temNome;
    }

    @Override
    public String toString() {
        return "BDTema{" +
                "temCodigo=" + temCodigo +
                ", temNome='" + temNome + '\'' +
                '}';
    }
}
