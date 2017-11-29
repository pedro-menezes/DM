package com.example.fernando.menudeslisante.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.fernando.menudeslisante.beans.Professor;
import com.example.fernando.menudeslisante.beans.Questao;
import com.example.fernando.menudeslisante.beans.Tema;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



/**
 * Classe responsável por manipular o banco de dados na tabela específica
 */
public class BDTema {
    public SQLiteDatabase db,dbr;

    public BDTema(Context ctx) {
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
    public long insertTema(Tema tema) {
        ContentValues values = new ContentValues();
        values.put("temCodigo", tema.gettemCodigo());
        values.put("temNome", tema.gettemNome());

        //inserindo diretamente na tabela pessoa sem a necessidade de script sql
        return db.insert("tema", null, values);
    }
    /**
     * Método para deletar
     */
    public void deleteAlltemas() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // deleta todas informações da tabela usando script sql
        db.execSQL("DELETE FROM tema;");
    }

    /**
     * Deletar por codigo
     */
    public void deleteTema(int temCodigo) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        /// Converte paramentro para string
        String args[] = new String[]{temCodigo+""};

        // Delete query
        db.delete("tema",// Nome da tabela
                "temCodigo=?",// Coluna da tabela
                args); // Argumentos de delete

    }

    /**
     * Listar
     *
     * @return
     */
    public List<Tema> listarprofessors() {
        // Cria lista
        List<Tema> listatema = new LinkedList<Tema>();
        // Query do banco
        String query = "SELECT * FROM tema";
        // Cria o cursor e executa a query
        Cursor cursor = db.rawQuery(query, null);
        // Percorre os resultados
        // Se o cursor pode ir ao primeiro
        if (cursor.moveToFirst()) do {
            // Cria novo , cada vez que entrar aqui
            Tema tema = new Tema();
            // Define os campos da configuracao, pegando do cursor pelo id da coluna
            tema.settemCodigo(cursor.getInt(0));
            tema.settemNome(cursor.getString(1));
            listatema.add(tema);
        }
        while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        // Retorna a lista
        return listatema;
    }



    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM  tema;";

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


    public List<Tema> buscarprofessor(Integer cod) {
        // Cria lista
        List<Tema> listaprofessor = new LinkedList<Tema>();
        // Query do banco
        Cursor cursor = db.query("tema",
                new String[]{"temCodigo", "temNome"},
                "temCodigo=?", new String[]{String.valueOf(cod)},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui
                Tema tema = new Tema();
                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                tema.settemCodigo(cursor.getInt(0));
                tema.settemNome(cursor.getString(1));
            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        }
        // Retorna a lista
        return listaprofessor;
    }

    //-------------------------------------------------------------------------
    public List<Tema> getAllSql(){
        return findBySql("SELECT * FROM tema;");
    }
    // Consulta por sql s
    public List<Tema> findBySql(String sql) {
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
    private List<Tema> toList(Cursor c) {
        List<Tema> temas = new ArrayList<Tema>();
        Log.d("IFMG", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {
                Tema tema = new Tema();
                temas.add(tema);

                // recupera os atributos de professores
                tema.settemCodigo(c.getInt(c.getColumnIndex("temCodigo")));
                tema.settemNome(c.getString(c.getColumnIndex("temNome")));
            } while (c.moveToNext());
        }
        return temas;
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
