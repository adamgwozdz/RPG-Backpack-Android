package com.wodu.mobile.rpg_backpack.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class User {

    @SerializedName(value = "userID")
    private Integer userID;
    @SerializedName(value = "statusID")
    private Integer statusID;
    @SerializedName(value = "email")
    private String email;
    @SerializedName(value = "name")
    private String name;
    @SerializedName(value = "password")
    private String password;
    @SerializedName(value = "emailVerified")
    private Boolean emailVerified;
    @SerializedName(value = "subscription")
    private Boolean subscription;
    @SerializedName(value = "dateCreated")
    private Timestamp dateCreated;
    @SerializedName(value = "dateModified")
    private Timestamp dateModified;
    @SerializedName(value = "dateRemoved")
    private Timestamp dateRemoved;
    @SerializedName(value = "dateSubscribed")
    private Timestamp dateSubscribed;
    @SerializedName(value = "admin")
    private Boolean admin;
    @SerializedName(value = "profileImage")
    private String profileImage;

    public User(Integer userID, Integer statusID, String email, String name, String password,
                Boolean emailVerified, Boolean subscription, Timestamp dateCreated,
                Timestamp dateModified, Timestamp dateRemoved, Timestamp dateSubscribed,
                Boolean admin, String profileImage) {
        this.userID = userID;
        this.statusID = statusID;
        this.email = email;
        this.name = name;
        this.password = password;
        this.emailVerified = emailVerified;
        this.subscription = subscription;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.dateRemoved = dateRemoved;
        this.dateSubscribed = dateSubscribed;
        this.admin = admin;
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userID +
                ", statusID=" + statusID +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", emailVerified=" + emailVerified +
                ", subscription=" + subscription +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", dateRemoved=" + dateRemoved +
                ", dateSubscribed=" + dateSubscribed +
                ", admin=" + admin +
                ", profileImage='" + profileImage + '\'' +
                '}';
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

    public String getPassword() {
        return password;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public Boolean getSubscription() {
        return subscription;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public Timestamp getDateModified() {
        return dateModified;
    }

    public Timestamp getDateRemoved() {
        return dateRemoved;
    }

    public Timestamp getDateSubscribed() {
        return dateSubscribed;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
