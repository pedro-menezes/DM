package com.example.alunos.galeria.visao;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alunos.galeria.R;
import com.example.alunos.galeria.controle.ImagemDB;
import com.example.alunos.galeria.modelo.Imagem;

import java.util.ArrayList;

public class ListaActivity extends Activity implements OnItemClickListener {
    //protected static final String TAG = "livro";
    private ListView listView;
    private ImagemDB bd;
    private ArrayList<Imagem> list;
    private ArrayList<String> listName = new ArrayList<>();

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_lista);
        bd = new ImagemDB(getBaseContext());
        list = bd.findAll();

        for (Imagem listImagem: list){
            int cont = 0;
            listName.add(listImagem.getNome());
            Log.d("TESTE", listName.get(cont));
        }
        //identifica ListView
       listView = (ListView) findViewById(R.id.listView);


        //cria adatper simples a partir da classe criada
        AdapterListView salv = new AdapterListView(this);
        salv.setLista(listName);//atribuindo a lista vindo da principal no adapter

        //setando adapter criado
        listView.setAdapter(salv);
       listView.setOnItemClickListener(this);
    }
    //

    //método listener do listView setado no onCreate
    public void onItemClick(AdapterView<?> parent, View view, int idx, long id) {
        String s = (String) parent.getAdapter().getItem(idx); // Objeto selecionado, que neste caso era de um array de strings
        Toast.makeText(this, "Texto selecionado: " + s + ", posição: " + idx, Toast.LENGTH_SHORT).show();
    }
}

