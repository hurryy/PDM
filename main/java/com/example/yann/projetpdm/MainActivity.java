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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.yann.projetpdm.Helper.DateHelper;
import com.example.yann.projetpdm.Helper.TimeHelper;
import com.example.yann.projetpdm.classes.Personne;
import com.example.yann.projetpdm.classes.Ticket;
import com.example.yann.projetpdm.classes.Voiture;
import com.example.yann.projetpdm.classes.Zone;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Zone> zones;
    private ArrayList<Voiture> voitures;
    Personne personneEnCours;
    //public int etat =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (Personne.getConducteurs(getApplicationContext()).size() <= 0) {
            Personne p12 = new Personne(getApplicationContext(), "Test", "test", "0606060606", "test@mail.com", "test", Personne.CONDUCTEUR);
            Zone z1 = new Zone(getApplicationContext(), "parking UJF", 1.2f, "8:00", "18:00");
            Zone z2 = new Zone(getApplicationContext(), "parking UGA", 0.5f, "9:00", "19:00");
            Voiture v1 = new Voiture(getApplicationContext(), "EE-666-EE", "Karl", "Opel", "", "52", 1);
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        personneEnCours = new Personne(getApplicationContext(), Long.valueOf(1));
        if(personneEnCours.aTicketEnCours()) {
            Intent intent = new Intent(MainActivity.this, TicketEnCours.class);  //Lancer l'activité DisplayVue
            startActivity(intent);    //Afficher la vue
        }
        initControls();


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

    private void initControls() {
        initNumberPickers();
        initSpinners();
        initButtonActions();
    }

    private void initText(String heureDebut, String heureFin) {
        TextView txtPayantHeureDebut = (TextView) findViewById(R.id.txtPayantHeureDebut);
        TextView txtPayantHeureFin = (TextView) findViewById(R.id.txtPayantHeureFin);
        if (heureDebut == null)
            txtPayantHeureDebut.setText("00:00");
        else
            txtPayantHeureDebut.setText(heureDebut);
        if (heureFin == null)
            txtPayantHeureFin.setText("00:00");
        else
            txtPayantHeureFin.setText(heureFin);
    }

    private void initNumberPickers() {
        NumberPicker nbPckH = (NumberPicker) findViewById(R.id.nbPckH);
        nbPckH.setMinValue(0);
        nbPckH.setMaxValue(TimeHelper.MAX_HOUR);
        nbPckH.setValue(0);
        NumberPicker nbPckMin = (NumberPicker) findViewById(R.id.nbPckMin);
        nbPckMin.setMinValue(0);
        nbPckMin.setMaxValue(TimeHelper.MAX_MIN);
        nbPckMin.setValue(30);
    }

    private void initSpinners() {

        Spinner spnVoiture = (Spinner) findViewById(R.id.spnVoiture);
        Spinner spnZone = (Spinner) findViewById(R.id.spnZone);
        Personne p1 = Personne.getConducteurs(getApplicationContext()).get(0);
        voitures = Voiture.getVoituresConducteur(getApplicationContext(), p1.getId());
        zones = Zone.getZones(getApplicationContext());
        final ArrayList<Zone> zs = zones;
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> list2 = new ArrayList<>();

        for (Voiture v : voitures) {
            list.add(v.getImmatriculation());
        }
        for (Zone p : zs) {
            list2.add(p.getNom());
        }
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list2);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnVoiture.setAdapter(adp1);
        spnZone.setAdapter(adp2);

        if (zs.size() > 0) {
            Zone z = zs.get(0);
            initText(z.getHeurePayanteDebut(), z.getHeurePayanteFin());
        } else
            initText(null, null);

        spnZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Zone z = zs.get(position);
                initText(z.getHeurePayanteDebut(), z.getHeurePayanteFin());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void initButtonActions() {
        Button btnDemarrer = (Button) findViewById(R.id.btnDemarrer);
        btnDemarrer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Spinner spnVoiture = (Spinner) findViewById(R.id.spnVoiture);
                Spinner spnZone = (Spinner) findViewById(R.id.spnZone);
                NumberPicker nbPckH = (NumberPicker) findViewById(R.id.nbPckH);
                NumberPicker nbPckMin = (NumberPicker) findViewById(R.id.nbPckMin);

                Ticket ticket = new Ticket(getApplicationContext());
                ticket.setIdVoiture(voitures.get(spnVoiture.getSelectedItemPosition()).getId());
                ticket.setIdZone(zones.get(spnZone.getSelectedItemPosition()).getId());
                ticket.setDureeInitiale(nbPckH.getValue() * 60 + nbPckMin.getValue());
                ticket.setDureeSupp(0);
                SimpleDateFormat dateFormat = DateHelper.getSimpleDateFormat();
                Long dateDemande = new Date().getTime();
                ticket.setDateDemande(dateDemande);
                ticket.setHeureDebut(dateDemande);
                ticket.setCoutTotal(zones.get(spnZone.getSelectedItemPosition()).getTarifHoraire() * (ticket.getDureeInitiale() / 60));
                ticket.enregistrer();
                lunchTicketEnCours();
            }
        });
    }
    private void lunchTicketEnCours(){
        Intent intent = new Intent(MainActivity.this, TicketEnCours.class);  //Lancer l'activité DisplayVue
        startActivity(intent);    //Afficher la vue
    }
}
