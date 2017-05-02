package com.example.yann.projetpdm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yann.projetpdm.Utils.ListeVoitureAdapter;
import com.example.yann.projetpdm.classes.Personne;
import com.example.yann.projetpdm.classes.Voiture;
import com.example.yann.projetpdm.persistence.MyApp;

import java.util.List;

public class ListeVoitures extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    ListView listView;
    Personne personneEnCours;
    private ListeVoitureAdapter adapter;
    public final static String EXTRA_MESSAGE =
            "PARAM.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_voitures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(ListeVoitures.this, CreationVoiture.class);
            startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        if(!Personne.dejaConnecte(getApplication(),getApplicationContext())){
            Intent intent = new Intent(ListeVoitures.this, LoginActivity.class);
            startActivity(intent);
        }

        List<String> connexions = ((MyApp)getApplication()).getStorageService().restore(getApplicationContext());
        personneEnCours = new Personne(getApplicationContext(), Long.valueOf(connexions.get(0)));

        listView = (ListView) findViewById(R.id.liste_voitures_liste);
        List<Voiture> tableaumodifiable = Voiture.getVoituresConducteur(getApplicationContext(), personneEnCours.getId());
        Voiture[] voitures = new Voiture[tableaumodifiable.size()];
        int i = 0;
        for(Voiture v : tableaumodifiable){
            voitures[i] = v;
            i++;
        }
        adapter = new ListeVoitureAdapter(this, voitures);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CallFunc(position);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void CallFunc(int position) {
        Voiture voiture = adapter.getListe(position);
        Intent i1 = new Intent( ListeVoitures.this, VueVoiture.class );
        i1.putExtra(EXTRA_MESSAGE, voiture.getId()+"");
        startActivityForResult(i1, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            ((MyApp)getApplication()).getStorageService().clear(getApplicationContext());
            Intent i = new Intent(this,LoginActivity.class);
            this.startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tickets) {
            Intent intent = new Intent(ListeVoitures.this, ListeTickets.class);
            startActivity(intent);
        } else if (id == R.id.nav_voitures) {
            Intent intent = new Intent(ListeVoitures.this, ListeVoitures.class);
            startActivity(intent);
        }else if (id == R.id.nav_ticket){
            if(personneEnCours.aTicketEnCours()){
                Intent intent = new Intent(ListeVoitures.this, TicketEnCours.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(ListeVoitures.this, MainActivity.class);
                startActivity(intent);
            }
        }else if (id == R.id.nav_compte){
            Intent intent = new Intent(ListeVoitures.this, MonCompte.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
