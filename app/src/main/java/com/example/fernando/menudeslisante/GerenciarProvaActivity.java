package com.example.fernando.menudeslisante;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fernando.menudeslisante.bd.BDProva;
import com.example.fernando.menudeslisante.bd.BDProva_Questao;
import com.example.fernando.menudeslisante.beans.Prova;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class GerenciarProvaActivity extends AppCompatActivity {
    private TextView textViewCodigo, textViewNome, textViewNQuestoes;
    private List<Prova> provas;
    private BDProva bdProva;
    private Prova prova;
    private int codigo;
    private AlertDialog alerta;
    private BDProva_Questao bdProva_questao;
    private  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_prova);

        intent = new Intent(this, MenuActivity.class);

        textViewCodigo = (TextView) findViewById(R.id.textViewCodigo);
        textViewNome = (TextView) findViewById(R.id.textViewNome);
        textViewNQuestoes = (TextView) findViewById(R.id.textViewNQuestoes);

        provas = new ArrayList<>();
        bdProva = new BDProva(this);
        provas = bdProva.getAllSql();
        prova = new Prova();

        Bundle extras = getIntent().getExtras();
        Object recebeExtras = extras.get("codigoProva");
        codigo = Integer.parseInt(recebeExtras.toString());

        for (Prova prova1: provas) {
            if (prova1.getprvCodigo() == codigo){
                        prova = prova1;
            }
        }

        textViewCodigo.setText(String.valueOf(prova.getprvCodigo()));
        textViewNome.setText(prova.getprvNome());
        textViewNQuestoes.setText(String.valueOf(prova.numeroQuestoes(this)));

        bdProva_questao = new BDProva_Questao(this);

        String[] permissoes = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        PermissionUtils.validate(this, 0, permissoes);

    }

    public void onClickEmail(View view){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Título do email");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Corpo do email");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "fernando.lima@ifmg.edu.br");
        emailIntent.setType("message/rfc822");
        Uri uri = Uri.parse("sdcard/dirtTest/TESTE.pdf");
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(emailIntent);
    }

    public void onClickPDF(View view){
        //gera o pdf
        new PDF("dirTeste","TESTE", prova);
        Toast.makeText(getBaseContext(),"PDF criado!",Toast.LENGTH_SHORT).show();
    }

    public void onClickExcluir(View view){
        AlertDialogExcluir();
    }

    private void AlertDialogExcluir() {

        //final BdVeiculoUnidade bdVeiculoUnidade = new BdVeiculoUnidade(getContext());

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Excluir");
        //define a mensagem
        builder.setMessage("Deseja excluir a prova "+codigo+"?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                bdProva.deleteProva(codigo);
                bdProva_questao.deleteLigacoesProva(codigo);
                startActivity(intent);
                finish();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

}
