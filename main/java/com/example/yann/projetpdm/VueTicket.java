package com.example.yann.projetpdm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.yann.projetpdm.Utils.DateHelper;
import com.example.yann.projetpdm.classes.Personne;
import com.example.yann.projetpdm.classes.Ticket;
import com.example.yann.projetpdm.classes.Voiture;
import com.example.yann.projetpdm.classes.Zone;
import com.example.yann.projetpdm.persistence.MyApp;

import java.util.List;

public class VueTicket extends AppCompatActivity implements View.OnClickListener {
    EditText edit_dateDemande;
    EditText edit_dateFin;
    EditText edit_prix;
    EditText edit_voiture;
    EditText edit_zone;
    Personne personneEnCours;
    Ticket ticket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_ticket);

        if(!Personne.dejaConnecte(getApplication(),getApplicationContext())){
            Intent intent = new Intent(VueTicket.this, LoginActivity.class);
            startActivity(intent);
        }
        List<String> connexions = ((MyApp)getApplication()).getStorageService().restore(getApplicationContext());
        personneEnCours = new Personne(getApplicationContext(), Long.valueOf(connexions.get(0)));

        // intent
        final Intent intent = getIntent();
        String message = intent.getStringExtra(ListeTickets.EXTRA_MESSAGE);
        ticket = new Ticket(getApplicationContext(), Long.valueOf(message));
    }
    @Override
    public void onResume(){
        super.onResume();
        edit_dateDemande = (EditText) findViewById(R.id.vue_ticket_DateDemande);
        edit_dateFin = (EditText) findViewById(R.id.vue_ticket_DateFin);
        edit_prix = (EditText) findViewById(R.id.vue_ticket_Prix);
        edit_voiture = (EditText) findViewById(R.id.vue_ticket_Voiture);
        edit_zone = (EditText) findViewById(R.id.vue_ticket_Zone);

        edit_dateDemande.setText(DateHelper.dateJour(ticket.getDateDemande()));
        edit_dateDemande.setEnabled(false);
        edit_dateDemande.setOnClickListener(this);
        edit_dateFin.setText(DateHelper.dateJour(ticket.getDateFin()));
        edit_dateFin.setEnabled(false);
        edit_dateFin.setOnClickListener(this);
        edit_prix.setText(ticket.getCoutTotal()+" €");
        edit_prix.setEnabled(false);
        edit_prix.setOnClickListener(this);
        edit_voiture.setText(new Voiture(getApplicationContext(),ticket.getIdVoiture()).getImmatriculation());
        edit_voiture.setEnabled(false);
        edit_voiture.setOnClickListener(this);
        edit_zone.setText(new Zone(getApplicationContext(), ticket.getIdZone()).getNom());
        edit_zone.setEnabled(false);
        edit_zone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == edit_dateDemande){

        }
        if(v == edit_dateFin){

        }
        if(v == edit_prix){

        }
        if(v == edit_voiture){

        }
        if(v == edit_zone){

        }
    }
}
