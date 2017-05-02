package com.example.yann.projetpdm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yann.projetpdm.Utils.FormHelper;
import com.example.yann.projetpdm.classes.Personne;
import com.example.yann.projetpdm.classes.Voiture;
import com.example.yann.projetpdm.persistence.MyApp;

import java.util.List;

public class CreationVoiture extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    //ImageView image;
    Button btnValider;
    EditText edit_immatriculation;
    EditText edit_marque;
    EditText edit_model;
    Personne personneEnCours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_voiture);

        btnValider = (Button) findViewById(R.id.creation_voiture_valider);
        btnValider.setOnClickListener(this);
        edit_immatriculation = (EditText) findViewById(R.id.creation_voiture_immatriculation);
        edit_marque = (EditText) findViewById(R.id.creation_voiture_MARQUE);
        edit_model = (EditText) findViewById(R.id.creation_voiture_MODEL);
        edit_immatriculation.setOnFocusChangeListener(this);
        edit_model.setOnFocusChangeListener(this);
        edit_marque.setOnFocusChangeListener(this);

        //image = (ImageView)findViewById(R.id.creation_voiture_imageView);
        //image.setOnClickListener(this);

    }

    @Override
    protected void onResume(){
        super.onResume();

        if(!Personne.dejaConnecte(getApplication(),getApplicationContext())){
            Intent intent = new Intent(CreationVoiture.this, LoginActivity.class);
            startActivity(intent);
        }
        List<String> connexions = ((MyApp)getApplication()).getStorageService().restore(getApplicationContext());
        personneEnCours = new Personne(getApplicationContext(), Long.valueOf(connexions.get(0)));
    }

    @Override
    public void onClick(View v) {
        /*if(v == image){
            loadImagefromGallery();
        }*/
        if(v == btnValider){
            if(validationTxtImmatriculation() && validationTxtMarqueModel(edit_model) && validationTxtMarqueModel(edit_marque)){
                Voiture voiture = new Voiture(getApplicationContext(), edit_immatriculation.getText().toString().trim(), edit_marque.getText().toString().trim(), edit_model.getText().toString().trim(), imgDecodableString, "", personneEnCours.getId());
                Intent intent = new Intent(CreationVoiture.this, ListeVoitures.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v == edit_immatriculation && !hasFocus){
            validationTxtImmatriculation();
        }
        if(v == edit_marque && !hasFocus){
            validationTxtMarqueModel(edit_marque);
        }
        if(v == edit_model && !hasFocus){
            validationTxtMarqueModel(edit_model);
        }
    }

    private boolean validationTxtImmatriculation() {
        boolean valide = false;
        edit_immatriculation.setError(null);
        if(isEmpty(edit_immatriculation)){
            edit_immatriculation.setError(FormHelper.CHAMPS_VIDE);
        } else {
            valide = true;
        }
        return true;
    }

    private boolean validationTxtMarqueModel(EditText v) {
        boolean valide = false;
        v.setError(null);
        if(isEmpty(v)){
            v.setError(FormHelper.CHAMPS_VIDE);
        } else {
            valide = true;
        }
        return true;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                // Set the Image in ImageView after decoding the String
                image.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }*/

    /*public void loadImagefromGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }*/
}
