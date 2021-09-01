package com.wodu.mobile.rpg_backpack.models;

import java.sql.Timestamp;

public class User {

    private Integer userId;
    private Integer statusId;
    private String email;
    private String name;
    private String password;
    private Boolean emailVerified;
    private Boolean subscription;
    private Timestamp dateCreated;
    private Timestamp dateModified;
    private Timestamp dateRemoved;
    private Timestamp dateSubscribed;
    private Boolean admin;
    private String profileImage;

    public User(Integer userId, Integer statusId, String email, String name, String password,
                Boolean emailVerified, Boolean subscription, Timestamp dateCreated,
                Timestamp dateModified, Timestamp dateRemoved, Timestamp dateSubscribed,
                Boolean admin, String profileImage) {
        this.userId = userId;
        this.statusId = statusId;
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
                "userId=" + userId +
                ", statusId=" + statusId +
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

    public Integer getUserId() {
        return userId;
    }

    public Integer getStatusId() {
        return statusId;
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
