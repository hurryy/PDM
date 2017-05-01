package com.example.yann.projetpdm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yann.projetpdm.classes.Personne;
import com.example.yann.projetpdm.persistence.MyApp;

public class CreationCompte extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener   {

    private EditText txtPrenom;
    private EditText txtNom;
    private EditText txtTel;
    private TextView txtTelError;
    private EditText txtMail;
    private EditText txtPwd;
    private EditText txtPwd2;
    private TextView txtPrenomError;
    private TextView txtNomError;
    private TextView txtMailError;
    private TextView txtPwdError;
    private TextView txtPwd2Error;
    private Button btnCancel;
    private Button btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_compte);
        initControls();
    }

    private void initControls(){
        txtPrenom = (EditText) findViewById(R.id.creation_compte_editPrenom);
        txtNom = (EditText) findViewById(R.id.creation_compte_editNom);
        txtTel = (EditText) findViewById(R.id.creation_compte_editTel);
        txtMail = (EditText) findViewById(R.id.creation_compte_editMail);
        txtPwd = (EditText) findViewById(R.id.creation_compte_editPassword);
        txtPwd2 = (EditText) findViewById(R.id.creation_compte_editPassword2);
        txtPrenomError = (TextView) findViewById(R.id.creation_compte_txtPrenomError);
        txtNomError = (TextView) findViewById(R.id.creation_compte_txtNomError);
        txtTelError = (TextView) findViewById(R.id.creation_compte_txtTelError);
        txtMailError = (TextView) findViewById(R.id.creation_compte_txtMailError);
        txtPwdError = (TextView) findViewById(R.id.creation_compte_txtPwdError);
        txtPwd2Error = (TextView) findViewById(R.id.creation_compte_txtPwd2Error);
        btnCancel = (Button) findViewById(R.id.creation_compte_btnCancel);
        btnValider = (Button) findViewById(R.id.creation_compte_btnValider);
        btnValider.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        txtNom.setOnFocusChangeListener(this);
        txtPrenom.setOnFocusChangeListener(this);
        txtMail.setOnFocusChangeListener(this);
        txtPwd.setOnFocusChangeListener(this);
        txtPwd2.setOnFocusChangeListener(this);
        txtTel.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnValider){
            if(VerificationsValides()){
                String nom = txtNom.getText().toString().trim();
                String prenom = txtPrenom.getText().toString().trim();
                String email = txtMail.getText().toString().trim();
                String pwd = txtPwd.getText().toString().trim();
                String tel = txtTel.getText().toString().trim();
                Personne personne = new Personne(getApplicationContext(), nom, prenom, tel, email, pwd);
                ((MyApp)getApplication()).getStorageService().clear(getApplicationContext());
                ((MyApp)getApplication()).getStorageService().add(getApplicationContext(), String.valueOf(personne.getId()));
                Intent intent = new Intent(CreationCompte.this, MainActivity.class);
                startActivity(intent);
            }
        }
        if(v == btnCancel){
            redirect();
        }
    }

    private boolean VerificationsValides(){
        return validationTxtPrenom() && validationTxtNom() && validationTxtTel() && validationTxtMail() && validationTxtPwd() && validationTxtPwd2();
    }


    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public String CHAMPS_VIDE = "Champs vide";

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v == txtNom && !hasFocus){
            validationTxtNom();
        }
        if(v == txtPrenom && !hasFocus){
            validationTxtPrenom();
        }
        if(v == txtTel && !hasFocus){
            validationTxtTel();
        }
        if(v == txtMail && !hasFocus){
            validationTxtMail();
        }
        if(v == txtPwd && !hasFocus){
            validationTxtPwd();
        }
        if(v == txtPwd2 && !hasFocus){
            validationTxtPwd2();
        }
    }

    private boolean validationTxtNom() {
        boolean valide = false;
        txtNomError.setVisibility(View.VISIBLE);
        if(isEmpty(txtNom)){
            txtNomError.setText(CHAMPS_VIDE);
        } else if (!txtNom.getText().toString().trim().matches("^[A-Za-z]+[\\s\\-]?[A-Za-z]+$")){
            txtNomError.setText("Plusieurs lettres, \"-\", \" \"");
        } else {
            txtNomError.setText("");
            txtNomError.setVisibility(View.INVISIBLE);
            valide = true;
        }
        return true;
    }

    private boolean validationTxtPrenom() {
        boolean valide = false;
        txtPrenomError.setVisibility(View.VISIBLE);
        if(isEmpty(txtPrenom)){
            txtPrenomError.setText(CHAMPS_VIDE);
        } else if (!txtPrenom.getText().toString().trim().matches("^[A-Za-z]+[\\s\\-]?[A-Za-z]+$")){
            txtPrenomError.setText("Plusieurs lettres, \"-\", \" \"");
        } else {
            txtPrenomError.setText("");
            txtPrenomError.setVisibility(View.INVISIBLE);
            valide = true;
        }
        return valide;
    }

    private boolean validationTxtTel() {
        boolean valide = false;
        txtTelError.setVisibility(View.VISIBLE);
        if(isEmpty(txtTel)){
            txtTelError.setText(CHAMPS_VIDE);
        } else if (!txtTel.getText().toString().trim().matches("^[0-9]{10}$")){
            txtTelError.setText("10 chiffres");
        } else {
            txtTelError.setText("");
            txtTelError.setVisibility(View.INVISIBLE);
            valide = true;
        }
        return true;
    }

    private boolean validationTxtMail() {
        boolean valide = false;
        txtMailError.setVisibility(View.VISIBLE);
        if(isEmpty(txtMail)){
            txtMailError.setText(CHAMPS_VIDE);
        } else if (!txtMail.getText().toString().trim().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")){
            txtMailError.setText("Mail invalide");
        } else {
            txtMailError.setText("");
            txtMailError.setVisibility(View.INVISIBLE);
            valide = true;
        }
        return valide;
    }

    private boolean validationTxtPwd() {
        boolean valide = false;
        txtPwdError.setVisibility(View.VISIBLE);
        if(isEmpty(txtPwd)){
            txtPwdError.setText(CHAMPS_VIDE);
        } else {
            txtPwdError.setText("");
            txtPwdError.setVisibility(View.INVISIBLE);
            valide = true;
        }
        return valide;
    }

    private boolean validationTxtPwd2() {
        boolean valide = false;
        txtPwd2Error.setVisibility(View.VISIBLE);
        if(isEmpty(txtPwd2)){
            txtPwd2Error.setText(CHAMPS_VIDE);
        } else if(!txtPwd.getText().toString().trim().contentEquals(txtPwd2.getText().toString().trim())){
            txtPwd2Error.setText("Mots de passe différents");
        } else {
            txtPwd2Error.setText("");
            txtPwd2Error.setVisibility(View.INVISIBLE);
            valide = true;
        }
        return valide;
    }

    public void redirect(){
        Intent intent = new Intent(CreationCompte.this, LoginActivity.class);
        startActivity(intent);
    }

}
