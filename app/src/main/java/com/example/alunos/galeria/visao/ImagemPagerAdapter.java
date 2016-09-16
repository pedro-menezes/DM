package com.example.alunos.galeria.visao;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alunos.galeria.R;

import java.util.ArrayList;

public class ImagemPagerAdapter extends PagerAdapter {

    private Context ctx;
    private final Bitmap[] imagens;

    //seta o contexto e as imagens
    public ImagemPagerAdapter(Context c, final Bitmap[] imagens) {
        this.ctx = c;
        this.imagens = imagens;
        //Log.d("PASSOU", "Construtor");
    }

    //retorna a quantidade de elementos da lista de imagens
    @Override
    public int getCount() {
        // Quantidade de views do adapter
        return imagens != null ? imagens.length : 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //Log.d("PASSOU", "inicio item");
        // Infla a view
        View view = LayoutInflater.from(this.ctx).inflate(R.layout.adapter_imagem, container, false);
        //Log.d("PASSOU", "inflar item");
        ImageView img = (ImageView) view.findViewById(R.id.img);
        //Log.d("PASSOU", "identificar imagem item");
        img.setImageBitmap(imagens[position]);
        //Log.d("PASSOU", "setar a imagem item");

        // Adiciona no layout ViewGroup
        ((ViewGroup) container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        // Remove do container
        ((ViewPager) container).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // Determina se a view informada é igual ao object retornado pelo instantiateItem
        return view == object;
    }
}