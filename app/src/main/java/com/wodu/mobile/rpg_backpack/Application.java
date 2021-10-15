package com.wodu.mobile.rpg_backpack;

public class Application extends android.app.Application {

    private final String TAG = "ApplicationTag";
    private static Application instance;
    private String token;
    private Integer userID;
    private Integer statusID;
    private String email;
    private String name;
    private Boolean emailVerified;
    private Boolean subscription;
    private Boolean admin;
    private long dateCreated;


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
            userID = getBodyIntegerElement(body, 2);
            statusID = getBodyIntegerElement(body, 3);
            email = getBodyStringElement(body, 4);
            name = getBodyStringElement(body, 5);
            emailVerified = getBodyStringElement(body, 6).equals("true");
            subscription = getBodyStringElement(body, 7).equals("true");
            admin = getBodyStringElement(body, 8).equals("true");
            dateCreated = Long.parseLong(getBodyStringElement(body, 9));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Example
    //{"iat":1633971064,"exp":1633985464,"userId":148,"statusId":1,"email":"ap1@qa.qa","name":"ap1",
    //"emailVerified":false,"subscription":false,"admin":false,"dateCreated":1633772428051}
    private String getBodyStringElement(String body, int i) {
        return body.split(",")[i].split(":")[1].replace("\"", "")
                .replace("{", "").replace("}", "");
    }

    private Integer getBodyIntegerElement(String body, int i) {
        return Integer.valueOf(body.split(",")[i].split(":")[1].replace("\"", "")
                .replace("{", "").replace("}", ""));
    }

    public String getToken() {
        return "Bearer " + token;
    }

    public Integer getUserID() {
        return userID;
    }

    public Integer getStatusID() {
        return statusID;
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
