package com.example.yann.projetpdm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yann.projetpdm.Utils.FormHelper;
import com.example.yann.projetpdm.classes.Personne;
import com.example.yann.projetpdm.persistence.MyApp;

public class CreationCompte extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener   {

    private EditText txtPrenom;
    private EditText txtNom;
    private EditText txtTel;
    private EditText txtMail;
    private EditText txtPwd;
    private EditText txtPwd2;
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
        txtNom.setError(null);
        if(isEmpty(txtNom)){
            txtNom.setError(FormHelper.CHAMPS_VIDE);
        } else if (!txtNom.getText().toString().trim().matches("^[A-Za-z]+[\\s\\-]?[A-Za-z]+$")){
            txtNom.setError("Plusieurs lettres, \"-\", \" \"");
        } else {
            valide = true;
        }
        return true;
    }

    private boolean validationTxtPrenom() {
        boolean valide = false;
        txtPrenom.setError(null);
        if(isEmpty(txtPrenom)){
            txtPrenom.setError(FormHelper.CHAMPS_VIDE);
        } else if (!txtPrenom.getText().toString().trim().matches("^[A-Za-z]+[\\s\\-]?[A-Za-z]+$")){
            txtPrenom.setError("Plusieurs lettres, \"-\", \" \"");
        } else {
            valide = true;
        }
        return valide;
    }

    private boolean validationTxtTel() {
        boolean valide = false;
        txtTel.setError(null);
        if(isEmpty(txtTel)){
            txtTel.setError(FormHelper.CHAMPS_VIDE);
        } else if (!txtTel.getText().toString().trim().matches("^[0-9]{10}$")){
            txtTel.setError("10 chiffres");
        } else {
            valide = true;
        }
        return true;
    }

    private boolean validationTxtMail() {
        boolean valide = false;
        txtMail.setError(null);
        if(isEmpty(txtMail)){
            txtMail.setError(FormHelper.CHAMPS_VIDE);
        } else if (!txtMail.getText().toString().trim().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")){
            txtMail.setError("Mail invalide");
        } else {
            valide = true;
        }
        return valide;
    }

    private boolean validationTxtPwd() {
        boolean valide = false;
        txtPwd.setError(null);
        if(isEmpty(txtPwd)){
            txtPwd.setError(FormHelper.CHAMPS_VIDE);
        } else {
            valide = true;
        }
        return valide;
    }

    private boolean validationTxtPwd2() {
        boolean valide = false;
        txtPwd2.setError(null);
        if(isEmpty(txtPwd2)){
            txtPwd2.setError(FormHelper.CHAMPS_VIDE);
        } else if(!txtPwd.getText().toString().trim().contentEquals(txtPwd2.getText().toString().trim())){
            txtPwd2.setError("Mots de passe différents");
        } else {
            valide = true;
        }
        return valide;
    }

    public void redirect(){
        Intent intent = new Intent(CreationCompte.this, LoginActivity.class);
        startActivity(intent);
    }

}
