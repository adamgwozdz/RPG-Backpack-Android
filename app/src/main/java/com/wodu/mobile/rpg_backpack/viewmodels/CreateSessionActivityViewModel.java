package com.wodu.mobile.rpg_backpack.viewmodels;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.repositories.CharacterRepository;
import com.wodu.mobile.rpg_backpack.repositories.SessionRepository;
import com.wodu.mobile.rpg_backpack.utilities.FIELDS;
import com.wodu.mobile.rpg_backpack.utilities.TextValidator;

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

    public void validateInput(TextInputLayout nameTextInputLayout, TextInputLayout passwordTextInputLayout,
                              EditText nameEditText, EditText passwordEditText) {
        nameEditText.addTextChangedListener(new TextValidator(nameEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() < FIELDS.SESSION_NAME.minLength) {
                    nameTextInputLayout.setError("Session name is too short");
                } else if (text.length() > FIELDS.SESSION_NAME.maxLength) {
                    nameTextInputLayout.setError("Session name is too long");
                } else {
                    nameTextInputLayout.setError(null);
                }
            }
        });

        passwordEditText.addTextChangedListener(new TextValidator(passwordEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() < FIELDS.SESSION_PASSWORD.minLength) {
                    passwordTextInputLayout.setError("Password is too short");
                } else if (text.length() > FIELDS.SESSION_PASSWORD.maxLength) {
                    passwordTextInputLayout.setError("Password is too long");
                } else {
                    passwordTextInputLayout.setError(null);
                }
            }
        });
    }
}
