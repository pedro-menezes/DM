package com.example.fernando.menudeslisante;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fernando.menudeslisante.bd.BDAlternativa;
import com.example.fernando.menudeslisante.bd.BDProva;
import com.example.fernando.menudeslisante.bd.BDProva_Questao;
import com.example.fernando.menudeslisante.bd.BDTema;
import com.example.fernando.menudeslisante.bd.BdQuestao;
import com.example.fernando.menudeslisante.beans.Alternativa;
import com.example.fernando.menudeslisante.beans.Prova;
import com.example.fernando.menudeslisante.beans.Prova_Questao;
import com.example.fernando.menudeslisante.beans.Questao;
import com.example.fernando.menudeslisante.beans.Tema;
import com.itextpdf.text.Paragraph;

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
        BdQuestao bdQuestao = new BdQuestao(this);
        BDTema bdTema = new BDTema(this);
        BDAlternativa bdAlternativa = new BDAlternativa(this);
        ArrayList<Integer> codigosQuestoes = new ArrayList<>();
        ArrayList<Integer> codigosTemas = new ArrayList<>();
        List<Prova_Questao> prova_questoes = bdProva_questao.getAllSql();
        List<Tema> temas = bdTema.getAllSql();
        List<Alternativa> alternativas = new ArrayList<>();

        for (Prova_Questao prova_questao: prova_questoes) {
            if (prova_questao.getPrq_prvCodigo() == prova.getprvCodigo()){
                codigosQuestoes.add(prova_questao.getPrq_queCodigo());
            }
        }

        ArrayList<Questao> questoes = new ArrayList<>();

        for (Integer codigosQuest: codigosQuestoes) {
            questoes.add(bdQuestao.buscarQuestao(codigosQuest).get(0));
        }

        for (Questao questao: questoes) {
            codigosTemas.add(questao.getque_temCodigo());
        }

        String nomeTemas = "";
        for (Tema tema: temas) {
            nomeTemas = nomeTemas+tema.gettemNome()+",";
        }

        ArrayList<Alternativa> alternativas1 = new ArrayList<>();
        for (Questao questao: questoes) {
            alternativas = bdAlternativa.findBySql("SELECT * FROM alternativa WHERE alt_queCodigo='"+questao.getqueCodigo()+"';");
            for (Alternativa alternativa: alternativas) {
                alternativas1.add(alternativa);
            }
        }

        for (Alternativa alternativa: alternativas1) {
            Log.d("[IFMG]", alternativa.getAltEnunciado());

        }


        String corpoEmail =" ";
        int contQuestao = 1;
        int contAlternativa = 1;
        for (Questao questao:questoes) {
            corpoEmail = corpoEmail + contQuestao+") "+questao.getqueEnunciado()+"\n";
            for (Alternativa alternativa: alternativas) {
                if (alternativa.getAlt_queCodigo() == questao.getqueCodigo()){
                    switch (contAlternativa){
                        case 1:
                            corpoEmail = corpoEmail + "a) "+alternativa.getAltEnunciado() + "\n";
                            contAlternativa++;
                            break;
                        case 2:
                            corpoEmail = corpoEmail + "b) "+alternativa.getAltEnunciado() + "\n";
                            contAlternativa++;
                            break;
                        case 3:
                            corpoEmail = corpoEmail + "c) "+alternativa.getAltEnunciado() + "\n";
                            contAlternativa++;
                            break;
                        case 4:
                            corpoEmail = corpoEmail + "d) "+alternativa.getAltEnunciado() + "\n";
                            contAlternativa++;
                            break;
                        case 5:
                            corpoEmail = corpoEmail + "e) "+alternativa.getAltEnunciado() + "\n\n\n";
                            break;
                    }
                }
            }
            contQuestao++;
        }
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Título do email");
        emailIntent.putExtra(Intent.EXTRA_TEXT, corpoEmail);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "fernando.lima@ifmg.edu.br");
        File file = new File("dirtTest/TESTE.pdf");
        Uri uri = Uri.fromFile(file);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        emailIntent.setType("application/pdf");

        startActivity(emailIntent);
    }

    public void onClickPDF(View view){
        //gera o pdf
        BdQuestao bdQuestao = new BdQuestao(this);
        BDTema bdTema = new BDTema(this);
        BDAlternativa bdAlternativa = new BDAlternativa(this);
        ArrayList<Integer> codigosQuestoes = new ArrayList<>();
        ArrayList<Integer> codigosTemas = new ArrayList<>();
        List<Prova_Questao> prova_questoes = bdProva_questao.getAllSql();
        List<Tema> temas = bdTema.getAllSql();
        List<Alternativa> alternativas = new ArrayList<>();

        for (Prova_Questao prova_questao: prova_questoes) {
            if (prova_questao.getPrq_prvCodigo() == prova.getprvCodigo()){
                codigosQuestoes.add(prova_questao.getPrq_queCodigo());
            }
        }

        ArrayList<Questao> questoes = new ArrayList<>();

        for (Integer codigosQuest: codigosQuestoes) {
            questoes.add(bdQuestao.buscarQuestao(codigosQuest).get(0));
        }

        for (Questao questao: questoes) {
            codigosTemas.add(questao.getque_temCodigo());
        }

        String nomeTemas = "";
        for (Tema tema: temas) {
            nomeTemas = nomeTemas+tema.gettemNome()+",";
        }

        ArrayList<Alternativa> alternativas1 = new ArrayList<>();
        for (Questao questao: questoes) {
            alternativas = bdAlternativa.findBySql("SELECT * FROM alternativa WHERE alt_queCodigo='"+questao.getqueCodigo()+"';");
            for (Alternativa alternativa: alternativas) {
                alternativas1.add(alternativa);
            }
        }

        for (Alternativa alternativa: alternativas1) {
            Log.d("[IFMG]", alternativa.getAltEnunciado());

        }


        new PDF("dirTeste","TESTE", prova, nomeTemas, questoes, alternativas1);
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
