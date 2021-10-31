package com.wodu.mobile.rpg_backpack.viewmodels;


import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.response_wrappers.Event;
import com.wodu.mobile.rpg_backpack.response_wrappers.ResponseWrapperJsonObject;
import com.wodu.mobile.rpg_backpack.repositories.UserRepository;
import com.wodu.mobile.rpg_backpack.utilities.FIELDS;
import com.wodu.mobile.rpg_backpack.utilities.TextValidator;
import com.wodu.mobile.rpg_backpack.utilities.Utilities;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Response;

public class RegisterActivityViewModel extends ViewModel {

    private final String TAG = "RegisterViewModel";

    private final UserRepository userRepository = UserRepository.getInstance();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Event<ResponseWrapperJsonObject>> registerMutableLiveData = new MutableLiveData<>();

    public RegisterActivityViewModel() {}

    public MutableLiveData<Event<ResponseWrapperJsonObject>> register(String email, String name, String password, boolean subscription) {
        loadRegisterData(email, name, password, subscription);
        return registerMutableLiveData;
    }

    private void loadRegisterData(String email, String name, String password, boolean subscription) {
        userRepository.register(email, name, password, subscription).subscribe(new Observer<Response<JsonObject>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull Response<JsonObject> jsonObjectResponse) {
                JsonObject jsonObject = jsonObjectResponse.body();
                if (jsonObjectResponse.isSuccessful()) {
                    Application.getInstance().setToken(Utilities.jsonResponseStringToString(jsonObjectResponse.body().get("token").toString().trim()));
                    registerMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, null)));
                } else if (jsonObjectResponse.code() == 400) {
                    Log.d(TAG, "HTTP Error: 400 Bad Request");
                    registerMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "400 Bad request")));
                } else if (jsonObjectResponse.code() == 401) {
                    Log.d(TAG, "HTTP Error: 401 Unauthorized");
                    registerMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "Can't create account with provided credentials")));
                } else if (jsonObjectResponse.code() == 403) {
                    Log.d(TAG, "HTTP Error: 403 Forbidden");
                    registerMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "Session expired")));
                } else if (jsonObjectResponse.code() == 404) {
                    Log.d(TAG, "HTTP Error: 404 Not Found");
                    registerMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "404 Not found")));
                } else if (jsonObjectResponse.code() == 500) {
                    Log.d(TAG, "HTTP Error: 500 Internal Server Error");
                    registerMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "500 Server error")));
                } else {
                    Log.d(TAG, "Unknown Error");
                    registerMutableLiveData.postValue(new Event<>(new ResponseWrapperJsonObject(jsonObject, "Unknown Error")));
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

    public void validateInput(TextInputLayout nameTextInputLayout, TextInputLayout emailTextInputLayout,
                              TextInputLayout passwordTextInputLayout, EditText nameEditText,
                              EditText emailEditText, EditText passwordEditText) {
        nameEditText.addTextChangedListener(new TextValidator(nameEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() < FIELDS.USERNAME.minLength) {
                    nameTextInputLayout.setError("Name is too short");
                } else if (text.length() > FIELDS.USERNAME.maxLength) {
                    nameTextInputLayout.setError("Name is too long");
                } else {
                    nameTextInputLayout.setError(null);
                }
            }
        });

        emailEditText.addTextChangedListener(new TextValidator(emailEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() < FIELDS.EMAIL.minLength) {
                    emailTextInputLayout.setError("E-mail is too short");
                } else if (text.length() > FIELDS.EMAIL.maxLength) {
                    emailTextInputLayout.setError("E-mail is too long");
                } else {
                    emailTextInputLayout.setError(null);
                }
            }
        });

        passwordEditText.addTextChangedListener(new TextValidator(passwordEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() < FIELDS.USER_PASSWORD.minLength) {
                    passwordTextInputLayout.setError("Password is too short");
                } else if (text.length() > FIELDS.USER_PASSWORD.maxLength) {
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

    public void revokeToken() {
        Application.getInstance().setToken("");
    }
}
