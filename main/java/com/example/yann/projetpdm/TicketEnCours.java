package com.example.yann.projetpdm;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.yann.projetpdm.Helper.DateHelper;
import com.example.yann.projetpdm.Helper.TimeHelper;
import com.example.yann.projetpdm.classes.Personne;
import com.example.yann.projetpdm.classes.Ticket;
import com.example.yann.projetpdm.classes.Voiture;
import com.example.yann.projetpdm.classes.Zone;

import java.util.ArrayList;
import java.util.Date;

public class TicketEnCours extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Personne personneEnCours;
    private Ticket ticketEnCours;
    private TextView txtImmat;
    private TextView txtRemainTime;
    private TextView txtParking;
    private Button btnTimePicker;
    private TextView txtHeureFinDialog;
    private Button btnOk;
    private NumberPicker hourPicker;
    private NumberPicker minPicker;
    private AlertDialog dialog;
    private Button btnCancel;
    private TextView txtHeureFin;
    private Button btn15Min;
    private Button btn30Min;
    private Button btn1H;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ticket_en_cours);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        personneEnCours = new Personne(getApplicationContext(), Long.valueOf(1));
        initControls();
                /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


    private void initControls(){
        getControls();
        initText();
        initTimer();
    }

    private long getStartedValue(){
        long time = ticketEnCours.getTempsRestantMs();
        return time;
    }

    private void initText(){
        ArrayList<Ticket> lTV = personneEnCours.getTicketsValides();
        if(lTV.size() >= 1) {
            ticketEnCours = lTV.get(0);
        } else if (lTV.size() < 1){
            Intent intent = new Intent(TicketEnCours.this, MainActivity.class);  //Lancer l'activité DisplayVue
            startActivity(intent);
        }
        Voiture voiture = new Voiture(getApplicationContext(), ticketEnCours.getIdVoiture());
        Zone zone = new Zone(getApplicationContext(), ticketEnCours.getIdZone());
        txtImmat.setText(voiture.getImmatriculation());
        txtParking.setText(zone.getNom());

        initTextTime();
    }

    public void initTextTime(){
        txtRemainTime.setText(TimeHelper.formatAffichageHeure(ticketEnCours.getTempsRestantMs()));
        Date dateFin = DateHelper.convertMillisecondsToDate(ticketEnCours.getDateFin());
        txtHeureFin.setText(dateFin.getHours() + ":" + dateFin.getMinutes());
    }

    private void initTimer(){
        Intent service = new Intent(TicketEnCours.this, NotifyService.class);
        service.putExtra(NotifyService.KEY_EXTRA_LONG_TIME_IN_MS, getStartedValue());
        startService(service);
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

    @Override
    protected void onPause() {
        unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, new IntentFilter(NotifyService.REFRESH_TIME_INTENT));
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(TicketEnCours.this, NotifyService.class));
        super.onDestroy();
    }

    public void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long timeInMs = intent.getLongExtra(NotifyService.KEY_EXTRA_LONG_TIME_IN_MS, 0);
            Integer[] minSec = TimeHelper.getMinSec(timeInMs);
            initTextTime();
        }
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(NotifyService.REFRESH_TIME_INTENT)) {
                updateGUI(intent);
            }
        }
    };

    private void getControls(){
        txtImmat = (TextView)findViewById(R.id.ticket_en_cours_lblImmatVoiture);
        txtRemainTime = (TextView)findViewById(R.id.ticket_en_cours_temps);
        txtParking = (TextView) findViewById(R.id.ticket_en_cours_NomZone);
        btnTimePicker=(Button)findViewById(R.id.ticket_en_cours_btn_time);
        btnTimePicker.setOnClickListener(this);
        txtHeureFin = (TextView) findViewById(R.id.ticket_en_cours_txtHeureFin);
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == btnTimePicker) {
            initDialog();
        }
        if (v == btnOk) {
            long hour = hourPicker.getValue();
            long minutes = minPicker.getValue();
            ticketEnCours.setDureeSupp(ticketEnCours.getDureeSupp() + TimeHelper.hourToMinutes(hour) + minutes);
            ticketEnCours.enregistrer();
            stopDialog();
            initControls();
        }
        if (v == btnCancel) {
            stopDialog();
            initControls();
        }
        if (v == btn15Min) {
            ticketEnCours.setDureeSupp(ticketEnCours.getDureeSupp() + 15);
            ticketEnCours.enregistrer();
            stopDialog();
            initControls();
        }
        if (v == btn30Min) {
            ticketEnCours.setDureeSupp(ticketEnCours.getDureeSupp() + 30);
            ticketEnCours.enregistrer();
            stopDialog();
            initControls();
        }
        if (v == btn1H) {
            ticketEnCours.setDureeSupp(ticketEnCours.getDureeSupp() + 60);
            ticketEnCours.enregistrer();
            stopDialog();
            initControls();
        }
    }





/////////////////////////////DIALOG//////////////////////////////////////////////////////////////////

    private void initDialog(){
        View npView = getLayoutInflater().inflate(R.layout.number_picker_dialog_layout, null);

        hourPicker = (NumberPicker) npView.findViewById(R.id.number_picker_dialog_layout_nbPckH);
        minPicker = (NumberPicker) npView.findViewById(R.id.number_picker_dialog_layout_nbPckMin);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        txtHeureFinDialog = (TextView) npView.findViewById(R.id.number_picker_dialog_layout_txtHeureFin);
        btnCancel = (Button) npView.findViewById(R.id.number_picker_dialog_layout_btnCancel);
        btnOk = (Button) npView.findViewById(R.id.number_picker_dialog_layout_btnValider);
        btn15Min = (Button) npView.findViewById(R.id.number_picker_dialog_layout_15Min);
        btn30Min = (Button) npView.findViewById(R.id.number_picker_dialog_layout_30MIN);
        btn1H = (Button) npView.findViewById(R.id.number_picker_dialog_layout_1H);

        hourPicker.setMaxValue(TimeHelper.MAX_HOUR);
        hourPicker.setMinValue(0);

        minPicker.setMaxValue(TimeHelper.MAX_MIN);
        minPicker.setMinValue(0);

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btn15Min.setOnClickListener(this);
        btn30Min.setOnClickListener(this);
        btn1H.setOnClickListener(this);
        updateHeureFinDialog();

        builder.setTitle("Prolonger:");
        builder.setView(npView);

        dialog = builder.create();
        dialog.show();
    }

    public void stopDialog(){
        dialog.dismiss();
    }

    private void updateHeureFinDialog(){
        Date dateFin = DateHelper.convertMillisecondsToDate(ticketEnCours.getDateFin());
        txtHeureFinDialog.setText(dateFin.getHours() + ":" + dateFin.getMinutes());
    }

}
