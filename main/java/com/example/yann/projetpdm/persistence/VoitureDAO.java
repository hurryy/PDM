package com.example.yann.projetpdm.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yann.projetpdm.classes.Voiture;

import java.util.ArrayList;

/**
 * Created by Yann_TOUR on 25/03/2017.
 */
public class VoitureDAO {
    public static final String immatriculation = "immatriculation";
    public static final String marque = "marque";
    public static final String model = "model";
    public static final String image = "image";
    public static final String idQRCode = "idQRCode";
    public static final String idConducteur = "idConducteur";

    public static final String TABLE_NAME = "Voiture";
    public static final String SQL_DELETE_ENTRIES =
            "drop table if exists " + TABLE_NAME;
    public static final String KEY = "_ID";

    private Context context;

    private String listeAttributs = this.KEY + ", " +
            this.immatriculation + ", " + this.marque + ", " + this.model + ", " +
            this.image + ", " + this.idQRCode + ", " + this.idConducteur;

    public static String SQL_CREATE_ENTRIES = " create table " + TABLE_NAME + " (" +
                KEY + " integer primary key autoincrement," +
                immatriculation + " text," +
                marque + " text," +
                model + " text," +
                image + " text," +
                idQRCode + " text," +
                idConducteur + " integer" +
                " ); ";

    private String[] projection = {
            "immatriculation",
            "marque",
            "model",
            "image",
            "idQRCode",
            "idConducteur"
    };

    public VoitureDAO(Context context) {
        this.context = context;
    }

    public long ajouter(Voiture v){
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        ContentValues value = getContentValueInsert(v);
        return mDB.insert(TABLE_NAME, null, value);
    }
    public long modifier(Voiture v){
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        ContentValues value = getContentValue(v);
        return mDB.update(TABLE_NAME, value, KEY + " = ?", new String[]{String.valueOf(v.getId())});
    }

    public ArrayList<Voiture> getVoituresConducteur (long idConducteur){
        ArrayList<Voiture> voitures = new ArrayList<Voiture>();
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        Cursor c = mDB.rawQuery("select " + listeAttributs + " from " + this.TABLE_NAME +
                " where " + this.idConducteur + " = ?" , new String[] {String.valueOf(idConducteur)});
        while (c.moveToNext()) {
            voitures.add(cursorToVoiture(c));
        }

        c.close();
        return voitures;
    }

    public Voiture getVoiture (long idVoiture){
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        Cursor c = mDB.rawQuery("select " + listeAttributs + " from " + this.TABLE_NAME +
                " where " + this.KEY + " = ?" , new String[] {String.valueOf(idVoiture)});
        c.moveToNext();
        Voiture v = cursorToVoiture(c);
        c.close();
        return v;
    }

    protected Voiture cursorToVoiture(Cursor c){
        return new Voiture(context, c.getLong(0), c.getString(1), c.getString(2),
                c.getString(3), c.getString(4), c.getString(5),
                c.getLong(6));
    }

    public ContentValues getContentValue(Voiture v){
        ContentValues value = new ContentValues();
        value.put(this.KEY, v.getId());
        value.put(this.immatriculation, v.getImmatriculation());
        value.put(this.marque, v.getMarque());
        value.put(this.model, v.getModel());
        value.put(this.image, v.getImage());
        value.put(this.idQRCode, v.getIdQRCode());
        value.put(this.idConducteur, v.getIdConducteur());
        return  value;
    }

    public ContentValues getContentValueInsert(Voiture v){
        ContentValues value = new ContentValues();
        value.put(this.immatriculation, v.getImmatriculation());
        value.put(this.marque, v.getMarque());
        value.put(this.model, v.getModel());
        value.put(this.image, v.getImage());
        value.put(this.idQRCode, v.getIdQRCode());
        value.put(this.idConducteur, v.getIdConducteur());
        return  value;
    }


    public void supprimer(Voiture v) {
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        mDB.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(v.getId())});
    }
}
