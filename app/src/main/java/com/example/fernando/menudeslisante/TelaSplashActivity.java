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
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fernando.menudeslisante.bd.BDAlternativa;
import com.example.fernando.menudeslisante.bd.BDTema;
import com.example.fernando.menudeslisante.bd.BdQuestao;
import com.example.fernando.menudeslisante.beans.Alternativa;
import com.example.fernando.menudeslisante.beans.Questao;
import com.example.fernando.menudeslisante.beans.Tema;
import com.example.fernando.menudeslisante.sinc.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

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
        public String doInBackground(String... args) {
            requisitaPostBD(  " ",
                    "https://provafacil.000webhostapp.com/REST/getTema.php", 1);
            requisitaPostBD(  " ",
                    "https://provafacil.000webhostapp.com/REST/getTodasQuestoes.php", 2);
            requisitaPostBD(  " ",
                    "https://provafacil.000webhostapp.com/REST/getAlternativas.php", 3);
            return null;
        }


        //método executado depois da thread do doInBackground
        @Override
        protected void onPostExecute(String retorno) {
            //manda mensagem na tela para dizer que já executou a segunda thread
            Toast.makeText(getApplicationContext(), "Executou, j=" + retorno, Toast.LENGTH_LONG).show();
            }
    }
    public void requisitaPostBD(final String parametroJSON, final String URL_,final int tipo) {


        //thread obrigatória para realização da requisição pode ser usado com outras formas de thread
        new Thread(new Runnable() {
            public void run() {
                JSONParser jsonParser = new JSONParser();
                JSONObject json = null;
                try {
                    //prepara parâmetros para serem enviados via método POST
                    HashMap<String, String> params = new HashMap<>();
                    params.put("dados", parametroJSON);

                    Log.d("[IFMG]", parametroJSON);
                    Log.d("[IFMG]", "JSON Envio Iniciando...");

                    //faz a requisição POST e retorna o que o webservice REST envoiu dentro de json
                    json = jsonParser.makeHttpRequest(URL_, "POST", params);

                    Log.d("[IFMG]", " JSON Envio Terminado...");

                    //Mostra no log e retorna o que o json retornou, caso não retornou nulo
                    if (json != null) {
                        Log.d("[IFMG]", json.toString());
                        //return json;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("[IFMG]", "finalizando baixar defeitos");

                //----------------------------------------------
                //PÓS DOWNLOAD
                //----------------------------------------------

                //teste para ferificar se o json chegou corretamente e foi interpretado
                if (json != null) {
                    //------------------------------------------------------------
                    //AQUI SE PEGA O JSON RETORNADO E TRATA O QUE DEVE SER TRATADO
                    //------------------------------------------------------------
                    JSONObject json2 = json;
                    if (tipo == 1) {
                        interpretaJSON_AritimeticaTema(json2);
                    } else  if (tipo == 2) {
                        interpretaJSON_AritimeticaQuestao(json2);
                    } else  if (tipo == 3) {
                        interpretaJSON_AritimeticaAlternativa(json2);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Falha na conexão!!!", Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        }).start();
    }

    public String interpretaJSON_AritimeticaTema(JSONObject json) {
        String texto = "";

        try {
            JSONArray linhas = null;
            //Printando na string os elementos identificados nela
            try {
                linhas = (JSONArray) json.get("pesquisar");//pega vetor do json recebido
                if (linhas.length() > 0) {//verifica we exite algum registro recebido do servidor
                    for (int i = 0; i < linhas.length(); i++) {
                        JSONObject linha = (JSONObject) linhas.get(i);
                        BDTema bdTema = new BDTema(this);
                        Tema tema = new Tema();

                        tema.settemCodigo(Integer.parseInt(linha.getString("temCodigo")));
                        tema.settemNome(linha.getString("temNome"));

                        bdTema.insertTema(tema);
                        Log.d("[IFMG]", "resultado: " + tema.toString());
                    }
                }
            } catch (Exception c) {
                c.printStackTrace();
                Log.d("[IFMG]", "Erro: " + c.getMessage());
            }
        } catch (Exception e) {//JSONException e) {
            e.printStackTrace();
        }
        return texto;
    }

    public String interpretaJSON_AritimeticaQuestao(JSONObject json) {
        String texto = "";

        try {
            JSONArray linhas = null;
            //Printando na string os elementos identificados nela
            try {
                linhas = (JSONArray) json.get("pesquisar");//pega vetor do json recebido
                if (linhas.length() > 0) {//verifica we exite algum registro recebido do servidor
                    for (int i = 0; i < linhas.length(); i++) {
                        JSONObject linha = (JSONObject) linhas.get(i);
                        BdQuestao bdQuestao = new BdQuestao(this);
                        Questao questao = new Questao();

                        questao.setqueCodigo(Integer.parseInt(linha.getString("queCodigo")));
                        questao.setqueEnunciado(linha.getString("queEnunciado"));
                        questao.setque_temCodigo(Integer.parseInt(linha.getString("que_temCodigo")));

                        bdQuestao.insertQuestao(questao);
                        Log.d("[IFMG]", "resultado: " + questao.toString());
                    }
                }
            } catch (Exception c) {
                c.printStackTrace();
                Log.d("[IFMG]", "Erro: " + c.getMessage());
            }
        } catch (Exception e) {//JSONException e) {
            e.printStackTrace();
        }
        return texto;
    }

    public String interpretaJSON_AritimeticaAlternativa(JSONObject json) {
        String texto = "";

        try {
            JSONArray linhas = null;
            //Printando na string os elementos identificados nela
            try {
                linhas = (JSONArray) json.get("pesquisar");//pega vetor do json recebido
                if (linhas.length() > 0) {//verifica we exite algum registro recebido do servidor
                    for (int i = 0; i < linhas.length(); i++) {
                        JSONObject linha = (JSONObject) linhas.get(i);
                        BDAlternativa bdAlternativa = new BDAlternativa(this);
                        Alternativa alternativa = new Alternativa();

                        alternativa.setAltCodigo(Integer.parseInt(linha.getString("altCodigo")));
                        alternativa.setAltEnunciado(linha.getString("altText"));
                        alternativa.setAlt_queCodigo(Integer.parseInt(linha.getString("alt_queCodigo")));
                        alternativa.setAltCorreta(Integer.parseInt(linha.getString("altCorreta")));
                        bdAlternativa.insertAlternativa(alternativa);
                        Log.d("[IFMG]", "resultado: " + alternativa.toString());
                        Intent it = new Intent(getApplicationContext(),MenuActivity.class);
                        startActivity(it);
                    }
                }
            } catch (Exception c) {
                c.printStackTrace();
                Log.d("[IFMG]", "Erro: " + c.getMessage());
            }
        } catch (Exception e) {//JSONException e) {
            e.printStackTrace();
        }
        return texto;
    }
}


