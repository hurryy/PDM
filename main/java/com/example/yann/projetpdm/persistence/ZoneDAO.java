package com.example.yann.projetpdm.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yann.projetpdm.classes.Zone;

import java.util.ArrayList;

/**
 * Created by Yann_TOUR on 25/03/2017.
 */
public class ZoneDAO {
    public static final String nom = "nom";
    public static final String tarifHoraire = "tarifHoraire";
    public static final String heurePayanteDebut = "heurePayanteDebut";
    public static final String heurePayanteFin = "heurePayanteFin";

    public static final String TABLE_NAME = "Zone";
    protected static final String SQL_DELETE_ENTRIES =
            "drop table if exists " + TABLE_NAME;
    public static final String KEY = "_ID";
    public static String SQL_CREATE_ENTRIES = " create table " + TABLE_NAME + " (" +
                KEY + " integer primary key autoincrement," +
                nom + " text," +
                tarifHoraire + " real," +
                heurePayanteDebut+ " real," +
                heurePayanteFin + " real" +
                " ); ";
    private Context context;

    private String listeAttributs = this.KEY + ", " + this.nom + ", " + this.tarifHoraire + ", " +
            this.heurePayanteDebut + ", " + this.heurePayanteFin;

    public ZoneDAO(Context context) {
        this.context = context;
    }

    public long ajouter(Zone z){
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        ContentValues value = getContentValueInsert(z);
        return mDB.insert(TABLE_NAME, null, value);
    }
    public long modifier(Zone z){
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        ContentValues value = getContentValue(z);
        return mDB.update(TABLE_NAME, value, KEY + " = ?", new String[]{String.valueOf(z.getId())});
    }
    public ContentValues getContentValueInsert(Zone z){
        ContentValues value = new ContentValues();
        value.put(this.nom, z.getNom());
        value.put(this.tarifHoraire, z.getTarifHoraire());
        value.put(this.heurePayanteDebut, z.getHeurePayanteDebut());
        value.put(this.heurePayanteFin, z.getHeurePayanteFin());
        return  value;
    }
    public ContentValues getContentValue(Zone z){
        ContentValues value = new ContentValues();
        value.put(this.KEY, z.getId());
        value.put(this.nom, z.getNom());
        value.put(this.tarifHoraire, z.getTarifHoraire());
        value.put(this.heurePayanteDebut, z.getHeurePayanteDebut());
        value.put(this.heurePayanteFin, z.getHeurePayanteFin());
        return  value;
    }

    public void supprimer(Zone z) {
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        mDB.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(z.getId())});
    }

    public ArrayList<Zone> getZones (){
        ArrayList<Zone> zones = new ArrayList<Zone>();
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        Cursor c = mDB.rawQuery("select " + listeAttributs + " from " + this.TABLE_NAME, null);
        while (c.moveToNext()) {
            zones.add(cursorToZone(c));
        }
        c.close();
        return zones;
    }
    public Zone getZone (long idZone){
        ArrayList<Zone> zones = new ArrayList<Zone>();
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        Cursor c = mDB.rawQuery("select " + listeAttributs + " from " + this.TABLE_NAME + " where " + this.KEY + " = ?", new String[]{String.valueOf(idZone)});
        c.moveToNext();
        Zone z = cursorToZone(c);
        c.close();
        return z;
    }

    public Zone cursorToZone(Cursor c){
        return new Zone(context, c.getLong(0), c.getString(1), c.getFloat(2), c.getString(3), c.getString(4));
    }
}
