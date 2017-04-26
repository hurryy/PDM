package com.example.yann.projetpdm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.yann.projetpdm.classes.Personne;
import com.example.yann.projetpdm.classes.Ticket;
import com.example.yann.projetpdm.classes.Voiture;

import java.util.ArrayList;

public class TicketEnCours extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Personne personneEnCours;
    private Ticket ticketEnCours;
    /*TextView txtImmat;
    TextView txtTime;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_en_cours);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txtImmat = (TextView)findViewById(R.id.ticket_en_cours_lblImmatVoiture);
        TextView txtTime = (TextView)findViewById(R.id.ticket_en_cours_temps);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        personneEnCours = new Personne(getApplicationContext(), Long.valueOf(1));
        initControls(txtImmat, txtTime);
    }

    private void initControls(TextView txtImmat, TextView txtTime){
        ArrayList<Ticket> lTV = personneEnCours.getTicketsValides();

        if(lTV.size() >= 1) {
            ticketEnCours = lTV.get(0);
        } else if (lTV.size() < 1){
            Intent intent = new Intent(TicketEnCours.this, MainActivity.class);  //Lancer l'activité DisplayVue
            startActivity(intent);
        }
        Voiture voiture = new Voiture(getApplicationContext(), ticketEnCours.getIdVoiture());
        txtImmat.setText(voiture.getImmatriculation());
        txtTime.setText(String.valueOf(ticketEnCours.getTempsRestant()));
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
        getMenuInflater().inflate(R.menu.ticket_en_cours, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
