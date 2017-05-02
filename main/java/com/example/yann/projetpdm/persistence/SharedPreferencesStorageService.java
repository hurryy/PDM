package com.example.yann.projetpdm.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Yann on 15/02/2017.
 */

public class SharedPreferencesStorageService implements StorageService {
    private String PREFS_NAME = "prefsFile.txt";

    @Override
    public HashMap<String, String> store(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
        HashMap<String,String> ret = new HashMap<String, String>();
        ret.put(key, value);
        return ret;
    }

    @Override
    public HashMap<String, String> restore(Context context, String key) {
        HashMap<String,String> ret = new HashMap<>();
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        ret.put(key,pref.getString("key_name", null));
        return ret;
    }

    @Override
    public void clear(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public HashMap<String, String> add(Context context, String Key, String Value) {
        return store(context,Key,Value);
    }

    @Override
    public void remove(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }
}
