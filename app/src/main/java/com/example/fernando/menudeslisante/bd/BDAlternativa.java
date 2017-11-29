package com.example.fernando.menudeslisante.bd;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;

        import com.example.fernando.menudeslisante.beans.Alternativa;
        import com.example.fernando.menudeslisante.beans.Questao;

        import java.util.ArrayList;
        import java.util.LinkedList;
        import java.util.List;

public class BDAlternativa {
    public SQLiteDatabase db;

    public BDAlternativa(Context context) {
        BdCore auxBd = new BdCore(context);
        db = auxBd.getWritableDatabase();
    }

    public long insertAlternativa(Alternativa questao) {
        ContentValues values = new ContentValues();
        values.put("altCodigo", questao.getAltCodigo());
        values.put("altEnunciado", questao.getAltEnunciado());
        values.put("alt_queCodigo", questao.getAlt_queCodigo());
        values.put("altCorreta", questao.getAltCorreta());
        //inserindo diretamente na tabela pessoa sem a necessidade de script sql
        return db.insert("alternativa", null, values);
    }
    /**
     * Deletar
     */
    public void deleteAllAlternativas() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // queeta informações da tabela,
        db.execSQL("DELETE FROM alternativa;");
    }

    /**
     * Deletar por codigo
     */
    public void deleteAlternativa(int altCodigo) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // Converte paramentro para string
        String args[] = new String[]{(altCodigo+"")};

        // Delete query
        db.delete("alternativa",// Nome da tabela
                "altCodigo=?",// Coluna da tabela
                args); // Argumentos de delete

    }

    public List<String> getAllAlternativasLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM  alternativa";

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

    public List<Alternativa> getAllAlternativasQuestao(int queCodigo) {
        List<Alternativa> altList = new ArrayList<Alternativa>();

        // Select All Query
        String selectQuery = "SELECT  * FROM  alternativa WHERE alt_queCodigo = "+queCodigo+";";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding the objs to list
        if (cursor.moveToFirst()) {
            do {
                Alternativa alternativa = new Alternativa();
                alternativa.setAltCodigo(cursor.getInt(0));
                alternativa.setAltEnunciado(cursor.getString(1));
                alternativa.setAltCorreta(cursor.getInt(2));
                alternativa.setAlt_queCodigo(cursor.getInt(3));
                altList.add(alternativa);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return altList;
    }


    public List<Alternativa> buscarAlternativa(int id) {
        // Cria lista
        List<Alternativa> listaAlternativas = new LinkedList<Alternativa>();
        // Query do banco
        Cursor cursor = db.query("questao",
                new String[]{"queCodigo", "queEnunciado", "que_temCodigo"},
                "queCodigo=?", new String[]{String.valueOf(id)},
                null, null, null);

        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui
                Alternativa alternativa = new Alternativa();
                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                alternativa.setAltCodigo(cursor.getInt(0));
                alternativa.setAltEnunciado(cursor.getString(1));
                alternativa.setAltCorreta(cursor.getInt(2));
                alternativa.setAlt_queCodigo(cursor.getInt(3));
                // Adiciona as informacoes
                listaAlternativas.add(alternativa);
            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        }
        // Retorna a lista
        return listaAlternativas;
    }
}
