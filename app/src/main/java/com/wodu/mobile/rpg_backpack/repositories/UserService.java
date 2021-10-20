package com.wodu.mobile.rpg_backpack.repositories;

import com.google.gson.JsonObject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @Headers("Content-Type: application/json")
    @POST("api/users/register")
    Observable<Response<JsonObject>> register(
            @Body JsonObject jsonBody
            );

    @Headers("Content-Type: application/json")
    @POST("api/users/login")
    Observable<Response<JsonObject>> login(
            @Body JsonObject jsonBody
    );
}
