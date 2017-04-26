package com.example.yann.projetpdm.classes;

import android.content.Context;

import com.example.yann.projetpdm.persistence.PersonneDAO;

import java.util.ArrayList;

/**
 * Created by Yann_TOUR on 25/03/2017.
 */
public class Personne {
    private long id;
    private String nom;
    private String prenom;
    private String tel;
    private String mail;
    private String password;
    private String role;
    private PersonneDAO pD;

    public static final String CONDUCTEUR = "conducteur";
    public static final String CONTROLEUR = "controleur";
    public static final String ADMINISTRATEUR = "administarteur";

    public Personne(Context context, String nom, String prenom, String tel, String mail, String password) {
        this.pD = new PersonneDAO(context);
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.mail = mail;
        this.password = password;
        this.role = CONDUCTEUR;
        this.enregistrer();
    }

    public Personne(Context context, String nom, String prenom, String tel, String mail, String password, String role) {
        this.pD = new PersonneDAO(context);
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.mail = mail;
        this.password = password;
        this.role = role;
        this.enregistrer();
    }
    public Personne(Context context, long id) {
        this.pD = new PersonneDAO(context);
        this.id = id;
        Personne p = pD.getPersonne(id);
        this.setNom(p.getNom());
        this.setPrenom(p.getPrenom());
        this.setTel(p.getTel());
        this.setMail(p.getMail());
        this.setPassword(p.getPassword());
        this.setRole(p.getRole());
    }
    public Personne(Context context, long id, String nom, String prenom, String tel, String mail, String password, String role) {
        this.pD = new PersonneDAO(context);
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.mail = mail;
        this.password = password;
        this.role = role;
    }

    public static ArrayList<Personne> getConducteurs(Context context){
        return new PersonneDAO(context).getConducteurs();
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
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

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public long enregistrer(){
        if(this.id != 0)
            return pD.modifier(this);
        else
            return pD.ajouter(this);
    }

    public void supprimer(){
        pD.supprimer(this);
    }

    public boolean aTicketEnCours(){
        ArrayList<Ticket> tickets = Ticket.getTicketsValides(pD.getContext(),this.getId());
        for (Ticket t : tickets ) {
            if(t.isValid()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Ticket> getTicketsValides(){
        return Ticket.getTicketsValides(pD.getContext(),this.id);
    }

    public ArrayList<Ticket> getTickets(){
        return Ticket.getTickets(pD.getContext(),this.id);
    }
}
