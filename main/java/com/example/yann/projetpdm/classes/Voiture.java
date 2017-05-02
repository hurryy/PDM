package com.example.yann.projetpdm.classes;

import android.content.Context;

import com.example.yann.projetpdm.persistence.VoitureDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yann on 22/03/2017.
 */

public class Voiture {
    private long id;
    private String immatriculation;
    private String marque;
    private String model;
    private String image;
    private String idQRCode;
    private long idConducteur;
    private VoitureDAO vD;

    public Voiture(Context context, long id) {
        this.id = id;
        this.vD = new VoitureDAO(context);
        Voiture v = vD.getVoiture(id);
        this.setImmatriculation(v.getImmatriculation());
        this.setMarque(v.getMarque());
        this.setModel(v.getModel());
        this.setImage(v.getImage());
        this.setIdQRCode(v.getIdQRCode());
        this.setIdConducteur(v.getIdConducteur());
    }

    public Voiture(Context context, String immatriculation, String marque, String model, String image, String idQRCode, long idConducteur) {
        this.vD = new VoitureDAO(context);
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.model = model;
        this.image = image;
        this.idQRCode = idQRCode;
        this.idConducteur = idConducteur;
        this.id = this.enregistrer();
    }

    public Voiture(Context context, long id, String immatriculation, String marque, String model, String image, String idQRCode, long idConducteur) {
        this.vD = new VoitureDAO(context);
        this.id = id;
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.model = model;
        this.image = image;
        this.idQRCode = idQRCode;
        this.idConducteur = idConducteur;
    }

    public static ArrayList<Voiture> getVoituresConducteur(Context context, long idConducteur){
        return new VoitureDAO(context).getVoituresConducteur(idConducteur);
    }

    public Voiture(){}
    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public long getIdConducteur(){return idConducteur; };
    public void setIdConducteur(long idConducteur) { this.idConducteur = idConducteur;}

    public String getImmatriculation() {
        return immatriculation;
    }
    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getMarque() {
        return marque;
    }
    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getIdQRCode() {
        return idQRCode;
    }
    public void setIdQRCode(String idQRCode) {
        this.idQRCode = idQRCode;
    }

    public static List<String> getListVoiture(){
        ArrayList<String> voitures = new ArrayList<String>();
        voitures.add("EE-000-EE");
        voitures.add("EA-000-EA");
        return voitures;
    }

    public long enregistrer(){
        if(this.id != 0)
            return vD.modifier(this);
        else
            return vD.ajouter(this);
    }

    public void supprimer(){
        vD.supprimer(this);
    }

}
