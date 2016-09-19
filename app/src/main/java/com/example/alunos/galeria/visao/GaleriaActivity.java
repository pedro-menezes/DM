package com.example.alunos.galeria.visao;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alunos.galeria.R;
import com.example.alunos.galeria.controle.ImagemDB;
import com.example.alunos.galeria.modelo.Imagem;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class GaleriaActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private Bitmap[] vetorImagem ;
    private ArrayList<Imagem> vetorImagemBD = new ArrayList<>();
    private ImagemDB ig;
    private Imagem im;
    private ViewPager g;
    private TextView tvNome, tvDescricao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        //criando imagens pra teste
      /*  Imagem i1 = new Imagem();
        i1.setNome("Nome 1");
        i1.setDescricao("Desc 1");
        i1.setUrl("http://www.sitedeimagens.com/imagens/diversas-imagens-do-cruzeiro-10.jpg");
        Imagem i2 = new Imagem();
        i2.setNome("Nome 2");
        i2.setDescricao("Desc 2");
        i2.setUrl("http://2.bp.blogspot.com/-oyl_Ngsh4Xs/VpjihFNzmWI/AAAAAAAAOUQ/QVuBc6DNtCg/s1600/cruzeiro-tabela-2016.jpg");
        Imagem i3 = new Imagem();
        i3.setNome("Nome 3");
        i3.setDescricao("Desc 3");
        i3.setUrl("http://www.fabricadebandeiras.com.br/img/products/bandeira-cruzeiro-2_1_630.jpg");*/

        tvNome = (TextView) findViewById(R.id.textView4);
        tvDescricao = (TextView) findViewById(R.id.textView5);
        ig = new ImagemDB(getBaseContext());

       /* ig.save(i1);
        ig.save(i2);
        ig.save(i3);*/
        im = new Imagem();
        vetorImagemBD = ig.findAll();
        vetorImagem = new Bitmap[vetorImagemBD.size()];
        downloadTodasImagens();
        //identifica o ViewPager
        g = (ViewPager) findViewById(R.id.viewPager);

        //seta as ações dos eventos
        g.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //mostra o nome da imagem em um toast
                tvNome.setText("Nome: "+ vetorImagemBD.get(position).getNome());
                tvDescricao.setText("Descrição: " + vetorImagemBD.get(position).getNome());

            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    // Faz o download da imagem em uma nova Thread
    private void downloadTodasImagens() {
        new Thread() {
            @Override
            public void run() {
                int cont = 0;
                for (Imagem img: vetorImagemBD) {
                    try {
                        // Faz o download da imagem
                        URL urlx = new URL(img.getUrl());
                        InputStream in = urlx.openStream();
                        // Converte a InputStream do Java para Bitmap
                        final Bitmap imagem = BitmapFactory.decodeStream(in);
                        in.close();
                        vetorImagem[cont] = imagem;
                        cont++;
                    } catch (IOException e) {
                        // Uma aplicacao real deveria tratar este erro
                        Log.e("ERRO", e.getMessage(), e);
                    }
                }
                chamaAdapter(vetorImagem);
            }

        }.start();



    }

    private void chamaAdapter(final Bitmap[] vetorImagem) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //seta o adaptador
                g.setAdapter(new ImagemPagerAdapter(getBaseContext(), vetorImagem));
                g.refreshDrawableState();
                tvNome.setText("Nome: "+ vetorImagemBD.get(0).getNome());
                tvDescricao.setText("Descrição: " + vetorImagemBD.get(0).getNome());

            }
        });

    }




}
