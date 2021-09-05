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
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
