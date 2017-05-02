package com.example.yann.projetpdm.persistence;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Yann on 15/02/2017.
 */

public interface StorageService {
    /**
     *
     * @param context
     * @param Key
     * @param Value
     * @return
     */
    public HashMap<String,String> store(Context context, String Key, String Value);

    /**
     *
     * @param context
     * @param key
     * @return
     */
    public HashMap<String, String> restore(Context context, String key);
    /**
     * Vide la liste des articles.
     * @param context contexte de l'activité
     * @return liste des articles vide.
     */
    public void clear(Context context);

    public HashMap<String,String> add(Context context, String Key, String Value);

    /**
     *
     * @param context
     * @param key
     */
    public void remove(Context context, String key);

}
