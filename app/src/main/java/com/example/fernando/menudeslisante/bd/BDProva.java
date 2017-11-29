package com.example.fernando.menudeslisante.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.fernando.menudeslisante.beans.Prova;
import com.example.fernando.menudeslisante.beans.Questao;
import com.example.fernando.menudeslisante.beans.Tema;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pedro-menezes on 22/11/17.
 */

public class BDProva {
    public SQLiteDatabase db, dbr;

    public  BDProva(Context ctx){
        BdCore auxBd = new BdCore(ctx);
        db = auxBd.getWritableDatabase();

        //acesso para leitura do bd
        dbr = auxBd.getReadableDatabase();
    }


    /**
     * Deletar
     */
    public void deleteAllProvas() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // queeta informações da tabela,
        db.execSQL("DELETE FROM prova;");
    }

    public long insertProva(Prova prova) {
        ContentValues values = new ContentValues();
        values.put("prvNome", prova.getprvNome());

        //inserindo diretamente na tabela pessoa sem a necessidade de script sql
        return db.insert("prova", null, values);
    }

    /**
     * Deletar por codigo
     */
    public void deleteProva(int prvCodigo) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // Converte paramentro para string
        String args[] = new String[]{(prvCodigo+"")};

        // Delete query
        db.delete("prova",// Nome da tabela
                "prvCodigo=?",// Coluna da tabela
                args); // Argumentos de delete

    }

    public List<String> getAllProvasLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM  prova";

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

    public List<Prova> getAllSql(){
        return findBySql("SELECT * FROM prova;");
    }
    // Consulta por sql s
    public List<Prova> findBySql(String sql) {
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
    private List<Prova> toList(Cursor c) {
        List<Prova> provas = new ArrayList<Prova>();
        Log.d("IFMG", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {
                Prova prova = new Prova();
                provas.add(prova);

                // recupera os atributos de professores
                prova.setprvCodigo(c.getInt(c.getColumnIndex("prvCodigo")));
                prova.setprvNome(c.getString(c.getColumnIndex("prvNome")));
            } while (c.moveToNext());
        }
        return provas;
    }

    public List<Prova> getAllProvaProf(int proCodigo) {
        List<Prova> prvList = new ArrayList<Prova>();

        // Select All Query
        String selectQuery = "SELECT  * FROM  prova WHERE prv_proCodigo = "+proCodigo+";";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding the objs to list
        if (cursor.moveToFirst()) {
            do {
                Prova prova = new Prova();
                prova.setprvCodigo(cursor.getInt(0));
                prova.setprvNome(cursor.getString(1));
                prova.setPrv_proCodigo(cursor.getInt(2));
                prvList.add(prova);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return prvList;
    }


    public List<Prova> buscarProva(int id) {
        // Cria lista
        List<Prova> listaProva = new LinkedList<Prova>();
        // Query do banco
        Cursor cursor = db.query("prova",
                new String[]{"prvCodigo", "prvNome", "prv_proCodigo"},
                "prvCodigo=?", new String[]{String.valueOf(id)},
                null, null, null);

        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui
                Prova prova = new Prova();
                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                prova.setprvCodigo(cursor.getInt(0));
                prova.setprvNome(cursor.getString(1));
                prova.setPrv_proCodigo(cursor.getInt(2));
                // Adiciona as informacoes
                listaProva.add(prova);
            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        }
        // Retorna a lista
        return listaProva;
    }
}
