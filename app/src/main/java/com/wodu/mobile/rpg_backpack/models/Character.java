package com.wodu.mobile.rpg_backpack.models;

import java.sql.Timestamp;

public class Character {

    private Integer characterId;
    private Integer userID;
    private Integer sessionID;
    private String name;
    private Boolean gameMaster;
    private Timestamp dateJoined;
    private Timestamp dateLeft;
    private String image;

    public Character(Integer characterId, Integer userID, Integer sessionID, String name,
                     Boolean gameMaster, Timestamp dateJoined, Timestamp dateLeft, String image) {
        this.characterId = characterId;
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
                "characterId=" + characterId +
                ", userID=" + userID +
                ", sessionID=" + sessionID +
                ", name='" + name + '\'' +
                ", gameMaster=" + gameMaster +
                ", dateJoined=" + dateJoined +
                ", dateLeft=" + dateLeft +
                ", image='" + image + '\'' +
                '}';
    }

    public Integer getCharacterId() {
        return characterId;
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
