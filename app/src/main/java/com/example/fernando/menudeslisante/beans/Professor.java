package com.example.fernando.menudeslisante.beans;

/**
 * Created by fernando on 01/06/2017.
 */
public class Professor {
    private int proCodigo;
    private String proNome;
    private String proSenha;
    private String proEmail;

    public Professor(int proCodigo, String proNome, String proSenha, String proEmail) {
        this.proCodigo = proCodigo;
        this.proNome = proNome;
        this.proSenha = proSenha;
        this.proEmail = proEmail;
    }

    public Professor() {
    }

    public int getProCodigo() {
        return proCodigo;
    }

    public void setProCodigo(int proCodigo) {
        this.proCodigo = proCodigo;
    }

    public String getProNome() {
        return proNome;
    }

    public void setProNome(String proNome) {
        this.proNome = proNome;
    }

    public String getProSenha() {
        return proSenha;
    }

    public void setProSenha(String proSenha) {
        this.proSenha = proSenha;
    }

    public String getProEmail() {
        return proEmail;
    }

    public void setProEmail(String proEmail) {
        this.proEmail = proEmail;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "pesCodigo=" + proCodigo +
                ", pesNome='" + proNome +
                ", pesNome='" + proEmail +
                ", pesNome='" + proSenha + '\'' +
                '}';
    }
}
