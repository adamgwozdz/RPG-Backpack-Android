package com.wodu.mobile.rpg_backpack.repositories;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wodu.mobile.rpg_backpack.ErrorHandlingAdapter;
import com.wodu.mobile.rpg_backpack.models.User;
import com.wodu.mobile.rpg_backpack.utilities.RestAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Response;

public class UserRepository {

    private final String TAG = "UserRepositoryImpl";
    private static UserRepository instance;
    private UserService userService;

    public UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private JsonObject createRegisterJsonBody(String email, String name, String password, Boolean subscription) {
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", email);
            jsonObj.put("name", name);
            jsonObj.put("password", password);
            jsonObj.put("subscription", subscription);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }

    private JsonObject createLoginJsonBody(String email, String password) {
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", email);
            jsonObj.put("password", password);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }

    public Observable<JsonObject> register(String email, String name, String password, Boolean subscription) {
        userService = RestAdapter.getAdapter().create(UserService.class);
        return userService.register(createRegisterJsonBody(email, name, password, subscription));
    }

    public Observable<JsonObject> login(String email, String password) {
        userService = RestAdapter.getAdapter().create(UserService.class);
        return userService.login(createLoginJsonBody(email, password));
    }
}

