package com.wodu.mobile.rpg_backpack.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Session {

    @SerializedName(value = "sessionId")
    private Integer sessionID;
    @SerializedName(value = "name")
    private String name;
    @SerializedName(value = "password")
    private String password;
    @SerializedName(value = "maxAttributes")
    private Integer maxAttributes;
    @SerializedName(value = "dateCreated")
    private Timestamp dateCreated;
    @SerializedName(value = "dateModified")
    private Timestamp dateModified;
    @SerializedName(value = "dateRemoved")
    private Timestamp dateRemoved;
    @SerializedName(value = "image")
    private String image;

    public Session(Integer sessionID, String name, String password, Integer maxAttributes,
                   Timestamp dateCreated, Timestamp dateModified, Timestamp dateRemoved,
                   String image) {
        this.sessionID = sessionID;
        this.name = name;
        this.password = password;
        this.maxAttributes = maxAttributes;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.dateRemoved = dateRemoved;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionID=" + sessionID +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", maxAttributes=" + maxAttributes +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", dateRemoved=" + dateRemoved +
                ", image='" + image + '\'' +
                '}';
    }

    public Integer getSessionID() {
        return sessionID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Integer getMaxAttributes() {
        return maxAttributes;
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

    public String getImage() {
        return image;
    }

    public enum USER_TIERS {
        NOT_SUBSCRIBED(5),
        SUBSCRIBED(20);

        public int maxAttributes;

        USER_TIERS(int maxAttributes) {
            this.maxAttributes = maxAttributes;
        }
    }
}
