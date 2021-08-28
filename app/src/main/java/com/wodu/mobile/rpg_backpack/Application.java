package com.wodu.mobile.rpg_backpack;

import android.content.Context;
import android.content.SharedPreferences;

public class Application extends android.app.Application {

    private static Application instance;

    private String accessToken;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        accessToken = retrieveTokenFromSharedPrefs();
    }

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    private String retrieveTokenFromSharedPrefs() {
        return getSharedPreferences("PrefsFile", Context.MODE_PRIVATE).getString("access_token", null);
    }

    public void setToken(String token) {
        accessToken = token;
        SharedPreferences sharedPreferences = getSharedPreferences("PrefsFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_token", accessToken);
        editor.apply();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public boolean hasLoggedIn() {
        return getAccessToken() != null;
    }

//    public boolean hasTokenExpired() {
//        // ecc..
//    }
}
