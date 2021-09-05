package com.wodu.mobile.rpg_backpack.repositories;

import com.google.gson.JsonObject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @Headers("Content-Type: application/json")
    @POST("api/users/register")
    Observable<JsonObject> register(
            @Body JsonObject jsonBody
            );

    @Headers("Content-Type: application/json")
    @POST("api/users/login")
    Observable<JsonObject> login(
            @Body JsonObject jsonBody
    );
}
