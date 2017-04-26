package com.example.yann.projetpdm.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yann_TOUR on 25/03/2017.
 */
public class DatabaseObject extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AppProjetPDM.db";
    public static String TABLE_NAME = "";
    protected SQLiteDatabase mDB = null;


    protected static final String SQL_DELETE_ENTRIES =
            "";

    public DatabaseObject(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VoitureDAO.SQL_CREATE_ENTRIES);
        db.execSQL(PersonneDAO.SQL_CREATE_ENTRIES);
        db.execSQL(TicketDAO.SQL_CREATE_ENTRIES);
        db.execSQL(ZoneDAO.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(VoitureDAO.SQL_DELETE_ENTRIES);
        db.execSQL(PersonneDAO.SQL_DELETE_ENTRIES);
        db.execSQL(TicketDAO.SQL_DELETE_ENTRIES);
        db.execSQL(ZoneDAO.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public SQLiteDatabase open() {
        mDB = this.getWritableDatabase();
        return mDB;
    }

    public void close() {
        mDB.close();
    }

}
