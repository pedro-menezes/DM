package com.example.fernando.menudeslisante.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.fernando.menudeslisante.beans.Questao;
import com.example.fernando.menudeslisante.beans.Tema;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BdQuestao {
    public SQLiteDatabase db, dbr;

    public BdQuestao(Context ctx) {
        BdCore auxBd = new BdCore(ctx);
        db = auxBd.getWritableDatabase();

        dbr = auxBd.getReadableDatabase();
    }

    public long insertQuestao(Questao questao) {
        ContentValues values = new ContentValues();
        values.put("queCodigo", questao.getqueCodigo());
        values.put("queEnunciado", questao.getqueEnunciado());
        values.put("que_temCodigo", questao.getque_temCodigo());

        //inserindo diretamente na tabela pessoa sem a necessidade de script sql
        return db.insert("questao", null, values);
    }
    /**
     * Deletar
     */
    public void deleteAllQuestaos() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // queeta informações da tabela,
        db.execSQL("DELETE FROM questao;");
    }

    public List<Questao> getAllSql(){
        return findBySql("SELECT * FROM questao;");
    }
    // Consulta por sql s
    public List<Questao> findBySql(String sql) {
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
    private List<Questao> toList(Cursor c) {
        List<Questao> questoes = new ArrayList<Questao>();
        Log.d("IFMG", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {
                Questao questao = new Questao();
                questoes.add(questao);

                // recupera os atributos de professores
                questao.setqueCodigo(c.getInt(c.getColumnIndex("queCodigo")));
                questao.setqueEnunciado(c.getString(c.getColumnIndex("queEnunciado")));
                questao.setque_temCodigo(c.getInt(c.getColumnIndex("que_temCodigo")));
            } while (c.moveToNext());
        }
        return questoes;
    }
    /**
     * Deletar por codigo
     */
    public void deleteQuestao(int queCodigo) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // Converte paramentro para string
        String args[] = new String[]{(queCodigo+"")};

        // Delete query
        db.delete("questao",// Nome da tabela
                "queCodigo=?",// Coluna da tabela
                args); // Argumentos de delete

    }

    public List<String> getAllQuestaosLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM  questao";

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

    public List<Questao> getAllQuestaosTema(int temCodigo) {
        List<Questao> queList = new ArrayList<Questao>();

        // Select All Query
        String selectQuery = "SELECT  * FROM  questao WHERE que_temCodigo = "+temCodigo+";";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding the objs to list
        if (cursor.moveToFirst()) {
            do {
                Questao que = new Questao();
                que.setqueCodigo(cursor.getInt(0));
                que.setqueEnunciado(cursor.getString(1));
                que.setque_temCodigo(cursor.getInt(2));
                queList.add(que);
            } while (cursor.moveToNext());
        }

        return queList;
    }


    public List<Questao> buscarQuestao(int id) {
        // Cria lista
        Questao que = new Questao();
        List<Questao> listaQuestoes = new LinkedList<Questao>();
        // Query do banco
        Cursor cursor = db.query("questao",
                new String[]{"queCodigo", "queEnunciado", "que_temCodigo"},
                "queCodigo=?", new String[]{String.valueOf(id)},
                null, null, null);

        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui
                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                que.setqueCodigo(cursor.getInt(0));
                que.setqueEnunciado(cursor.getString(1));
                que.setque_temCodigo(cursor.getInt(2));
                // Adiciona as informacoes
                listaQuestoes.add(que);
            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        }
        // Retorna a lista
        return listaQuestoes;
    }
}
