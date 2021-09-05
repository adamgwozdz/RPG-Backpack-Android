package com.wodu.mobile.rpg_backpack.repositories;

import com.wodu.mobile.rpg_backpack.models.Session;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
}
