package com.example.yann.projetpdm.classes;

import android.content.Context;

import com.example.yann.projetpdm.persistence.ZoneDAO;

import java.util.ArrayList;

/**
 * Created by Yann on 22/03/2017.
 */

public class Zone {
    private long id;
    private String nom;
    private Float tarifHoraire;
    private String heurePayanteDebut;
    private String heurePayanteFin;
    private ZoneDAO zD;

    public Zone(Context context, long id) {
        this.zD = new ZoneDAO(context);
        this.id = id;
        Zone z = zD.getZone(this.id);
        this.setHeurePayanteDebut(z.getHeurePayanteDebut());
        this.setHeurePayanteFin(z.getHeurePayanteFin());
        this.setTarifHoraire(z.getTarifHoraire());
        this.setNom(z.getNom());
    }

    public Zone(Context context, long id, String nom, Float tarifHoraire, String heurePayanteDebut, String heurePayanteFin) {
        this.zD = new ZoneDAO(context);
        this.id = id;
        this.nom = nom;
        this.tarifHoraire = tarifHoraire;
        this.heurePayanteDebut = heurePayanteDebut;
        this.heurePayanteFin = heurePayanteFin;
    }

    public Zone(Context context, String nom, Float tarifHoraire, String heurePayanteDebut, String heurePayanteFin) {
        this.zD = new ZoneDAO(context);
        this.nom = nom;
        this.tarifHoraire = tarifHoraire;
        this.heurePayanteDebut = heurePayanteDebut;
        this.heurePayanteFin = heurePayanteFin;
        this.enregistrer();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Float getTarifHoraire() {
        return tarifHoraire;
    }

    public void setTarifHoraire(Float tarifHoraire) {
        this.tarifHoraire = tarifHoraire;
    }

    public String getHeurePayanteDebut() {
        return heurePayanteDebut;
    }

    public void setHeurePayanteDebut(String heurePayanteDebut) {
        this.heurePayanteDebut = heurePayanteDebut;
    }

    public String getHeurePayanteFin() {
        return heurePayanteFin;
    }

    public void setHeurePayanteFin(String heurePayanteFin) {
        this.heurePayanteFin = heurePayanteFin;
    }

    public static ArrayList<Zone> getZones(Context context){
        return new ZoneDAO(context).getZones();
    }

    public long enregistrer(){
        if(this.id != 0)
            return zD.modifier(this);
        else
            return zD.ajouter(this);
    }

    public void supprimer(){
        zD.supprimer(this);
    }
}
