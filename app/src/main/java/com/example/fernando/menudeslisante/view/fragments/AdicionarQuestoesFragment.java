package com.example.fernando.menudeslisante.view.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fernando.menudeslisante.AdicionarQuestoesActivity;
import com.example.fernando.menudeslisante.MainActivity;
import com.example.fernando.menudeslisante.R;
import com.example.fernando.menudeslisante.adapters.AdapterListViewSimples;
import com.example.fernando.menudeslisante.bd.BDProva;
import com.example.fernando.menudeslisante.bd.BDProva_Questao;
import com.example.fernando.menudeslisante.beans.Prova;
import com.example.fernando.menudeslisante.beans.Prova_Questao;
import com.example.fernando.menudeslisante.beans.Questao;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdicionarQuestoesFragment extends Fragment {
    private ListView listView;
    List<String> lista = new ArrayList<String>();
    private boolean verificador = false;
    private BDProva bdProva;
    private Prova prova;
    private EditText editText;
    private String nome;
    private Button button, button2;
    private Prova prova2;
    private int codigo = Integer.MAX_VALUE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //extraio o objeto view para trabalhar com demais componentes no fragment
        View view = inflater.inflate(R.layout.fragment_opcao2, container, false);
        editText = (EditText) view.findViewById(R.id.editText);
        bdProva = new BDProva(getContext());
        // identifica ListView
        listView = (ListView) view.findViewById(R.id.listViewQuestoes);

        //evento de click na listagem de pratos
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialogSelList(position);
            }
        });
        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        button = (Button)view.findViewById(R.id.buttonQuestoes);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() == 0){
                    Toast.makeText(getContext(), "O nome da prova deve ser digitado primeiro!", Toast.LENGTH_SHORT).show();
                } else {

                    List<Prova> provas = new ArrayList<>();
                    provas = bdProva.getAllSql();
                    prova = new Prova();
                    prova.setprvNome(editText.getText().toString());

                    boolean verif = false;
                    if (verificador == false) {
                        verif = false;
                        codigo = 0;
                        for (Prova prova1 : provas) {
                            if (prova1.getprvNome().equals(prova.getprvNome())) {
                                verif = true;
                                codigo = prova1.getprvCodigo();
                            }
                        }
                        verificador = true;
                    }
                    if (verif == true){
                        Log.d("[IFMG]","resultado: prova já existente");
                        Toast.makeText(getContext(), "Já existe uma prova cadastrada com esse nome!", Toast.LENGTH_SHORT).show();
                    } else if (verif == false){
                        verificador = true;
                        bdProva.insertProva(prova);

                        prova2 = new Prova();
                        prova2.setprvCodigo(0);
                        for (Prova prova1: provas) {
                            if (prova1.getprvCodigo() > prova2.getprvCodigo()){
                                prova2 = prova1;
                            }
                        }
                        codigo = prova2.getprvCodigo();
                        Log.d("[IFMG]","resultado: não existia, prova nova");
                        editText.setEnabled(false);
                        Intent intent = new Intent(getActivity(), AdicionarQuestoesActivity.class);
                        intent.putExtra("codigoProva", prova2.getprvCodigo());
                        startActivity(intent);
                    }
                }
            }
        });


        button2 = (Button)view.findViewById(R.id.buttonCadastar);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codigo == Integer.MAX_VALUE){
                    Toast.makeText(getContext(), "As informações não foram cadastradas!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            }
        });
    }

    public AdicionarQuestoesFragment(){

    }

    public void onResume() {
        super.onResume();

        criaLista();
    }

    private AdapterListViewSimples salv;

    private void criaLista() {
        lista.clear();
        Prova_Questao pquestao = new Prova_Questao();
        BDProva_Questao bdProva_questao = new BDProva_Questao(getContext());
        List<Prova_Questao> prova_questaoList = new ArrayList<>();
        List<Integer> codigosQuestoes = new ArrayList<>();
        prova_questaoList = bdProva_questao.getAllSql();

        for (Prova_Questao provaq: prova_questaoList) {
            if (provaq.getPrq_prvCodigo() == codigo){
                codigosQuestoes.add(provaq.getPrq_queCodigo());
            }
        }

        for (Integer codigos: codigosQuestoes) {
            lista.add(String.valueOf(codigos));
        }

        //criando o adapter customizado
        salv = new AdapterListViewSimples(getContext());
        salv.setLista(lista);

        //setando o adapter customizado ao list
        listView.setAdapter(salv);
    }

    private AlertDialog alerta;

    private void AlertDialogSelList(final int position) {

        //final BdVeiculoUnidade bdVeiculoUnidade = new BdVeiculoUnidade(getContext());

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //define o titulo
        builder.setTitle("Excluir");
        //define a mensagem
        builder.setMessage("Deseja retirar questão da prova?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Prova_Questao pquestao = new Prova_Questao();
                BDProva_Questao bdProva_questao = new BDProva_Questao(getContext());
                bdProva_questao.executeSQL("DELETE FROM prova_questao WHERE prq_prvCodigo = '"+codigo+"' AND prq_queCodigo = '"+lista.get(position)+"';");
                criaLista();
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
