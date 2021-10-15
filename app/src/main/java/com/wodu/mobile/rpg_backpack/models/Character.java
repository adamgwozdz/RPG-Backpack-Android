package com.wodu.mobile.rpg_backpack.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Character {

    @SerializedName(value = "characterID")
    private Integer characterID;
    @SerializedName(value = "userID")
    private Integer userID;
    @SerializedName(value = "sessionID")
    private Integer sessionID;
    @SerializedName(value = "name")
    private String name;
    @SerializedName(value = "gameMaster")
    private Boolean gameMaster;
    @SerializedName(value = "dateJoined")
    private Timestamp dateJoined;
    @SerializedName(value = "dateLeft")
    private Timestamp dateLeft;
    @SerializedName(value = "image")
    private String image;

    public Character(Integer characterID, Integer userID, Integer sessionID, String name,
                     Boolean gameMaster, Timestamp dateJoined, Timestamp dateLeft, String image) {
        this.characterID = characterID;
        this.userID = userID;
        this.sessionID = sessionID;
        this.name = name;
        this.gameMaster = gameMaster;
        this.dateJoined = dateJoined;
        this.dateLeft = dateLeft;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Character{" +
                "characterID=" + characterID +
                ", userID=" + userID +
                ", sessionID=" + sessionID +
                ", name='" + name + '\'' +
                ", gameMaster=" + gameMaster +
                ", dateJoined=" + dateJoined +
                ", dateLeft=" + dateLeft +
                ", image='" + image + '\'' +
                '}';
    }

    public Integer getCharacterID() {
        return characterID;
    }

    public Integer getUserID() {
        return userID;
    }

    public Integer getSessionID() {
        return sessionID;
    }

    public String getName() {
        return name;
    }

    public Boolean getGameMaster() {
        return gameMaster;
    }

    public Timestamp getDateJoined() {
        return dateJoined;
    }

    public Timestamp getDateLeft() {
        return dateLeft;
    }

    public String getImage() {
        return image;
    }
}
