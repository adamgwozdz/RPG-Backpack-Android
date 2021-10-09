package com.wodu.mobile.rpg_backpack.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wodu.mobile.rpg_backpack.ErrorHandlingAdapter;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.repositories.SessionRepository;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityViewModel extends ViewModel {

    private final String TAG = "MainActivityViewModel";

    private final SessionRepository sessionRepository = SessionRepository.getInstance();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Session> sessionMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Session>> sessionsListMutableLiveData = new MutableLiveData<>();

    public MainActivityViewModel() {}

    public MutableLiveData<List<Session>> getSessions(boolean onlyCurrentUser) {
        if (onlyCurrentUser)
            loadUserSessionsData();
        else
            loadSessionsData();

        return sessionsListMutableLiveData;
    }

    public MutableLiveData<Session> getSession(long userId) {
        loadSessionData(userId);
        return sessionMutableLiveData;
    }

    private void loadSessionsData() {
        sessionRepository.getSessions().subscribe(new Observer<List<Session>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull List<Session> sessions) {
                sessionsListMutableLiveData.postValue(sessions);
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

    private void loadUserSessionsData() {
        sessionRepository.getUserSessions().subscribe(new Observer<List<Session>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull List<Session> sessions) {
                sessionsListMutableLiveData.postValue(sessions);
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

    private void loadSessionData(long userId) {
        sessionRepository.getSessionsByUserId(userId).subscribe(new Observer<Session>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: called");
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull Session session) {
                sessionMutableLiveData.postValue(session);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: called");
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
