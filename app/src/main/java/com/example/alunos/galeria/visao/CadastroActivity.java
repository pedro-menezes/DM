package com.example.alunos.galeria.visao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alunos.galeria.R;
import com.example.alunos.galeria.controle.ImagemDB;
import com.example.alunos.galeria.modelo.Imagem;

import java.util.ArrayList;

public class CadastroActivity extends AppCompatActivity {
    private ImagemDB bd;
    private ArrayList<Imagem> list;
    private Imagem imagem;

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

        imagem = new Imagem();
        imagem.setNome(etNome.getText() + "");
        imagem.setUrl(etUrl.getText() + "");
        imagem.setDescricao(etDescricao.getText() + "");

        if (verificar() == true){
            Toast.makeText(this, "Imagem com o mesmo nome já cadastrada!",Toast.LENGTH_SHORT).show();
        } else if (verificar() == false){
            Toast.makeText(this, "Imagem cadastrada com sucesso!",Toast.LENGTH_SHORT).show();
            bd.save(imagem);
        }
    }


    public void onClickDeletaTudo(View v){
        bd.execSQL("DELETE FROM contato WHERE conCodigo > 0;");
    }

    public void onClickGaleria(View v){
        startActivity(new Intent(this, GaleriaActivity.class));
    }

    public void onClickListaDeFotos(View v){
        startActivity(new Intent(this, ListaActivity.class));
    }

    public boolean verificar(){
        list = bd.findAll();
        boolean verif = false;
        for (Imagem imagem1: list){
            if (imagem1.getNome().equals(imagem.getNome())){
            verif = true;
            }
        }
        return verif;
    }
}