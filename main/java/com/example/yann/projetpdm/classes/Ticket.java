package com.example.yann.projetpdm.classes;

import android.content.Context;

import com.example.yann.projetpdm.persistence.TicketDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Yann_TOUR on 25/03/2017.
 */
public class Ticket {
    private long id;
    private Long dateDemande;
    private Long heureDebut;
    /**
     * duree en minute
     */
    private int dureeInitiale;
    private int dureeSupp;
    private float coutTotal;
    private long idVoiture;
    private long idZone;
    private TicketDAO tD;

    public Ticket(Context context, Long dateDemande, Long heureDebut, int dureeInitiale, int dureeSupp, float coutTotal, long idVoiture, long idZone) {
        this.dateDemande = dateDemande;
        this.heureDebut = heureDebut;
        this.dureeInitiale = dureeInitiale;
        this.dureeSupp = dureeSupp;
        this.coutTotal = coutTotal;
        this.idVoiture = idVoiture;
        this.idZone = idZone;
        this.tD = new TicketDAO(context);
        this.enregistrer();
    }

    public Ticket(Context context) {
        this.tD = new TicketDAO(context);
        this.id = tD.ajouter(this);
    }

    public Ticket(Context context, long id, Long dateDemande, Long heureDebut, int dureeInitiale, int dureeSupp, float coutTotal, long idVoiture, long idZone) {
        this.id = id;
        this.dateDemande = dateDemande;
        this.heureDebut = heureDebut;
        this.dureeInitiale = dureeInitiale;
        this.dureeSupp = dureeSupp;
        this.coutTotal = coutTotal;
        this.idVoiture = idVoiture;
        this.idZone = idZone;
        this.tD = new TicketDAO(context);
    }

    public boolean isValid(){
        SimpleDateFormat dateFormat = DateHelper.getSimpleDateFormat();
        return  this.getDateFin().compareTo(new Date().getTime()) >= 0;
    }

    public Long getDateFin(){
        Long dateDebut = this.heureDebut;
        Date dDebut = new Date();
        dDebut.setTime(dateDebut);
        Date dateFin = dDebut;
        dateFin.setTime(dDebut.getTime() + DateHelper.convertMinToMilliseconds(this.getDureeInitiale()) + DateHelper.convertMinToMilliseconds(this.getDureeSupp()));
        return dateFin.getTime();
    }

    public static ArrayList<Ticket> getTicketsVoiture(Context context, long idVoiture){
        return new TicketDAO(context).getTicketsVoiture(idVoiture);
    }

    public static ArrayList<Ticket> getTickets(Context context, long idPersonne) {
        return new TicketDAO(context).getTickets(idPersonne);
    }

    public static ArrayList<Ticket> getTicketsValides(Context context, long idPersonne) {
        ArrayList<Ticket> tickets = new TicketDAO(context).getTickets(idPersonne);
        ArrayList<Ticket> tV = new ArrayList<Ticket>();
        for(Ticket t : tickets){
            if(t.isValid())
                tV.add(t);
        }
        return tV;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Long dateDemande) {
        this.dateDemande = dateDemande;
    }

    public Long getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Long heureDebut) {
        this.heureDebut = heureDebut;
    }

    /**
     * duree initiale en minute
     */
    public int getDureeInitiale() {
        return dureeInitiale;
    }

    public void setDureeInitiale(int dureeInitiale) {
        this.dureeInitiale = dureeInitiale;
    }

    public int getDureeSupp() {
        return dureeSupp;
    }

    /**
     * duree supp en minute
     */
    public void setDureeSupp(int dureeSupp) {
        this.dureeSupp = dureeSupp;
    }

    /**
     * Retourne le temps restant en minutes
     * @return nombre de minutes restantes
     */
    public long getTempsRestant()
    {
        return DateHelper.convertMillisecondsToMinutes(this.getDateFin()- new Date().getTime());
    }

    public float getCoutTotal() {
        return coutTotal;
    }

    public void setCoutTotal(float coutTotal) {
        this.coutTotal = coutTotal;
    }

    public long getIdVoiture() {
        return idVoiture;
    }

    public void setIdVoiture(long idVoiture) {
        this.idVoiture = idVoiture;
    }

    public long getIdZone() {
        return idZone;
    }

    public void setIdZone(long idZone) {
        this.idZone = idZone;
    }

    public long enregistrer(){
        if(this.id != 0)
            return tD.modifier(this);
        else
            return tD.ajouter(this);
    }

    public void supprimer(){
        tD.supprimer(this);
    }

}
