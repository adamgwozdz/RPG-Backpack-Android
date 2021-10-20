package com.wodu.mobile.rpg_backpack.viewmodels;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.repositories.SessionRepository;
import com.wodu.mobile.rpg_backpack.response_wrappers.Event;
import com.wodu.mobile.rpg_backpack.response_wrappers.ResponseWrapperJsonObject;
import com.wodu.mobile.rpg_backpack.utilities.FIELDS;
import com.wodu.mobile.rpg_backpack.utilities.TextValidator;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Response;

public class JoinSessionViewModel extends ViewModel {

    private final String TAG = "JoinSessionViewModel";

    private final SessionRepository sessionRepository = SessionRepository.getInstance();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Event<ResponseWrapperJsonObject>> sessionMutableLiveData = new MutableLiveData<>();

    public JoinSessionViewModel() {
    }

    public MutableLiveData<Event<ResponseWrapperJsonObject>> joinSession(Integer sessionID, Integer userID, String password, String name, Boolean gameMaster, String image) {
        joinSessionSubscription(sessionID, userID, password, name, gameMaster, image);
        return sessionMutableLiveData;
    }

    private void joinSessionSubscription(Integer sessionID, Integer userID, String password, String name, Boolean gameMaster, String image) {
        sessionRepository.joinSession(sessionID, userID, password, name, gameMaster, image).subscribe(new Observer<Response<JsonObject>>() {
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
                    sessionMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "Provided credentials are incorrect")));
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
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void validateInput(TextInputLayout sessionIdTextInputLayout, TextInputLayout passwordTextInputLayout,
                              EditText sessionIdEditText, EditText passwordEditText) {
        sessionIdEditText.addTextChangedListener(new TextValidator(sessionIdEditText) {
            @Override
            public void validate(TextView textView, String text) {
                //TODO sessionID not name!!!
                if (text.length() < FIELDS.SESSION_NAME.minLength) {
                    sessionIdTextInputLayout.setError("Session ID is too short");
                } else if (text.length() > FIELDS.SESSION_NAME.maxLength) {
                    sessionIdTextInputLayout.setError("Session ID is too long");
                } else {
                    sessionIdTextInputLayout.setError(null);
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

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
