package com.example.yann.projetpdm.persistence;

import android.app.Application;

/**
 * Created by Yann_TOUR on 01/05/2017.
 */

public class MyApp extends Application {
    private SharedPreferencesStorageServiceImpl storageService;
    @Override
    public void onCreate(){
        super.onCreate();
        storageService = new SharedPreferencesStorageServiceImpl();
    }
    public SharedPreferencesStorageServiceImpl getStorageService(){return storageService;}
}
