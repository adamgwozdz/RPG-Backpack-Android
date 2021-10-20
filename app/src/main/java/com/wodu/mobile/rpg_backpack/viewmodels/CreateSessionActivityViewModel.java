package com.wodu.mobile.rpg_backpack.viewmodels;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.repositories.CharacterRepository;
import com.wodu.mobile.rpg_backpack.repositories.SessionRepository;
import com.wodu.mobile.rpg_backpack.response_wrappers.Event;
import com.wodu.mobile.rpg_backpack.response_wrappers.ResponseWrapperJsonObject;
import com.wodu.mobile.rpg_backpack.utilities.FIELDS;
import com.wodu.mobile.rpg_backpack.utilities.TextValidator;
import com.wodu.mobile.rpg_backpack.utilities.Utilities;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Response;

public class CreateSessionActivityViewModel extends ViewModel {

    private final String TAG = "CreateSessionActivityViewModel";

    private final SessionRepository sessionRepository = SessionRepository.getInstance();
    private final CharacterRepository characterRepository = CharacterRepository.getInstance();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Event<ResponseWrapperJsonObject>> sessionMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Event<ResponseWrapperJsonObject>> characterMutableLiveData = new MutableLiveData<>();

    public CreateSessionActivityViewModel() {
    }

    public MutableLiveData<Event<ResponseWrapperJsonObject>> createSession(String name, String password, Integer maxAttributes, String image) {
        createSessionSubscription(name, password, maxAttributes, image);
        return sessionMutableLiveData;
    }

    public MutableLiveData<Event<ResponseWrapperJsonObject>> createCharacter(Integer userID, Integer sessionID, String name, Boolean isGameMaster, String image) {
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
        sessionRepository.createSession(name, password, maxAttributes, image).subscribe(new Observer<Response<JsonObject>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull Response<JsonObject> jsonObjectResponse) {
                JsonObject jsonObject = jsonObjectResponse.body();
                if (jsonObjectResponse.isSuccessful()) {
                    sessionMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, null)));
                } else if (jsonObjectResponse.code() == 400) {
                    Log.d(TAG, "HTTP Error: 400 Bad Request");
                    sessionMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "400 Bad request")));
                } else if (jsonObjectResponse.code() == 401) {
                    Log.d(TAG, "HTTP Error: 401 Unauthorized");
                    sessionMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "Can't create session with provided credentials")));
                } else if (jsonObjectResponse.code() == 403) {
                    Log.d(TAG, "HTTP Error: 403 Forbidden");
                    sessionMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "Session expired")));
                } else if (jsonObjectResponse.code() == 404) {
                    Log.d(TAG, "HTTP Error: 404 Not Found");
                    sessionMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "404 Not found")));
                } else if (jsonObjectResponse.code() == 500) {
                    Log.d(TAG, "HTTP Error: 500 Internal Server Error");
                    sessionMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "500 Server error")));
                } else {
                    Log.d(TAG, "Unknown Error");
                    sessionMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "Unknown Error")));
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {}

            @Override
            public void onComplete() {
            }
        });
    }

    private void createCharacterSubscription(Integer userID, Integer sessionID, String name, Boolean isGameMaster, String image) {
        characterRepository.createCharacter(userID, sessionID, name, isGameMaster, image).subscribe(new Observer<Response<JsonObject>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Response<JsonObject> jsonObjectResponse) {
                JsonObject jsonObject = jsonObjectResponse.body();
                if (jsonObjectResponse.isSuccessful()) {
                    characterMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, null)));
                } else if (jsonObjectResponse.code() == 400) {
                    Log.d(TAG, "HTTP Error: 400 Bad Request");
                    characterMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "400 Bad request")));
                } else if (jsonObjectResponse.code() == 401) {
                    Log.d(TAG, "HTTP Error: 401 Unauthorized");
                    characterMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "Can't create character with provided credentials")));
                } else if (jsonObjectResponse.code() == 403) {
                    Log.d(TAG, "HTTP Error: 403 Forbidden");
                    characterMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "Session expired")));
                } else if (jsonObjectResponse.code() == 404) {
                    Log.d(TAG, "HTTP Error: 404 Not Found");
                    characterMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "404 Not found")));
                } else if (jsonObjectResponse.code() == 500) {
                    Log.d(TAG, "HTTP Error: 500 Internal Server Error");
                    characterMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "500 Server error")));
                } else {
                    Log.d(TAG, "Unknown Error");
                    characterMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "Unknown Error")));
                }
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

    public Session convertToSession(JsonObject jsonObject) {
                Gson gson = new Gson();
                return gson.fromJson(jsonObject, Session.class);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
