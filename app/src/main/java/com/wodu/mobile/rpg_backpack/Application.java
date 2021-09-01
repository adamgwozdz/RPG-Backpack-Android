package com.wodu.mobile.rpg_backpack;

import android.content.Context;
import android.content.SharedPreferences;

public class Application extends android.app.Application {

    private static Application instance;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }
}
