package com.example.yann.projetpdm.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yann.projetpdm.classes.Personne;

import java.util.ArrayList;

/**
 * Created by Yann_TOUR on 25/03/2017.
 */
public class PersonneDAO {

    public static final String nom = "nom";
    public static final String prenom = "prenom";
    public static final String tel = "tel";
    public static final String mail = "mail";
    public static final String password = "password";
    public static final String role = "role";

    public static final String TABLE_NAME = "Personne";
    public static final String SQL_DELETE_ENTRIES =
            "drop table if exists " + TABLE_NAME;
    public static final String KEY = "_ID";

    private Context context;
    private String listeAttributs =
                this.KEY + ", " + this.nom + ", " + this.prenom + ", " +
                this.tel + ", " + this.mail + ", " + this.password + ", " + this.role;

    public static String SQL_CREATE_ENTRIES = " create table " + TABLE_NAME + " (" +
                KEY + " integer primary key autoincrement," +
                nom + " text," +
                prenom + " text," +
                tel + " text," +
                mail + " text," +
                password + " text," +
                role + " text" +
        " ); ";

    public PersonneDAO(Context context) {
        this.context = context;
    }

    public long ajouter(Personne p){
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        ContentValues value = getContentValueInsert(p);
        return mDB.insert(TABLE_NAME, null, value);
    }
    public long modifier(Personne p){
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        ContentValues value = getContentValue(p);
        return mDB.update(TABLE_NAME, value, KEY + " = ?", new String[]{String.valueOf(p.getId())});
    }

    public ArrayList<Personne> getConducteurs (){
        ArrayList<Personne> personnes = new ArrayList<Personne>();
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        Cursor c = mDB.rawQuery("select " + listeAttributs + " from " + this.TABLE_NAME +
                " where " + this.role + " = ? " ,
                new String[]{String.valueOf(Personne.CONDUCTEUR)});
        while (c.moveToNext()) {
            personnes.add(cursorToPersonne(c));
        }
        c.close();
        return personnes;
    }

    public ArrayList<Personne> getPersonnes (){
        ArrayList<Personne> personnes = new ArrayList<Personne>();
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        Cursor c = mDB.rawQuery("select " + listeAttributs + " from " + this.TABLE_NAME, null);
        while (c.moveToNext()) {
            personnes.add(cursorToPersonne(c));
        }
        c.close();
        return personnes;
    }
    public Personne getPersonne (long idPersonne){
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        Cursor c = mDB.rawQuery("select " + listeAttributs + " from " + this.TABLE_NAME +
                " where " + this.KEY + " = ?"
                ,new String[]{String.valueOf(idPersonne)});
        c.moveToNext();
        Personne p = cursorToPersonne(c);
        c.close();
        return p;
    }

    protected Personne cursorToPersonne(Cursor c){
        return new Personne(context, c.getLong(0), c.getString(1),
                c.getString(2), c.getString(3), c.getString(4),c.getString(5), c.getString(6));
    }

    public ContentValues getContentValueInsert(Personne p){
        ContentValues value = new ContentValues();
        value.put(this.nom, p.getNom());
        value.put(this.prenom, p.getPrenom());
        value.put(this.tel, p.getTel());
        value.put(this.mail, p.getMail());
        value.put(this.password, p.getPassword());
        value.put(this.role, p.getRole());
        return  value;
    }
    public ContentValues getContentValue(Personne p){
        ContentValues value = new ContentValues();
        value.put(this.KEY, p.getId());
        value.put(this.nom, p.getNom());
        value.put(this.prenom, p.getPrenom());
        value.put(this.tel, p.getTel());
        value.put(this.mail, p.getMail());
        value.put(this.password, p.getPassword());
        value.put(this.role, p.getRole());
        return  value;
    }

    public Context getContext() {
        return context;
    }

    public void supprimer(Personne p) {
        SQLiteDatabase mDB = new DatabaseObject(context).open();
        mDB.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(p.getId())});
    }
}
