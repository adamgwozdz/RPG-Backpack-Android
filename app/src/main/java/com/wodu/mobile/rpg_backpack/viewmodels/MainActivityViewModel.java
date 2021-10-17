package com.wodu.mobile.rpg_backpack.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.repositories.SessionRepository;
import com.wodu.mobile.rpg_backpack.repositories.UserRepository;
import com.wodu.mobile.rpg_backpack.views.CreateSessionActivity;
import com.wodu.mobile.rpg_backpack.views.LoginActivity;
import com.wodu.mobile.rpg_backpack.views.MainActivity;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainActivityViewModel extends ViewModel {

    private final String TAG = "MainActivityViewModel";

    private final SessionRepository sessionRepository = SessionRepository.getInstance();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Session> sessionMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Session>> sessionsListMutableLiveData = new MutableLiveData<>();

    public MainActivityViewModel() {
    }

    public MutableLiveData<List<Session>> getSessions(boolean forCurrentUser) {
        if (forCurrentUser)
            loadUserSessionsData();
        else
            loadSessionsData();

        return sessionsListMutableLiveData;
    }

    public MutableLiveData<Session> getSession(long userId) {
        loadSessionData(userId);
        return sessionMutableLiveData;
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

    public void scaleTextSize(TextView textView) {
        int length = textView.length();
        if (length <= 10) {
            textView.setTextSize(25);
        } else {
            textView.setTextSize(18);
        }
    }

    public void setUserName(TextView textView) {
        textView.setText(Application.getInstance().getName());
    }

    public void setEmailConfirmationMessage(TextView textView) {
        if (Application.getInstance().getEmailVerified())
            textView.setText(R.string.email_verified);
        else
            textView.setText(R.string.email_unverified);
    }

    public void setSubscriptionIconVisibility(TextView textView) {
        if (!Application.getInstance().getSubscription())
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    public void setupFloatingActionButtons(List<ExtendedFloatingActionButton> buttonList, RecyclerView recyclerView) {
        float translationY = 100f;
        final Boolean[] menuOpen = {false};

        buttonList.get(1).setAlpha(0f);
        buttonList.get(2).setAlpha(0f);
        buttonList.get(3).setAlpha(0f);
        buttonList.get(4).setAlpha(0f);
        buttonList.get(1).setTranslationY(translationY);
        buttonList.get(2).setTranslationY(translationY);
        buttonList.get(3).setTranslationY(translationY);
        buttonList.get(4).setTranslationY(translationY);

        buttonList.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuOpen[0]) {
                    menuOpen[0] = closeMenu(menuOpen[0], buttonList, translationY);
                    recyclerView.setAlpha(1f);
                }
                else {
                    menuOpen[0] = openMenu(menuOpen[0], buttonList);
                    recyclerView.setAlpha(0.15f);
                }
            }
        });

        buttonList.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Join");
                menuOpen[0] = closeMenu(menuOpen[0], buttonList, translationY);
                recyclerView.setAlpha(1f);
            }
        });

        buttonList.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Create");
                recyclerView.setAlpha(1f);
                Intent intent = new Intent(view.getContext(), CreateSessionActivity.class);
                view.getContext().startActivity(intent);
                ((Activity) view.getContext()).overridePendingTransition(0, 0);
            }
        });

        buttonList.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Subscribe");
                recyclerView.setAlpha(1f);
                menuOpen[0] = closeMenu(menuOpen[0], buttonList, translationY);
            }
        });

        buttonList.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Logout");
                recyclerView.setAlpha(1f);
                Application.getInstance().resetToken();
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);
                ((Activity) view.getContext()).overridePendingTransition(0, 0);
            }
        });
    }

    private Boolean openMenu(boolean menuOpen, List<ExtendedFloatingActionButton> buttonList) {
        OvershootInterpolator interpolator = new OvershootInterpolator();
        buttonList.get(0).setIconResource(R.drawable.ic_arrow_down);
        buttonList.get(1).setVisibility(View.VISIBLE);
        buttonList.get(2).setVisibility(View.VISIBLE);
        buttonList.get(3).setVisibility(View.VISIBLE);
        buttonList.get(4).setVisibility(View.VISIBLE);

        buttonList.get(1).animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        buttonList.get(2).animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        buttonList.get(3).animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        buttonList.get(4).animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        return !menuOpen;
    }

    private Boolean closeMenu(boolean menuOpen, List<ExtendedFloatingActionButton> buttonList, float translationY) {
        OvershootInterpolator interpolator = new OvershootInterpolator();
        buttonList.get(0).setIconResource(R.drawable.ic_arrow_up);
        buttonList.get(1).animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        buttonList.get(2).animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        buttonList.get(3).animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        buttonList.get(4).animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();

        buttonList.get(1).setVisibility(View.GONE);
        buttonList.get(2).setVisibility(View.GONE);
        buttonList.get(3).setVisibility(View.GONE);
        buttonList.get(4).setVisibility(View.GONE);
        return !menuOpen;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
