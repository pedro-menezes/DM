package com.example.fernando.menudeslisante;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.fernando.menudeslisante.adapters.AdapterListViewSimples;
import com.example.fernando.menudeslisante.bd.BDTema;
import com.example.fernando.menudeslisante.bd.BdQuestao;
import com.example.fernando.menudeslisante.beans.Prova;
import com.example.fernando.menudeslisante.beans.Questao;
import com.example.fernando.menudeslisante.beans.Tema;

import java.util.ArrayList;
import java.util.List;

public class AdicionarQuestoesActivity extends AppCompatActivity {
    private Spinner spinner;
    private List<Tema> listaTemas;
    private List<Questao> listaQuestao;
    private BDTema bdTema;
    private BdQuestao  bdQuestao;
    private List<String> nomeTemas;
    private ListView listView;
    private List<String> lista = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_questoes);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        final Intent intent = new Intent(this, VisualizarQuestaoActivity.class);
        bdTema = new BDTema(this);
        listaTemas = bdTema.getAllSql();
        bdQuestao = new BdQuestao(this);

        nomeTemas = new ArrayList<>();
        for (Tema tema: listaTemas) {
            nomeTemas.add(tema.gettemNome());
        }

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomeTemas);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptador);
        // Se selecionar algum planeta atualiza a imagem
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                // Atualiza a imagem
                int codigoTema = 0;
                for (Tema tema: listaTemas) {
                    if (tema.gettemNome().equals(nomeTemas.get(posicao))){
                        codigoTema = tema.gettemCodigo();
                    }
                }

                listaQuestao = bdQuestao.getAllQuestaosTema(codigoTema);
                lista.clear();

                for (Questao questao: listaQuestao) {
                    lista.add(questao.getqueEnunciado());
                }

                criaLista(lista);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // identifica ListView
        listView = (ListView) findViewById(R.id.listQuestoes);

        //evento de click na listagem de pratos
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int codigoQuestao = 0;
                for (Questao questao: listaQuestao) {
                    if (questao.getqueEnunciado().equals(lista.get(position))){
                        codigoQuestao = questao.getqueCodigo();
                    }
                }

                Bundle extras = getIntent().getExtras();
                Object recebeExtra = extras.get("codigoProva");

                intent.putExtra("codigoProva", Integer.parseInt(recebeExtra.toString()));
                intent.putExtra("codigo",codigoQuestao);
                startActivity(intent);
            }
        });

    }

    private AdapterListViewSimples salv;

    private void criaLista(List<String> lista) {
        //criando o adapter customizado
        salv = new AdapterListViewSimples(this);
        salv.setLista(lista);

        //setando o adapter customizado ao list
        listView.setAdapter(salv);
    }

    private AlertDialog alerta;

    private void AlertDialogSelList(final int position) {

        //final BdVeiculoUnidade bdVeiculoUnidade = new BdVeiculoUnidade(getContext());

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Comprar");
        //define a mensagem
        builder.setMessage("Deseja bla bla bla?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //bdVeiculoUnidade.deleteVeiculoUnidade(veiculoUnidadeList.get(position).getVeiPlaca());
                Toast.makeText(listView.getContext(), "Indice: : " + position, Toast.LENGTH_SHORT).show();
                // Recriar a lista atualizando-a
                //criaLista();
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
