package com.example.yann.projetpdm.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Yann on 15/02/2017.
 */

public class SharedPreferencesStorageServiceImpl implements StorageService {
    public static String NAME = "articles";
    private String PREFS_NAME = "prefsFile.txt";
    @Override
    public List<String> store(Context context, List<String> listIn) {
        // Récupération d'un objet de type SharedPreferences
        SharedPreferences.Editor editor = initSetConnection(context);
        HashSet list = new HashSet(listIn);
        editor.putStringSet(NAME, list);
        editor.commit();
        return restore(context);
    }

    @Override
    public List<String> restore(Context context) {
        SharedPreferences preferences = initGetConnection(context);
        Set<String> list = preferences.getStringSet(NAME, new HashSet<String>());
        List<String> articles = new ArrayList<String>();
        articles.addAll(list);
        return articles;
    }

    @Override
    public List<String> clear(Context context) {
        SharedPreferences.Editor editor = initSetConnection(context);
        editor.clear();
        return restore(context);
    }

    @Override
    public void add(Context context, String article) {
        // Récupération d'un objet de type SharedPreferences
        List<String> articles = restore(context);
        articles.add(article);
        store(context, articles);
    }

    private SharedPreferences.Editor initSetConnection(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        return editor;
    }
    private SharedPreferences initGetConnection(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        return preferences;
    }
}
