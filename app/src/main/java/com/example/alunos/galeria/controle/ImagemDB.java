package com.example.alunos.galeria.controle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.alunos.galeria.modelo.Imagem;

import java.util.ArrayList;
import java.util.List;

public class ImagemDB extends SQLiteOpenHelper {
    private static final String TAG = "sql";

    // Nome do banco
    private static final String NOME_BANCO = "galeria";
    private static final String TABELA = "imagem";
    private static final int VERSAO_BANCO = 1;

    public ImagemDB(Context context) {
        // context, nome do banco, factory, versão
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a Tabela "+TABELA+"...");
        db.execSQL("create table if not exists " + TABELA + " (" +
                "imgCodigo integer primary key autoincrement," +
                "imgNome text, " +
                "imgUrl text," +
                "imgDescricao text" +
                ");");
        Log.d(TAG, "Tabela "+TABELA+" criada com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Caso mude a versão do banco de dados, podemos executar um SQL aqui
        if (oldVersion == 1 && newVersion == 2) {
            // Execute o script para atualizar a versão...
        }
    }

    // Insere um novo CONTATO, ou atualiza se já existe.
    public long save(Imagem imagem) {
        long id = imagem.getCodigo();
        SQLiteDatabase db = getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("imgNome", imagem.getNome());
            values.put("imgUrl", imagem.getUrl());
            values.put("imgDescricao", imagem.getDescricao());

            if (id != 0) {//SE O ID É DIFERENTE DE 0 ATUALIZA,

                String _id = String.valueOf(imagem.getCodigo());
                String[] whereArgs = new String[]{_id};

                // update contato set values = ... where _id=?
                int count = db.update(TABELA, values, "imgCodigo=?", whereArgs);

                return count;
            } else { // SE O ID FOR 0, SIGNIFICA QUE NÃO TEM ID, ASSIM VAI INSERIR O DADO
                // insert into contato values (...)
                Log.d(TAG, imagem.toString());
                id = db.insert(TABELA, "", values);
                return id;
            }
        } finally {
            db.close();
        }
    }

    // Deleta o CONTATO
    public int delete(Imagem imagem) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from contato where _id=?
            int count = db.delete(TABELA, "imgCodigo=?", new String[]{String.valueOf(imagem.getCodigo())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }


    // Consulta a lista com todos os contatos
    public ArrayList<Imagem> findAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from contato
            Cursor c = db.query(TABELA, null, null, null, null, null, null, null);

            return toList(c);
        } finally {
            db.close();
        }
    }

    // Consulta por sql testar depois
    public ArrayList<Imagem> findBySql(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql,null);
            ArrayList<Imagem> imagems = new ArrayList<Imagem>();

            if (c.moveToFirst()) {
                do {
                    Imagem imagem = new Imagem();
                    imagems.add(imagem);

                    // recupera os atributos de imagem
                    imagem.setCodigo(c.getLong(c.getColumnIndex("imgCodigo")));
                    imagem.setNome(c.getString(c.getColumnIndex("imgNome")));
                    imagem.setUrl(c.getString(c.getColumnIndex("imgUrl")));
                    imagem.setDescricao(c.getString(c.getColumnIndex("imgDescricao")));
                } while (c.moveToNext());
            }
            return imagems;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private ArrayList<Imagem> toList(Cursor c) {
        ArrayList<Imagem> imagems = new ArrayList<Imagem>();

        if (c.moveToFirst()) {
            do {
                Imagem imagem = new Imagem();
                imagems.add(imagem);

                // recupera os atributos de imagems
                imagem.setCodigo(c.getLong(c.getColumnIndex("imgCodigo")));
                imagem.setNome(c.getString(c.getColumnIndex("imgNome")));
                imagem.setUrl(c.getString(c.getColumnIndex("imgUrl")));
                imagem.setDescricao(c.getString(c.getColumnIndex("imgDescricao")));
            } while (c.moveToNext());
        }
        return imagems;
    }

    // Executa um SQL
    public void execSQL(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
        } finally {
            db.close();
        }
    }

}