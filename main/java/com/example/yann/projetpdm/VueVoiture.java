package com.example.yann.projetpdm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.yann.projetpdm.classes.Personne;
import com.example.yann.projetpdm.classes.Voiture;
import com.example.yann.projetpdm.persistence.MyApp;

import java.util.List;

public class VueVoiture extends AppCompatActivity implements View.OnClickListener {
    private Personne personneEnCours;
    private Voiture voiture;
    private EditText edit_immat;
    private EditText edit_modele;
    private EditText edit_marque;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_voiture);


        if(!Personne.dejaConnecte(getApplication(),getApplicationContext())){
            Intent intent = new Intent(VueVoiture.this, LoginActivity.class);
            startActivity(intent);
        }
        List<String> connexions = ((MyApp)getApplication()).getStorageService().restore(getApplicationContext());
        personneEnCours = new Personne(getApplicationContext(), Long.valueOf(connexions.get(0)));
        edit_immat = (EditText) findViewById(R.id.vue_voiture_immatriculation);
        edit_modele = (EditText) findViewById(R.id.vue_voiture_model);
        edit_marque = (EditText) findViewById(R.id.vue_voiture_marque);
        edit_modele.setOnClickListener(this);
        edit_immat.setOnClickListener(this);
        edit_marque.setOnClickListener(this);
    }
    @Override
    public void onResume(){
        super.onResume();


        // intent
        final Intent intent = getIntent();
        String message = intent.getStringExtra(ListeTickets.EXTRA_MESSAGE);
        voiture = new Voiture(getApplicationContext(), Long.valueOf(message));
        initText();
    }

    public void initText(){
        edit_immat.setText(voiture.getImmatriculation());
        edit_marque.setText(voiture.getMarque());
        edit_modele.setText(voiture.getModel());
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        if(v == edit_immat){
            alert.setTitle("Immatriculation");
            alert.setMessage("");
            // Create TextView
            final EditText input = new EditText (this);
            input.setHint("Nouvelle immatriculation");
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(input.getText().toString().trim().isEmpty())
                        input.setError("Champs vide");
                    else {
                        voiture.setImmatriculation(input.getText().toString().trim());
                        voiture.enregistrer();
                        initText();
                    }
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });
            alert.show();
        }
        if(v == edit_modele){
            alert.setTitle("Modèle");
            alert.setMessage("");
            // Create TextView
            final EditText input = new EditText (this);
            input.setHint("Modèle");
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(input.getText().toString().trim().isEmpty())
                        input.setError("Champs vide");
                    else {
                        voiture.setModel(input.getText().toString().trim());
                        voiture.enregistrer();
                        initText();
                    }
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });
            alert.show();
        }
        if(v == edit_marque){
            alert.setTitle("Marque");
            alert.setMessage("");
            // Create TextView
            final EditText input = new EditText (this);
            input.setHint("Marque");
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(input.getText().toString().trim().isEmpty())
                        input.setError("Champs vide");
                    else {
                        voiture.setMarque(input.getText().toString().trim());
                        voiture.enregistrer();
                        initText();
                    }
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });
            alert.show();
        }
    }
}
