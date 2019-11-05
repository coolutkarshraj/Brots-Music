package com.brots.music.application.localStorage;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by team edeals on 2/11/2018.
 */

public class PreferenceManager {
    public static final String ISLOGGEDIN = "isLoggedin";
    public static String instaMixMemory= "instaMixFragment";
    private static PreferenceManager instance;
    private SharedPreferences storage;
    private SharedPreferences.Editor ed;
    public static String id = "id";
    public static String REGID = "REGID";
    public static String name = "name";
    public static String email = "email";
    public static String userType = "userType";
    public static String password = "password";
    public static String deviceToken = "deviceToken";
    public static String savedSongMemory = "SavedSongMemory";
    public static String InstaMixMemory = "InstaMixMemory";
    public static String isFirstLaunch = "isFirstLanunch";
    public static final String loginData = "loginData";


    public PreferenceManager(Context context) {
        storage = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceManager getInstance(Context context) {
        if (instance == null)
            instance = new PreferenceManager(context);
        return instance;
    }

    public void putString(String name, String value) {
        storage.edit().putString(name, String.valueOf(value)).apply();
    }

    public String getString(String name) {
        return storage.getString(name, "");
    }

    public void putInt(String name, int value) {
        storage.edit().putInt(name, value).apply();
    }

    public int getInt(String name) {
        return storage.getInt(name, -1);
    }


    public void clearAll() {
        storage.edit().clear().apply();
    }
    public void editor(){
    ed=storage.edit();
    }

    public void commit()
    {
      ed.commit();
      ed.apply();
    }



}

