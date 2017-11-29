package com.example.fernando.menudeslisante.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fernando.menudeslisante.R;
import com.example.fernando.menudeslisante.beans.Prova;

import java.util.ArrayList;
import java.util.List;

public class AdapterListView extends BaseAdapter {
    private List<Prova> provaList = new ArrayList<>();
     private Context context;

    public void setLista(List<Prova> lista){
        this.provaList = lista;
    }



    public AdapterListView(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return provaList.size();
    }

    @Override
    public Object getItem(int position) {
        return provaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Prova itemLista = provaList.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_listview_item, parent, false);
        TextView t = (TextView) view.findViewById(R.id.textNome);
        TextView t1 = (TextView) view.findViewById(R.id.textCodigo);
        TextView t2 = (TextView) view.findViewById(R.id.textNumQuestoes);
        t.setText(itemLista.getprvNome());
        t1.setText(String.valueOf(itemLista.getprvCodigo()));
        t2.setText(String.valueOf(itemLista.numeroQuestoes(context)));
        return view;
    }
}
