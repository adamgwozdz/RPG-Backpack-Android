package com.wodu.mobile.rpg_backpack.repositories;

import com.google.gson.JsonObject;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.models.Session;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CharacterService {

    @GET("api/app/session/characters/session/{sessionID}")
    Observable<List<Character>> getSessionCharacters(
            @Header("Authorization") String token,
            @Path(value = "sessionID") String sessionID
    );

    @Headers("Content-Type: application/json")
    @POST("/api/app/session/characters/{sessionID}")
    Observable<Response<JsonObject>> createCharacter(
            @Header("Authorization") String token,
            @Path(value = "sessionID") String sessionID,
            @Body JsonObject jsonBody
    );

    @Headers("Content-Type: application/json")
    @PUT("/api/app/session/characters/{characterID}")
    Observable<Response<JsonObject>> updateCharacter(
            @Header("Authorization") String token,
            @Path(value = "characterID") String characterID,
            @Body JsonObject jsonBody
    );
}
