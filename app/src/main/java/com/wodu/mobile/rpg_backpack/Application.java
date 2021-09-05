package com.wodu.mobile.rpg_backpack;

public class Application extends android.app.Application {

    private static Application instance;
    private String token = "";


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

    public String getToken() {
        return "Bearer " + token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
