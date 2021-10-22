package com.wodu.mobile.rpg_backpack.utilities;

public enum FIELDS {
    EMAIL(5, 254),
    USER_PASSWORD(6, 30),
    USERNAME(4, 14),
    SESSION_NAME(1, 30),
    SESSION_PASSWORD(6, 30),
    SESSION_ID(1, 7),
    CHARACTER_NAME(1, 30);

    public int minLength;
    public int maxLength;

    FIELDS(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }
}
