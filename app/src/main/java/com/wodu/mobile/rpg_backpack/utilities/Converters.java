package com.wodu.mobile.rpg_backpack.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.models.Session;

public abstract class Converters {

    public static Session convertToSession(JsonObject jsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, Session.class);
    }

    public static Character convertToCharacter(JsonObject jsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, Character.class);
    }
}
