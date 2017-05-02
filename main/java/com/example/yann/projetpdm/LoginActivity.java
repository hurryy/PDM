package com.example.yann.projetpdm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.yann.projetpdm.classes.Personne;
import com.example.yann.projetpdm.persistence.MyApp;
import com.example.yann.projetpdm.persistence.PersonneDAO;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener{




    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Personne personneConnecte;
    private Button mEmailSignInButton;
    private Button btnInscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume(){
        super.onResume();
        if(Personne.dejaConnecte(getApplication(),getApplicationContext())){
            redirect();
        }


        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        btnInscription = (Button) findViewById(R.id.login_inscription);
        btnInscription.setOnClickListener(this);
        mEmailSignInButton = (Button) findViewById(R.id.login_sign_in_button);
        mEmailSignInButton.setOnClickListener(this);
    }

    public boolean login(String email, String password){
        personneConnecte = new PersonneDAO(getApplicationContext()).login(email, password);
        return personneConnecte != null;
    }

    @Override
    public void onClick(View v) {
        if(v == mEmailSignInButton){

            if(!login(mEmailView.getText().toString().trim(), mPasswordView.getText().toString().trim())){
                mEmailView.setError("Email ou mot de passe incorrect");
            }else{
                ((MyApp)getApplication()).getStorageService().clear(getApplicationContext());
                ((MyApp)getApplication()).getStorageService().add(getApplicationContext(), String.valueOf(personneConnecte.getId()));
                redirect();
            }
        }
        if(v == btnInscription){
            Intent intent = new Intent(LoginActivity.this, CreationCompte.class);
            startActivity(intent);
        }
    }



    public void redirect(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

