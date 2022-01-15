package com.wodu.mobile.rpg_backpack.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.List;

public class Session {

    @SerializedName(value = "sessionID")
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
    @SerializedName(value = "characters")
    private List<Character> characters;


    @SerializedName(value = "status")
    private Integer status;

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

    public List<Character> getCharacters() {
        return characters;
    }

    public Integer getStatus() {
        return status;
    }

    public void setSessionID(Integer sessionID) {
        this.sessionID = sessionID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMaxAttributes(Integer maxAttributes) {
        this.maxAttributes = maxAttributes;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }

    public void setDateRemoved(Timestamp dateRemoved) {
        this.dateRemoved = dateRemoved;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
