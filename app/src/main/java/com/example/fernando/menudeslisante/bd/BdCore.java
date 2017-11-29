package com.example.fernando.menudeslisante.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

/**
 * Neste arquivo estão os códigos de criação de todo o banco de daodos.
 */
public class BdCore extends SQLiteOpenHelper {

    private static final String Name_BD = "ProvaFacil.db";
    private static final int Versao_BD = 1;

    public BdCore(Context context) {
        super(context, Name_BD, null, Versao_BD);
    }

    public boolean checkDataBase(Context context) {
        File database = context.getDatabasePath(Name_BD);
        if (!database.exists()) {
            Log.i("IFMG", "BD não existente");
            return false;
        } else {
            Log.i("IFMG", "BD não existente");
            return true;
        }
    }

    /**
     * Chamando os metodos de criacao das tabelas
     *
     * @param bd
     */
    @Override
    public void onCreate(SQLiteDatabase bd) {
        //neste local são chamados os métodos que criam as tabelas
        criarTabelaProfessor(bd);
        criarTabelaProva(bd);
        criarTabelaTema(bd);
        criarTabelaQuestao(bd);
        criarTabelaAlternativa(bd);
        criarTabelaProva_Questao(bd);
    }

    /**
     * Create Pessoa.
     * Cria tabela no banco local;
     * Chamada no metodo onCreate desta classe.
     *
     * @param bd = Nome do banco de dados.
     */
    public void criarTabelaProfessor(SQLiteDatabase bd) {
        String slqCreateTabelaDefeito = "CREATE TABLE IF NOT EXISTS professor ("
                + "proCodigo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "proNome TEXT,"
                + "proSenha TEXT,"
                + "proEmail TEXT"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaDefeito);
    }

    /**
     * Create telefone.
     * Cria tabela no banco local;
     * Chamada no metodo onCreate desta classe.
     *
     * @param bd = Nome do banco de dados.
     */
    public void criarTabelaProva(SQLiteDatabase bd) {
        String slqCreateTabelaEstabelecimento = "CREATE TABLE IF NOT EXISTS prova("
                + "prvCodigo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "prvNome  TEXT,"
                + "prv_proCodigo INTEGER"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaEstabelecimento);
    }

    public void criarTabelaTema(SQLiteDatabase bd) {
        String slqCreateTabelaEstabelecimento = "CREATE TABLE IF NOT EXISTS tema("
                + "temCodigo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "temNome  TEXT"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaEstabelecimento);
    }

    public void criarTabelaQuestao(SQLiteDatabase bd) {
        String slqCreateTabelaEstabelecimento = "CREATE TABLE IF NOT EXISTS questao("
                + "queCodigo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "queEnunciado  TEXT,"
                + "que_temCodigo INTEGER"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaEstabelecimento);
    }

    public void criarTabelaAlternativa(SQLiteDatabase bd) {
        String slqCreateTabelaEstabelecimento = "CREATE TABLE IF NOT EXISTS alternativa("
                + "altCodigo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "altEnunciado  TEXT,"
                + "altCorreta INTEGER,"
                + "alt_queCodigo INTEGER"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaEstabelecimento);
    }

    public void criarTabelaProva_Questao(SQLiteDatabase bd) {
        String slqCreateTabelaEstabelecimento = "CREATE TABLE IF NOT EXISTS prova_questao("
                + "prqCodigo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "prq_prvCodigo INTEGER NOT NULL,"
                + "prq_queCodigo INTEGER NOT NULL"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaEstabelecimento);
    }

    /**
     * Upgrade banco.
     * Sistema chama automaticamente quando a versão do banco é alterada;
     * Realiza o drop e create das tabelas.
     *
     * @param bd         = Banco de dados;
     * @param oldVersion = Versao antiga do banco;
     * @param newVersion = Nova versao do banco.
     */
    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {

        //TABELA Professor
        upgradeTabelaProfessor(bd, oldVersion, newVersion);
        criarTabelaProfessor(bd);

        //TABELA Prova
        upgradeTabelaProva(bd, oldVersion, newVersion);
        criarTabelaProva(bd);

        //TABELA Tema
        upgradeTabelaTema(bd, oldVersion, newVersion);
        criarTabelaTema(bd);

        //TABELA Questao
        upgradeTabelaQuestao(bd, oldVersion, newVersion);
        criarTabelaQuestao(bd);

        //TABELA Alternativa
        upgradeTabelaAlternativa(bd, oldVersion, newVersion);
        criarTabelaAlternativa(bd);

        //TABELA Prova_questao
        upgradeTabelaProva_questao(bd, oldVersion, newVersion);
        criarTabelaProva_Questao(bd);
    }

    /******************************************************************************
     * UPGRADES DAS TABELAS, COMANDOS PARA DELETÁ-LAS CASO UMA VERSÃO NOVA DO BANCO DE DADOS ESTEJA NO CÓDIGO
     */
    private void upgradeTabelaProfessor(SQLiteDatabase bd, int oldVersion, int newVersion) {
        //Drop da tabela
        String sqlDropTabelaDTC = "DROP TABLE professor";
        //Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaDTC);
    }

    private void upgradeTabelaProva(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop da tabela
        String sqlDropTabelaDefeito = "DROP TABLE prova";
        // Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaDefeito);
    }

    private void upgradeTabelaTema(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop da tabela
        String sqlDropTabelaDefeito = "DROP TABLE tema";
        // Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaDefeito);
    }
    private void upgradeTabelaQuestao(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop da tabela
        String sqlDropTabelaDefeito = "DROP TABLE questao";
        // Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaDefeito);
    }
    private void upgradeTabelaAlternativa(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop da tabela
        String sqlDropTabelaDefeito = "DROP TABLE alternativa";
        // Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaDefeito);
    }

    private void upgradeTabelaProva_questao(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop da tabela
        String sqlDropTabelaDefeito = "DROP TABLE prova_questao";
        // Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaDefeito);
    }
}


