package com.example.alunos.galeria.visao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alunos.galeria.R;
import com.example.alunos.galeria.controle.ImagemDB;
import com.example.alunos.galeria.modelo.Imagem;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends Activity implements OnItemClickListener {
    //protected static final String TAG = "livro";
    private ListView listView;
    private ImagemDB bd;
    private ArrayList<String> lista;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_lista);
        bd = new ImagemDB(getBaseContext());

        //pega array do intent
        Intent intent = getIntent();
        lista = intent.getStringArrayListExtra("lista");

        // identifica ListView
        listView = (ListView) findViewById(R.id.listView);

        //cria adatper simples a partir da classe criada
        AdapterListView salv = new AdapterListView(this);
        atualizaCampoTexto();
        salv.setLista(lista);//atribuindo a lista vindo da principal no adapter

        //setando adapter criado
        listView.setAdapter(salv);
        listView.setOnItemClickListener(this);
    }

    public void atualizaCampoTexto(){
        ArrayList<Imagem> imagems = bd.findAll();

        for (Imagem imagem: imagems) {
            lista.add(imagem.getNome());
        }
    }

    //método listener do listView setado no onCreate
    public void onItemClick(AdapterView<?> parent, View view, int idx, long id) {
        String s = (String) parent.getAdapter().getItem(idx); // Objeto selecionado, que neste caso era de um array de strings
        Toast.makeText(this, "Texto selecionado: " + s + ", posição: " + idx, Toast.LENGTH_SHORT).show();
    }
}

