package com.example.alunos.galeria.visao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.alunos.galeria.R;
import com.example.alunos.galeria.controle.ImagemDB;
import com.example.alunos.galeria.modelo.Imagem;

import java.util.List;

public class CadastroActivity extends AppCompatActivity {
    ImagemDB bd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        bd = new ImagemDB(getBaseContext());
    }

    public void onClickCadastrar(View v){
        EditText etNome =  (EditText)findViewById(R.id.etNome);
        EditText etUrl =  (EditText)findViewById(R.id.etUrl);
        EditText etDescricao =  (EditText)findViewById(R.id.etDescricao);

        Imagem imagem = new Imagem();
        imagem.setNome(etNome.getText() + "");
        imagem.setUrl(etUrl.getText() + "");
        imagem.setDescricao(etDescricao.getText() + "");

        bd.save(imagem);
    }


    public void onClickDeletaTudo(View v){
        bd.execSQL("DELETE FROM contato WHERE conCodigo > 0;");
    }

    public void onClickGaleria(View v){
        Intent itn = new Intent(this,ListaActivity.class);
        startActivity(itn);
    }
}