package com.wodu.mobile.rpg_backpack.viewmodels;


import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.repositories.UserRepository;
import com.wodu.mobile.rpg_backpack.utilities.FIELDS;
import com.wodu.mobile.rpg_backpack.utilities.TextValidator;
import com.wodu.mobile.rpg_backpack.utilities.Utilities;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class RegisterActivityViewModel extends ViewModel {

    private final String TAG = "RegisterViewModel";

    private final UserRepository userRepository = UserRepository.getInstance();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<String> registrationMutableLiveData = new MutableLiveData<>();

    public RegisterActivityViewModel() {}

    public MutableLiveData<String> register(String email, String name, String password, boolean subscription) {
        loadRegisterData(email, name, password, subscription);
        return registrationMutableLiveData;
    }

    private void loadRegisterData(String email, String name, String password, boolean subscription) {
        userRepository.register(email, name, password, subscription).subscribe(new Observer<JsonObject>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull JsonObject jsonResponse) {
                String token = Utilities.jsonResponseStringToString(jsonResponse.get("token").toString());
                Log.d(TAG, "Token: " + token);
                Application.getInstance().setToken(token);
                registrationMutableLiveData.postValue(token);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                String error = e.getMessage();
                registrationMutableLiveData.postValue(error.trim());
                Log.d(TAG, "onError: " + error);
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
}
