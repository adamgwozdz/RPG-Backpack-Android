package com.wodu.mobile.rpg_backpack.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.repositories.CharacterRepository;
import com.wodu.mobile.rpg_backpack.repositories.SessionRepository;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class CreateSessionActivityViewModel extends ViewModel {

    private final String TAG = "CreateSessionActivityViewModel";

    private final SessionRepository sessionRepository = SessionRepository.getInstance();
    private final CharacterRepository characterRepository = CharacterRepository.getInstance();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Session> sessionMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Character> characterMutableLiveData = new MutableLiveData<>();

    public CreateSessionActivityViewModel() {
    }

    public MutableLiveData<Session> createSession(String name, String password, Integer maxAttributes, String image) {
        createSessionSubscription(name, password, maxAttributes, image);
        return sessionMutableLiveData;
    }

    public MutableLiveData<Character> createCharacter(Integer userID, Integer sessionID, String name, Boolean isGameMaster, String image) {
        createCharacterSubscription(userID, sessionID, name, isGameMaster, image);
        return characterMutableLiveData;
    }

    public int getMaxAttributesNumber(boolean subscribed) {
        if (subscribed)
            return Session.USER_TIERS.SUBSCRIBED.maxAttributes;
        else
            return Session.USER_TIERS.NOT_SUBSCRIBED.maxAttributes;
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

    private void createCharacterSubscription(Integer userID, Integer sessionID, String name, Boolean isGameMaster, String image) {
        characterRepository.createCharacter(userID, sessionID, name, isGameMaster, image).subscribe(new Observer<JsonObject>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull JsonObject characterResponse) {
                Gson gson = new Gson();
                Character character = gson.fromJson(characterResponse, Character.class);
                characterMutableLiveData.postValue(character);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
