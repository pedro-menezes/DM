package com.example.fernando.menudeslisante.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.fernando.menudeslisante.beans.Professor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



/**
 * Classe responsável por manipular o banco de dados na tabela específica
 */
public class BdProfessor {

    public SQLiteDatabase db,dbr;

    public BdProfessor(Context context) {
        //objeto obrigatório para todas as classes
        BdCore auxBd = new BdCore(context);

        //acesso para escrita no bd
        db = auxBd.getWritableDatabase();
        //acesso para leitura do bd
        dbr = auxBd.getReadableDatabase();
    }


    /**
     * Método para inserir
     */

    /**
     * Método para deletar
     */
    public void deleteAllprofessor() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // deleta todas informações da tabela usando script sql
        db.execSQL("DELETE FROM professor;");
    }

    /**
     * Deletar por codigo
     */
    public void deleteProfessor(int pesCodigo) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        /// Converte paramentro para string
        String args[] = new String[]{pesCodigo+""};

        // Delete query
        db.delete("professor",// Nome da tabela
                "pesCodigo=?",// Coluna da tabela
                args); // Argumentos de delete

    }

    /**
     * Listar
     *
     * @return
     */
    public List<Professor> listarprofessors() {
        // Cria lista
        List<Professor> listaprofessor = new LinkedList<Professor>();
        // Query do banco
        String query = "SELECT * FROM professor";
        // Cria o cursor e executa a query
        Cursor cursor = db.rawQuery(query, null);
        // Percorre os resultados
        // Se o cursor pode ir ao primeiro
        if (cursor.moveToFirst()) do {
            // Cria novo , cada vez que entrar aqui
            Professor professor = new Professor();
            // Define os campos da configuracao, pegando do cursor pelo id da coluna
            professor.setProCodigo(cursor.getInt(0));
            professor.setProNome(cursor.getString(1));
            professor.setProEmail(cursor.getString(2));
            professor.setProSenha(cursor.getString(3));
            // Adiciona as informacoes
            listaprofessor.add(professor);
        }
        while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        // Retorna a lista
        return listaprofessor;
    }



    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM  professor;";

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


    public List<Professor> buscarprofessor(Integer cod) {
        // Cria lista
        List<Professor> listaprofessor = new LinkedList<Professor>();
        // Query do banco
        Cursor cursor = db.query("professor",
                new String[]{"proCodigo", "proNome", "proEmail", "proSenha"},
                "proCodigo=?", new String[]{String.valueOf(cod)},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui
                Professor professor = new Professor();
                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                professor.setProCodigo(cursor.getInt(0));
                professor.setProNome(cursor.getString(1));
                professor.setProEmail(cursor.getString(2));
                professor.setProSenha(cursor.getString(3));
            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        }
        // Retorna a lista
        return listaprofessor;
    }

    //-------------------------------------------------------------------------
    public List<Professor> getAllSql(){
        return findBySql("SELECT * FROM professor;");
    }
    // Consulta por sql s
    public List<Professor> findBySql(String sql) {
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
    private List<Professor> toList(Cursor c) {
        List<Professor> professores = new ArrayList<Professor>();
        Log.d("IFMG", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {
                Professor professor = new Professor();
                professores.add(professor);

                // recupera os atributos de professores
                professor.setProCodigo(c.getInt(c.getColumnIndex("proCodigo")));
                professor.setProNome(c.getString(c.getColumnIndex("proNome")));
                professor.setProEmail(c.getString(c.getColumnIndex("proEmail")));
                professor.setProSenha(c.getString(c.getColumnIndex("proSenha")));
            } while (c.moveToNext());
        }
        return professores;
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
