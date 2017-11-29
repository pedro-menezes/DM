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
import android.widget.ListView;
import android.widget.Toast;

import com.example.fernando.menudeslisante.GerenciarProvaActivity;
import com.example.fernando.menudeslisante.R;
import com.example.fernando.menudeslisante.adapters.AdapterListView;
import com.example.fernando.menudeslisante.bd.BDProva;
import com.example.fernando.menudeslisante.bd.BDProva_Questao;
import com.example.fernando.menudeslisante.beans.Prova;
import com.example.fernando.menudeslisante.beans.Prova_Questao;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaProvasFragment extends Fragment {
    private ListView listView;
    List<String> lista = new ArrayList<String>();
    private int codigo = -1;
    private String nome;
    private List<Prova> provas;
    private BDProva bdProva;

    public ListaProvasFragment() {
        // Required empty public constructor
    }

    public void setFiltroEstabelecimento(int estCodigo, String estNome) {
        this.codigo = estCodigo;

    }

    private AdapterListView salv;   //adaptador, declarado global para aparecer no método
    // de atualização do listView

    private int cod;                        //valor inteiro declarad global para ser atribuido
    // quando o usuário clicar em algum item do listview e
    // será usado no Alerta para deletar elementos da lista

    private String s;                       //valor string declarad global para ser atribuido
    // quando o usuário clicar em algum item do listview e
    // será usado no Alerta para deletar elementos da lista

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //extraio o objeto view para trabalhar com demais componentes no fragment
        View view = inflater.inflate(R.layout.fragment_opcao1, container, false);

        provas = new ArrayList<>();
        bdProva = new BDProva(getContext());
        provas = bdProva.getAllSql();
        // identifica ListView
        listView = (ListView) view.findViewById(R.id.listViewPratos);

        //evento de click na listagem de pratos
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), GerenciarProvaActivity.class);
                intent.putExtra("codigoProva", provas.get(position).getprvCodigo());
                startActivity(intent);
            }
        });

        return view;
    }

    public void onResume() {
        super.onResume();
        criaLista();
    }

    private void criaLista() {
        //criando o adapter customizado
        salv = new AdapterListView(getContext());
        salv.setLista(provas);

        //setando o adapter customizado ao list
        listView.setAdapter(salv);
    }

    private AlertDialog alerta;

    private void AlertDialogSelList(final int position) {

        //final BdVeiculoUnidade bdVeiculoUnidade = new BdVeiculoUnidade(getContext());

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
