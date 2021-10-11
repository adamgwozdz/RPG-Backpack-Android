package com.wodu.mobile.rpg_backpack.viewmodels;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.repositories.SessionRepository;
import com.wodu.mobile.rpg_backpack.repositories.UserRepository;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainActivityViewModel extends ViewModel {

    private final String TAG = "MainActivityViewModel";

    private final SessionRepository sessionRepository = SessionRepository.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Session> sessionMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Session>> sessionsListMutableLiveData = new MutableLiveData<>();

    private boolean isFabOpen = false;

    public MainActivityViewModel() {
    }

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

//    private void loadCurrentUserData() {
//        userRepository.
//    }

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

//    public boolean setButton(Resources resources, FloatingActionButton fabMain,
//                          FloatingActionButton fabCreate, FloatingActionButton fabJoin) {
//        fabMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!isFabOpen) {
//                    showFabMenu(resources, fabCreate, fabJoin);
//                } else {
//                    closeFabMenu(fabCreate, fabJoin);
//                }
//            }
//        });
//        return isFabOpen;
//    }
//
//    private void showFabMenu(Resources resources, FloatingActionButton fabCreate, FloatingActionButton fabJoin) {
//        isFabOpen = true;
//        fabCreate.animate().translationY(-resources.getDimension(R.dimen._55sdp));
//        fabJoin.animate().translationY(-resources.getDimension(R.dimen._105sdp));
//    }
//
//    private void closeFabMenu(FloatingActionButton fabCreate, FloatingActionButton fabJoin) {
//        isFabOpen = false;
//        fabCreate.animate().translationY(0);
//        fabJoin.animate().translationY(0);
//    }

    public void setupFloatingActionButtons(List<ExtendedFloatingActionButton> buttonList) {
        float translationY = 100f;
        final Boolean[] menuOpen = {false};

        buttonList.get(1).setAlpha(0f);
        buttonList.get(2).setAlpha(0f);
        buttonList.get(1).setTranslationY(translationY);
        buttonList.get(2).setTranslationY(translationY);

        buttonList.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuOpen[0])
                    menuOpen[0] = closeMenu(menuOpen[0], buttonList, translationY);
                else
                    menuOpen[0] = openMenu(menuOpen[0], buttonList);
            }
        });

        buttonList.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Create");
                menuOpen[0] = closeMenu(menuOpen[0], buttonList, translationY);
            }
        });

        buttonList.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Join");
                menuOpen[0] = closeMenu(menuOpen[0], buttonList, translationY);
            }
        });
    }

    private Boolean openMenu(boolean menuOpen, List<ExtendedFloatingActionButton> buttonList) {
        OvershootInterpolator interpolator = new OvershootInterpolator();
        buttonList.get(0).setIconResource(R.drawable.ic_arrow_down);
        buttonList.get(1).animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        buttonList.get(2).animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        return !menuOpen;
    }

    private Boolean closeMenu(boolean menuOpen, List<ExtendedFloatingActionButton> buttonList, float translationY) {
        OvershootInterpolator interpolator = new OvershootInterpolator();
        buttonList.get(0).setIconResource(R.drawable.ic_arrow_up);
        buttonList.get(1).animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        buttonList.get(2).animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        return !menuOpen;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
