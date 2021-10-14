package com.wodu.mobile.rpg_backpack.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.repositories.SessionRepository;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class CreateSessionActivityViewModel extends ViewModel {

    private final String TAG = "CreateSessionActivityViewModel";

    private final SessionRepository sessionRepository = SessionRepository.getInstance();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Session> sessionMutableLiveData = new MutableLiveData<>();

    public CreateSessionActivityViewModel() {
    }

    public MutableLiveData<Session> createSession(String name, String password, Integer maxAttributes, String image) {
        createSessionSubscription(name, password, maxAttributes, image);
        return sessionMutableLiveData;
    }

    private void createSessionSubscription(String name, String password, Integer maxAttributes, String image) {
        sessionRepository.createSession(name, password, maxAttributes, image).subscribe(new Observer<JsonObject>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull JsonObject sessionResponse) {
                Gson gson = new Gson();
                Session session = gson.fromJson(sessionResponse, Session.class);
                sessionMutableLiveData.postValue(session);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public int getMaxAttributesNumber(boolean subscribed) {
        if (subscribed)
            return 20;
        else
            return 10;
    }
}
