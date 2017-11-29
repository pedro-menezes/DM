package com.example.fernando.menudeslisante;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fernando.menudeslisante.bd.BDAlternativa;
import com.example.fernando.menudeslisante.bd.BDTema;
import com.example.fernando.menudeslisante.bd.BdQuestao;
import com.example.fernando.menudeslisante.beans.Alternativa;
import com.example.fernando.menudeslisante.beans.Questao;
import com.example.fernando.menudeslisante.beans.Tema;
import com.example.fernando.menudeslisante.sinc.JSONDados;
import com.example.fernando.menudeslisante.sinc.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.fernando.menudeslisante.sinc.JSONDados.URL_SERVICO1;

public class LoginActivity extends Activity {
    private EditText etN1, etN2, etResp;
    private String n1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        etN1 = (EditText) findViewById(R.id.editUsuario);
        etN2 = (EditText) findViewById(R.id.editSenha);

        n1="1";
    }

    ProgressDialog pDialog;

    public void onCLick(View v) {
        //chama o metodo de requisição passando o json como parametro e a URL do webservice

        Intent it = new Intent(getApplicationContext(),TelaSplashActivity.class);
        startActivity(it);
        requisitaPost(
                JSONDados.geraJsonUsuario(
                        etN1.getText().toString(),
                        etN2.getText().toString()
                ),
                URL_SERVICO1
        );
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
    public void requisitaPost(final String parametroJSON, final String URL_) {


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
                    final String resp = interpretaJSON_Aritimetica(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(resp.equals("true")){

                                        requisitaPostBD(  " ",
                                                "https://provafacil.000webhostapp.com/REST/getTema.php", 1);
                                        requisitaPostBD(  " ",
                                                "https://provafacil.000webhostapp.com/REST/getTodasQuestoes.php", 2);
                                        requisitaPostBD(  " ",
                                        "https://provafacil.000webhostapp.com/REST/getAlternativas.php", 3);

                                Intent it = new Intent(getApplicationContext(),MenuActivity.class);
                                startActivity(it);

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Falha no Login!!!", Toast.LENGTH_LONG).show();

                            }
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


    /**
     * Método criado para receber, interpretar o obj json e retornar uma string formatada do mesmo
     *
     * @param json
     * @return string formatada
     */

    public String interpretaJSON_Aritimetica(JSONObject json) {
        String texto = "";
        try {
            JSONArray linhas = null;
            //Printando na string os elementos identificados nela
            try {
                linhas = (JSONArray) json.get("logar");//pega vetor do json recebido
                if (linhas.length() > 0) {//verifica we exite algum registro recebido do servidor
                    for (int i = 0; i < linhas.length(); i++) {
                        JSONObject linha = (JSONObject) linhas.get(i);
                        texto +=  linha.getBoolean("logar");
//                        texto += "Soma: " + linha.getInt("soma") + ", ";
//                        texto += "Subtracao: " + linha.getInt("subtracao") + ", ";
//                        texto += "Multiplicacao: " + linha.getString("multiplicacao") + ".\n";
                        Log.d("[IFMG]", "resultado: " + texto);
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

    public static ProgressDialog gerarDialogIndeterminado(String mensagem, Context activityContexto) {
        ProgressDialog pDialog = new ProgressDialog(activityContexto);
        pDialog.setMessage(mensagem);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        //pDialog.show();
        return pDialog;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

