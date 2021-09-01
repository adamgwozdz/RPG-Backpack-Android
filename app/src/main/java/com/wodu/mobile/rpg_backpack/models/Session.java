package com.wodu.mobile.rpg_backpack.models;

import java.sql.Timestamp;

public class Session {

    private Integer sessionId;
    private String name;
    private String password;
    private Integer maxAttributes;
    private Timestamp dateCreated;
    private Timestamp dateModified;
    private Timestamp dateRemoved;
    private String image;

    public Session(Integer sessionId, String name, String password, Integer maxAttributes, Timestamp dateCreated, Timestamp dateModified, Timestamp dateRemoved, String image) {
        this.sessionId = sessionId;
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
                "sessionId=" + sessionId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", maxAttributes=" + maxAttributes +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", dateRemoved=" + dateRemoved +
                ", image='" + image + '\'' +
                '}';
    }

    public Integer getSessionId() {
        return sessionId;
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
}
