package com.example.yann.projetpdm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.yann.projetpdm.Utils.FormHelper;
import com.example.yann.projetpdm.classes.Personne;
import com.example.yann.projetpdm.persistence.MyApp;

import java.util.List;

public class MonCompte extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Personne personneEnCours;

    private EditText edit_prenom;
    private EditText edit_nom;
    private EditText edit_mail;
    private EditText edit_tel;
    private Button btnModifMDP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_compte);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        /*
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

        if(!Personne.dejaConnecte(getApplication(),getApplicationContext())){
            Intent intent = new Intent(MonCompte.this, LoginActivity.class);
            startActivity(intent);
        }
        List<String> connexions = ((MyApp)getApplication()).getStorageService().restore(getApplicationContext());
        personneEnCours = new Personne(getApplicationContext(), Long.valueOf(connexions.get(0)));

        edit_prenom = (EditText) findViewById(R.id.mon_compte_prenom);
        edit_nom = (EditText) findViewById(R.id.mon_compte_nom);
        edit_tel = (EditText) findViewById(R.id.mon_compte_tel);
        edit_mail = (EditText) findViewById(R.id.mon_compte_mail);
        edit_prenom.setOnClickListener(this);
        edit_nom.setOnClickListener(this);
        edit_mail.setOnClickListener(this);
        edit_tel.setOnClickListener(this);
        btnModifMDP = (Button) findViewById(R.id.mon_compte_btn_password);
        btnModifMDP.setOnClickListener(this);
    }

    protected void onResume(){
        super.onResume();
        initText();
    }

    public void initText(){
        edit_prenom.setText(personneEnCours.getPrenom());
        edit_nom.setText(personneEnCours.getNom());
        edit_mail.setText(personneEnCours.getMail());
        edit_tel.setText(personneEnCours.getTel());
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

        if (id == R.id.nav_tickets) {
            Intent intent = new Intent(MonCompte.this, ListeTickets.class);
            startActivity(intent);
        } else if (id == R.id.nav_voitures) {
            Intent intent = new Intent(MonCompte.this, ListeVoitures.class);
            startActivity(intent);
        }else if (id == R.id.nav_ticket){
            if(!personneEnCours.aTicketEnCours()){
                Intent intent = new Intent(MonCompte.this, MainActivity.class);
                startActivity(intent);
            }
        }else if (id == R.id.nav_compte){
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        if(v == edit_prenom){
                alert.setTitle("Prénom");
                alert.setMessage("");
                // Create TextView
                final EditText input = new EditText (this);
                input.setHint("Prénom");
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(input.getText().toString().trim().isEmpty())
                            input.setError("Champs vide");
                        else {
                            personneEnCours.setPrenom(input.getText().toString().trim());
                            personneEnCours.enregistrer();
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
        if(v == edit_nom){
            alert.setTitle("Nom");
            alert.setMessage("");
            // Create TextView
            final EditText input = new EditText (this);
            input.setHint("Nom");
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(input.getText().toString().trim().isEmpty())
                        input.setError("Champs vide");
                    else {
                        personneEnCours.setNom(input.getText().toString().trim());
                        personneEnCours.enregistrer();
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
        if(v == edit_tel){
            alert.setTitle("Téléphone");
            alert.setMessage("");
            // Create TextView
            final EditText input = new EditText (this);
            input.setInputType(InputType.TYPE_CLASS_PHONE);
            input.setHint("Téléphone");
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(!input.getText().toString().trim().isEmpty() && input.getText().toString().trim().matches("^[0-9]{10}$")){
                        personneEnCours.setTel(input.getText().toString().trim());
                        personneEnCours.enregistrer();
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
        if(v == edit_mail){
            alert.setTitle("Mail");
            alert.setMessage("");
            // Create TextView
            final EditText input = new EditText (this);
            input.setHint("Mail");
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(input.getText().toString().trim().isEmpty())
                        input.setError("Champs vide");
                    else {
                        personneEnCours.setMail(input.getText().toString().trim());
                        personneEnCours.enregistrer();
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
        if(v == btnModifMDP){
            alert.setTitle("Mot de passe");
            alert.setMessage("");
            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            // Create TextView
            final EditText input = new EditText (this);
            input.setHint("Nouveau mot de passe");
            final EditText input2 = new EditText (this);
            input2.setHint("Confirmation");
            input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            input2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            layout.addView(input);
            layout.addView(input2);
            alert.setView(layout);
            final AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(!input.getText().toString().trim().isEmpty()) {
                        if(input.getText().toString().trim().contentEquals(input2.getText().toString().trim())){
                            personneEnCours.setMail(input.getText().toString().trim());
                            personneEnCours.enregistrer();
                            initText();
                            alert2.setTitle("Succès");
                            alert2.setMessage("Mot de passe modifié avec succès");

                        }else{
                            alert2.setTitle("Erreur");
                            alert2.setMessage("Les mots de passe sont différents, veuillez réessayer");
                        }
                        alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }});
                        alert2.show();
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

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean validationTxtPrenom() {
        boolean valide = false;
        edit_prenom.setError(null);
        if(isEmpty(edit_prenom)){
            edit_prenom.setError(FormHelper.CHAMPS_VIDE);
        } else if (!edit_prenom.getText().toString().trim().matches("^[A-Za-z]+[\\s\\-]?[A-Za-z]+$")){
            edit_prenom.setError("Plusieurs lettres, \"-\", \" \"");
        } else {
            valide = true;
        }
        return valide;
    }
    private boolean validationTxtNom() {
        boolean valide = false;
        edit_nom.setError(null);
        if(isEmpty(edit_nom)){
            edit_nom.setError(FormHelper.CHAMPS_VIDE);
        } else if (!edit_nom.getText().toString().trim().matches("^[A-Za-z]+[\\s\\-]?[A-Za-z]+$")){
            edit_nom.setError("Plusieurs lettres, \"-\", \" \"");
        } else {
            valide = true;
        }
        return valide;
    }

    private boolean validationTxtTel() {
        boolean valide = false;
        edit_tel.setError(null);
        if(isEmpty(edit_tel)){
            edit_tel.setError(FormHelper.CHAMPS_VIDE);
        } else if (!edit_tel.getText().toString().trim().matches("^[0-9]{10}$")){
            edit_tel.setError("10 chiffres");
        } else {
            valide = true;
        }
        return true;
    }

    private boolean validationTxtMail() {
        boolean valide = false;
        edit_mail.setError(null);
        if(isEmpty(edit_mail)){
            edit_mail.setError(FormHelper.CHAMPS_VIDE);
        } else if (!edit_mail.getText().toString().trim().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")){
            edit_mail.setError("Mail invalide");
        } else {
            valide = true;
        }
        return valide;
    }
}
