package com.wodu.mobile.rpg_backpack.repositories;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.utilities.RestAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;

public class SessionRepository {

    private final String TAG = "SessionRepository";
    private static SessionRepository instance;
    private SessionService sessionService;

    public SessionRepository() {
    }

    public static SessionRepository getInstance() {
        if(instance == null) {
            instance = new SessionRepository();
        }
        return new SessionRepository();
    }

    public Observable<List<Session>> getSessions() {
        sessionService = RestAdapter.getAdapter().create(SessionService.class);
        return sessionService.getSessions(Application.getInstance().getToken());
    }

    public Observable<List<Session>> getUserSessions() {
        sessionService = RestAdapter.getAdapter().create(SessionService.class);
        return sessionService.getUserSessions(Application.getInstance().getToken());
    }

    public Observable<Session> getSessionsByUserId(long userId) {
        sessionService = RestAdapter.getAdapter().create(SessionService.class);
        return sessionService.getSession(Application.getInstance().getToken(), userId);
    }

    public Observable<JsonObject> createSession(String name, String password, Integer maxAttributes, String image) {
        sessionService = RestAdapter.getAdapter().create(SessionService.class);
        return sessionService.createSession(Application.getInstance().getToken(), createSessionBody(name, password, maxAttributes, image));
    }

    public Observable<JsonObject> joinSession(Integer sessionID, Integer userID, String password, String name, Boolean gameMaster, String image) {
        sessionService = RestAdapter.getAdapter().create(SessionService.class);
        return sessionService.joinSession(Application.getInstance().getToken(), joinSessionBody(sessionID, userID, password, name, gameMaster, image));
    }

    private JsonObject joinSessionBody(Integer sessionID, Integer userID, String password, String name, Boolean gameMaster, String image) {
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("sessionID", sessionID);
            jsonObj.put("userID", userID);
            jsonObj.put("password", password);
            jsonObj.put("name", name);
            jsonObj.put("gameMaster", gameMaster);
            jsonObj.put("image", image);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }

    private JsonObject createSessionBody(String name, String password, Integer maxAttributes, String image) {
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("name", name);
            jsonObj.put("password", password);
            jsonObj.put("maxAttributes", maxAttributes);
            jsonObj.put("image", image);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }
}
