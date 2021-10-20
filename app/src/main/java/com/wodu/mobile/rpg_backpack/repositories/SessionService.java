package com.wodu.mobile.rpg_backpack.repositories;

import com.google.gson.JsonObject;
import com.wodu.mobile.rpg_backpack.models.Session;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SessionService {

    @GET("api/app/session/sessions/all-sessions")
    Observable<List<Session>> getSessions(
            @Header("Authorization") String token
    );

    @GET("api/app/session/sessions/user-sessions")
    Observable<List<Session>> getUserSessions(
            @Header("Authorization") String token
    );

    @GET("api/app/session/sessions/{id}")
    Observable<Session> getSession(
            @Header("Authorization") String token,
            @Path("id") long userId
    );

    @Headers("Content-Type: application/json")
    @POST("/api/app/session/sessions")
    Observable<Response<JsonObject>> createSession(
            @Header("Authorization") String token,
            @Body JsonObject jsonBody
    );

    @Headers("Content-Type: application/json")
    @POST("/api/app/session/sessions/join")
    Observable<Response<JsonObject>> joinSession(
            @Header("Authorization") String token,
            @Body JsonObject jsonBody
    );
}
