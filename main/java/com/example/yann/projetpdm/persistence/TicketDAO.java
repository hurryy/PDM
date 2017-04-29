package com.example.yann.projetpdm.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yann.projetpdm.classes.Ticket;

import java.util.ArrayList;

/**
 * Created by Yann_TOUR on 25/03/2017.
 */
public class TicketDAO {
    public static final String dateDemande = "dateDemande";
    public static final String heureDebut = "heureDebut";
    public static final String dureeInitiale = "dureeInitiale";
    public static final String dureeSupp = "dureeSupp";
    public static final String coutTotal = "coutTotal";
    public static final String idVoiture = "idVoiture";
    public static final String idZone = "idZone";

    public static final String TABLE_NAME = "Ticket";
    protected static final String SQL_DELETE_ENTRIES =
            "drop table if exists " + TABLE_NAME;
    public static final String KEY = "_ID";
    public static String SQL_CREATE_ENTRIES = " create table " + TABLE_NAME + " (" +
                KEY + " integer primary key autoincrement," +
                dateDemande + " integer," +
                heureDebut + " integer," +
                dureeInitiale + " integer," +
                dureeSupp + " integer," +
                coutTotal + " real," +
                idVoiture + " integer," +
                idZone + " integer" +
                " ); ";

    private Context context;

    private String listeAttributs = this.KEY + ", " +
            this.dateDemande + ", " + this.heureDebut + ", " +
            this.dureeInitiale + ", " + this.dureeSupp +  ", " +
            this.coutTotal + ", " + this.idVoiture + ", " + this.idZone;

    public TicketDAO(Context context) {
        this.context = context;
    }

    public long ajouter(Ticket t){
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        ContentValues value = getContentValueInsert(t);
        return mDB.insert(TABLE_NAME, null, value);
    }
    public long modifier(Ticket t){
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        ContentValues value = getContentValue(t);
        return mDB.update(TABLE_NAME, value, KEY + " = ?", new String[]{String.valueOf(t.getId())});
    }

    public ContentValues getContentValueInsert(Ticket t){
        ContentValues value = new ContentValues();
        value.put(this.dateDemande, String.valueOf(t.getDateDemande()));
        value.put(this.heureDebut, String.valueOf(t.getHeureDebut()));
        value.put(this.dureeInitiale, t.getDureeInitiale());
        value.put(this.dureeSupp, t.getDureeSupp());
        value.put(this.coutTotal,t.getCoutTotal());
        value.put(this.idVoiture,t.getIdVoiture());
        value.put(this.idZone, t.getIdZone());
        return  value;
    }
    public ContentValues getContentValue(Ticket t){
        ContentValues value = new ContentValues();
        value.put(this.KEY, t.getId());
        value.put(this.dateDemande, t.getDateDemande().toString());
        value.put(this.heureDebut, t.getHeureDebut().toString());
        value.put(this.dureeInitiale, t.getDureeInitiale());
        value.put(this.dureeSupp, t.getDureeSupp());
        value.put(this.coutTotal,t.getCoutTotal());
        value.put(this.idVoiture,t.getIdVoiture());
        value.put(this.idZone, t.getIdZone());
        return  value;
    }


    public void supprimer(Ticket t) {
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        mDB.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(t.getId())});
    }

    public ArrayList<Ticket> getTicketsVoiture (long idVoitre){
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        Cursor c = mDB.rawQuery("select " + listeAttributs + " from " + this.TABLE_NAME +
                " where "  + this.idVoiture + " = ?", new String[]{String.valueOf(this.idVoiture)});
        while (c.moveToNext()) {
            tickets.add(cursorToTicket(c));
        }
        c.close();
        return tickets;
    }

    public ArrayList<Ticket> getTickets (long idPersonne){
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        Cursor c = mDB.rawQuery("select v." + listeAttributs + " from " + this.TABLE_NAME + " t " +
                "join Voiture v on t.idVoiture = v._ID" +
                " where v." + VoitureDAO.idConducteur + " = ?"
                , new String[]{String.valueOf(idPersonne)});
        while (c.moveToNext()) {
            tickets.add(cursorToTicket(c));
        }
        c.close();
        return tickets;
    }

    protected Ticket cursorToTicket(Cursor c){
        Ticket t ;
        t = new Ticket(context, c.getLong(0), c.getLong(1), c.getLong(2), c.getInt(3),
                c.getInt(4), c.getFloat(5), c.getLong(6), c.getLong(7));

        return t;
    }
}
