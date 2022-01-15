package com.wodu.mobile.rpg_backpack.repositories;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.utilities.RestAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;

public class CharacterRepository {

    private final String TAG = "CharacterRepository";
    private static CharacterRepository instance;
    private CharacterService characterService;

    public CharacterRepository() {
    }

    public static CharacterRepository getInstance() {
        if(instance == null) {
            instance = new CharacterRepository();
        }
        return new CharacterRepository();
    }

    public Observable<Response<JsonObject>> createCharacter(Integer userID, Integer sessionID, String name, Boolean isGameMaster, String image) {
        characterService = RestAdapter.getAdapter().create(CharacterService.class);
        return characterService.createCharacter(Application.getInstance().getToken(), sessionID.toString(), createCharacterBody(userID, name, isGameMaster, image));
    }

    public Observable<Response<JsonObject>> updateCharacter(Integer sessionID, String name, Boolean isGameMaster, String image) {
        characterService = RestAdapter.getAdapter().create(CharacterService.class);
        return characterService.updateCharacter(Application.getInstance().getToken(), sessionID.toString(), createCharacterBody(name, isGameMaster, image));
    }

    public Observable<Response<JsonObject>> kickCharacter(Integer characterID) {
        characterService = RestAdapter.getAdapter().create(CharacterService.class);
        return characterService.kickCharacter(Application.getInstance().getToken(), characterID.toString());
    }

    public Observable<List<Character>> getSessionCharacters(Integer sessionID) {
        characterService = RestAdapter.getAdapter().create(CharacterService.class);
        return characterService.getSessionCharacters(Application.getInstance().getToken(), sessionID.toString());
    }

    private JsonObject createCharacterBody(Integer userID, String name, Boolean isGameMaster, String image) {
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("userID", userID);
            jsonObj.put("name", name);
            jsonObj.put("gameMaster", isGameMaster);
            jsonObj.put("image", image);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }

    private JsonObject createCharacterBody(String name, Boolean isGameMaster, String image) {
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("name", name);
            jsonObj.put("gameMaster", isGameMaster);
            jsonObj.put("image", image);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }
}
