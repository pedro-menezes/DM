package com.example.fernando.menudeslisante;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Created by pedro_menezes on 28/09/17.
 */

public class GaleriaImagensAdapter extends PagerAdapter {

    private Context context;
    private int[] imagens = new int[] { R.drawable.imagem, R.drawable.imagem1, R.drawable.imagem2 };

    GaleriaImagensAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imagens.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup pager, int position, Object object) {
        ((ViewPager) pager).removeView((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup pager, int position) {
        ImageView imagem = new ImageView(context);
        imagem.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imagem.setImageResource(imagens[position]);
        ((ViewPager) pager).addView(imagem, 0);
        return imagem;
    }
}
