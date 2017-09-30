package com.example.fernando.menudeslisante;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fernando.menudeslisante.view.fragments.Opcao2Fragment;
import com.example.fernando.menudeslisante.view.fragments.Opcao1Fragment;
import com.example.fernando.menudeslisante.view.fragments.WelcomeFragment;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //fixa o layout vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //seta o fragment inicial
        replaceFragment(new WelcomeFragment());
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        replaceFragment(new WelcomeFragment());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //evento do menu  no  canto superiror direito
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_sobre):
                Toast.makeText(getApplicationContext(), "Teste!", Toast.LENGTH_LONG).show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickPlus(View v){
        Opcao2Fragment op2= new Opcao2Fragment();
        replaceFragment(op2);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_opcao1:
                Opcao1Fragment op1= new Opcao1Fragment();
                replaceFragment(op1);
                //Toast.makeText(getApplicationContext(), "Teste1!", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_opcao2:
                Opcao2Fragment op2= new Opcao2Fragment();
                replaceFragment(op2);
                //Toast.makeText(getApplicationContext(), "Teste2!", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_opcao3:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://provafacil.000webhostapp.com/"));
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "Teste3!", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_sair:
                Intent itn = new Intent(this, MainActivity.class);
                startActivity(itn);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragments, fragment, "TAG").addToBackStack(null).commit();
    }
}
