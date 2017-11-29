package com.example.fernando.menudeslisante.beans;

import android.content.Context;

import com.example.fernando.menudeslisante.bd.BDProva_Questao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando on 01/06/2017.
 */

public class Prova {
    private int prvCodigo;
    private String prvNome;
    private int prv_proCodigo;

    public Prova(int prvCodigo, String prvNome, int prv_proCodigo) {
        this.prvCodigo = prvCodigo;
        this.prvNome = prvNome;
        this.prv_proCodigo = prv_proCodigo;
    }

    public Prova() {
    }

    public int getPrv_proCodigo() {
        return prv_proCodigo;
    }

    public void setPrv_proCodigo(int prv_proCodigo) {
        this.prv_proCodigo = prv_proCodigo;
    }

    public int getprvCodigo() {
        return prvCodigo;
    }

    public void setprvCodigo(int prvCodigo) {
        this.prvCodigo = prvCodigo;
    }

    public String getprvNome() {
        return prvNome;
    }

    public void setprvNome(String prvNome) {
        this.prvNome = prvNome;
    }

    public int numeroQuestoes(Context ctx){
        List<Prova_Questao> prova_questaoList = new ArrayList<>();
        BDProva_Questao bdProva_questao = new BDProva_Questao(ctx);
        prova_questaoList = bdProva_questao.getAllSql();
        int cont = 0;

        for (Prova_Questao provaq: prova_questaoList) {
            if (provaq.getPrq_prvCodigo() == prvCodigo){
                cont++;
            }
        }

        return cont;
    }

    @Override
    public String toString() {
        return "Prova{" +
                "prvCodigo=" + prvCodigo +
                ", prvNome='" + prvNome + '\'' +
                '}';
    }
}
