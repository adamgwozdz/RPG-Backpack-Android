package com.wodu.mobile.rpg_backpack;

import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.wodu.mobile.rpg_backpack.models.Session;

import java.sql.Timestamp;

public class Application extends android.app.Application {

    private final String TAG = "ApplicationTag";
    private static Application instance;
    private String token = "";
    private String userId = "";
    private String statusId = "";
    private String email = "";
    private String name = "";
    private Boolean emailVerified = false;
    private Boolean subscription = false;
    private Boolean admin = false;
    private long dateCreated = 0;


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

    private void setUserData(String token) {
        try {
            String body = JWTUtils.decoded(token);
            userId = getBodyElement(body, 2);
            statusId = getBodyElement(body, 3);
            email = getBodyElement(body, 4);
            name = getBodyElement(body, 5);
            emailVerified = getBodyElement(body, 6).equals("true");
            subscription = getBodyElement(body, 7).equals("true");
            admin = getBodyElement(body, 8).equals("true");
            dateCreated = Long.parseLong(getBodyElement(body, 9));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Example
    //{"iat":1633971064,"exp":1633985464,"userId":148,"statusId":1,"email":"ap1@qa.qa","name":"ap1",
    //"emailVerified":false,"subscription":false,"admin":false,"dateCreated":1633772428051}
    private String getBodyElement(String body, int i) {
        return body.split(",")[i].split(":")[1].replace("\"", "")
                .replace("{", "").replace("}", "");
    }

    public String getToken() {
        return "Bearer " + token;
    }

    public String getUserId() {
        return userId;
    }

    public String getStatusId() {
        return statusId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public Boolean getSubscription() {
        return subscription;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setToken(String token) {
        this.token = token;
        setUserData(token);
    }

    public void resetToken() {
        this.token = "";
    }
}
