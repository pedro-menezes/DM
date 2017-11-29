package com.example.fernando.menudeslisante;

/**
 * Created by pedro-menezes on 25/11/17.
 */
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

public class TelaSplashActivity extends AppCompatActivity {

    private ImageView splash;
    AnimationDrawable splashAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_splash);

        splash = (ImageView) findViewById(R.id.ivAnimacao);// imageWiew no layout
        splash.setBackgroundResource(R.drawable.splash_animation);//drawable construido para animaçao

        //inicia processamento paralelo a thread
        new InsertAsync().execute("");
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //inicia animação na splash, na thread da interface gráfica mesmo
        splashAnimation = (AnimationDrawable) splash.getBackground();
        if (hasFocus) {
            splashAnimation.start();
        } else {
            splashAnimation.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int tempoSplash = 5000;//5 segundos

        //cria delay para entrar na proxima activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Fecha Activity atual
                finish();
            }
        }, tempoSplash);

    }

    /* -------------------------------------------------------
    SUBCLASSE RESPONSÁVEL POR CRIAR A SEGUNDA THREAD, OBJETIVANDO PROCESSAMENTO
    PARALELO AO DA THREAD DA INTERFACE GRÁFICA
     ----------------------------------------------------------*/
    class InsertAsync extends AsyncTask<String, String, String> {
        //método executado antes do método da segunda thread doInBackground
        @Override
        protected void onPreExecute() {

        }

        //método que será executado em outra thread
        @Override
        protected String doInBackground(String... args) {
            int j = 0;
            for (int i = 0; i < 1000; i++) {
                j++;
            }
            return j + "";
        }

        //método executado depois da thread do doInBackground
        @Override
        protected void onPostExecute(String retorno) {
            //manda mensagem na tela para dizer que já executou a segunda thread
            Toast.makeText(getApplicationContext(), "Executou, j=" + retorno, Toast.LENGTH_LONG).show();
        }
    }
}


