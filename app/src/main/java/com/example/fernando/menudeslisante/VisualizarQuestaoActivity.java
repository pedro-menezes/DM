package com.example.fernando.menudeslisante;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fernando.menudeslisante.adapters.AdapterListViewSimples;
import com.example.fernando.menudeslisante.bd.BDAlternativa;
import com.example.fernando.menudeslisante.bd.BDProva_Questao;
import com.example.fernando.menudeslisante.bd.BdQuestao;
import com.example.fernando.menudeslisante.beans.Alternativa;
import com.example.fernando.menudeslisante.beans.Prova_Questao;
import com.example.fernando.menudeslisante.beans.Questao;

import java.util.ArrayList;
import java.util.List;

public class VisualizarQuestaoActivity extends AppCompatActivity {
    private List<Questao> listaQuestao;
    private BdQuestao bdQuestao;
    private List<Alternativa> listaAlternativas;
    private BDAlternativa bdAlternativa;
    private Questao questao;
    private List<String> enunciadosQuestao;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_questao);
        enunciadosQuestao = new ArrayList<>();
        questao = new Questao();
        bdQuestao = new BdQuestao(this);
        Alternativa alternativa = new Alternativa();
        bdAlternativa = new BDAlternativa(this);
        Bundle extras = getIntent().getExtras();
        Object recebeExtra = extras.get("codigo");
        listaQuestao = bdQuestao.getAllSql();
        listView = (ListView) findViewById(R.id.listQuestao);

        Log.d("[IFMG]", "resultado: " + recebeExtra.toString());
        int codigo = Integer.parseInt(recebeExtra.toString());
        Log.d("[IFMG]", "resultado: " + recebeExtra.toString());

        for (Questao questao1: listaQuestao) {
                if (questao1.getqueCodigo() == codigo){
                    questao = questao1;
                }
        }

        Log.d("[IFMG]","resultado:"+ questao.getqueEnunciado());

        listaAlternativas = bdAlternativa.getAllAlternativasQuestao(questao.getqueCodigo());

        enunciadosQuestao.add(questao.getqueEnunciado());

        int cont = 0;

        for (Alternativa alternativa1: listaAlternativas) {
            switch (cont){
                case 0:
                    enunciadosQuestao.add("a) "+alternativa1.getAltEnunciado());
                    break;
                case 1:
                    enunciadosQuestao.add("b) "+alternativa1.getAltEnunciado());
                    break;
                case 2:
                    enunciadosQuestao.add("c) "+alternativa1.getAltEnunciado());
                    break;
                case 3:
                    enunciadosQuestao.add("d) "+alternativa1.getAltEnunciado());
                    break;
                case 4:
                    enunciadosQuestao.add("e) "+alternativa1.getAltEnunciado());
                    break;
            }
            cont++;
        }
        criaLista(enunciadosQuestao);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    public void onClick(View v){
        Bundle extras = getIntent().getExtras();
        Object recebeExtra = extras.get("codigoProva");
        int codigoProva = Integer.parseInt(recebeExtra.toString());

        Prova_Questao pquestao = new Prova_Questao();
        BDProva_Questao bdProvaQuestao = new BDProva_Questao(this);

        pquestao.setPrq_prvCodigo(codigoProva);
        pquestao.setPrq_queCodigo(questao.getqueCodigo());

        bdProvaQuestao.insertProva_Questao(pquestao);
        finish();
    }

    private AdapterListViewSimples salv;

    private void criaLista(List<String> lista) {
        //criando o adapter customizado
        salv = new AdapterListViewSimples(this);
        salv.setLista(lista);

        //setando o adapter customizado ao list
        listView.setAdapter(salv);
    }
}
