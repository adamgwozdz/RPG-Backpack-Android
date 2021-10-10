package com.wodu.mobile.rpg_backpack.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.repositories.UserRepository;
import com.wodu.mobile.rpg_backpack.utilities.Utilities;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class LoginActivityViewModel extends ViewModel {

    private final String TAG = "LoginActivityViewModel";

    private final UserRepository userRepository = UserRepository.getInstance();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<String> loginMutableLiveData = new MutableLiveData<>();

    public LoginActivityViewModel() {}

    public MutableLiveData<String> login(String email, String password) {
        loadLoginData(email, password);
        return loginMutableLiveData;
    }

    private void loadLoginData(String email, String password) {
        userRepository.login(email, password).subscribe(new Observer<JsonObject>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull JsonObject jsonResponse) {
                String token = Utilities.jsonResponseStringToString(jsonResponse.get("token").toString());
                Application.getInstance().setToken(token);
                loginMutableLiveData.postValue(token);
                Log.d(TAG, "Token: " + token);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                String error = e.getMessage();
                loginMutableLiveData.postValue(error.trim());
                Log.d(TAG, "onError: " + error);
            }

            @Override
            public void onComplete() {
                disposables.clear();
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}






















