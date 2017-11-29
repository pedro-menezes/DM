package com.example.fernando.menudeslisante.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.fernando.menudeslisante.beans.Prova_Questao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pedro-menezes on 26/11/17.
 */

public class BDProva_Questao {
    public SQLiteDatabase db,dbr;

    public BDProva_Questao(Context ctx) {
        //objeto obrigatório para todas as classes
        BdCore auxBd = new BdCore(ctx);

        //acesso para escrita no bd
        db = auxBd.getWritableDatabase();
        //acesso para leitura do bd
        dbr = auxBd.getReadableDatabase();
    }

    /**
     * Método para inserir
     */
    public long insertProva_Questao(Prova_Questao pquestao) {
        ContentValues values = new ContentValues();
        values.put("prq_prvCodigo", pquestao.getPrq_prvCodigo());
        values.put("prq_queCodigo", pquestao.getPrq_queCodigo());

        //inserindo diretamente na tabela pessoa sem a necessidade de script sql
        return db.insert("prova_questao", null, values);
    }
    public void deleteLigacoesProva(int prvCodigo) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        /// Converte paramentro para string
        String args[] = new String[]{prvCodigo+""};

        // Delete query
        db.delete("prova_questao",// Nome da tabela
                "prq_prvCodigo=?",// Coluna da tabela
                args); // Argumentos de delete

    }
    /**
     * Método para deletar
     */
    public void deleteAllligacoes() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // deleta todas informações da tabela usando script sql
        db.execSQL("DELETE FROM prova_questao;");
    }

    /**
     * Listar
     *
     * @return
     */
    public List<Prova_Questao> listarprofessors() {
        // Cria lista
        List<Prova_Questao> prova_questaos = new LinkedList<Prova_Questao>();
        // Query do banco
        String query = "SELECT * FROM prova_questao";
        // Cria o cursor e executa a query
        Cursor cursor = db.rawQuery(query, null);
        // Percorre os resultados
        // Se o cursor pode ir ao primeiro
        if (cursor.moveToFirst()) do {
            // Cria novo , cada vez que entrar aqui
            Prova_Questao prova_questao = new Prova_Questao();
            // Define os campos da configuracao, pegando do cursor pelo id da coluna
            prova_questao.setPrq_prvCodigo(cursor.getInt(0));
            prova_questao.setPrq_queCodigo(cursor.getInt(1));
            prova_questaos.add(prova_questao);
        }
        while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        // Retorna a lista
        return prova_questaos;
    }



    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM  prova_questao;";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    //-------------------------------------------------------------------------
    public List<Prova_Questao> getAllSql(){
        return findBySql("SELECT * FROM prova_questao;");
    }
    // Consulta por sql s
    public List<Prova_Questao> findBySql(String sql) {
        //SQLiteDatabase db = getReadableDatabase();
        Log.d("[IFMG]", "SQL: " + sql);
        try {
            Log.d("[IFMG]", "Vai consultar");
            Cursor c = dbr.rawQuery(sql, null);
            Log.d("[IFMG]", "Consultou...");
            return toList(c);
        } finally {

            //dbr.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Prova_Questao> toList(Cursor c) {
        List<Prova_Questao> prova_questaos = new ArrayList<Prova_Questao>();
        Log.d("IFMG", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {
                Prova_Questao prova_questao = new Prova_Questao();
                prova_questaos.add(prova_questao);

                // recupera os atributos de professores
                prova_questao.setPrq_prvCodigo(c.getInt(c.getColumnIndex("prq_prvCodigo")));
                prova_questao.setPrq_queCodigo(c.getInt(c.getColumnIndex("prq_queCodigo")));
            } while (c.moveToNext());
        }
        return prova_questaos;
    }

    /**
     * método que somente executa sql de manipulação de dados
     * @param sql
     */
    public void executeSQL(String sql) {
        try {
            Log.d("IFMG", "Executando: " + sql);
            db.execSQL(sql);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

}

