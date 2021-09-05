package com.wodu.mobile.rpg_backpack.repositories;

import android.util.Log;

import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.utilities.RestAdapter;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

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

    public Observable<Session> getSession(long userId) {
        sessionService = RestAdapter.getAdapter().create(SessionService.class);
        return sessionService.getSession(Application.getInstance().getToken(), userId);
    }
}
