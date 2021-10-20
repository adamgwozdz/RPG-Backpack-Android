package com.wodu.mobile.rpg_backpack.repositories;

import com.google.gson.JsonObject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CharacterService {

    @Headers("Content-Type: application/json")
    @POST("/api/app/session/characters/{sessionID}")
    Observable<Response<JsonObject>> createCharacter(
            @Header("Authorization") String token,
            @Path(value = "sessionID") String sessionID,
            @Body JsonObject jsonBody
    );
}
