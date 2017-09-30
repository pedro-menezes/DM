package com.example.fernando.menudeslisante;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        ViewPager galeria = (ViewPager) findViewById(R.id.galeria);
        GaleriaImagensAdapter adapter = new GaleriaImagensAdapter(this);
        galeria.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        //não fazer nada com botão voltar pressionado
    }

    public void onClickCadastrar(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://provafacil.000webhostapp.com/"));
        startActivity(intent);
    }

    public void onClickLogin(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
