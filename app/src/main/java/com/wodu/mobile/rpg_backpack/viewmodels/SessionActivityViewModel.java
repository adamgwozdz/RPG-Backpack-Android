package com.wodu.mobile.rpg_backpack.viewmodels;

import android.widget.TextView;

import androidx.lifecycle.ViewModel;

public class SessionActivityViewModel extends ViewModel {


    public SessionActivityViewModel() {
    }

    @Override
    protected void onCleared() {
        super.onCleared();
//        disposables.clear();
    }

    public void setSessionName(TextView textView, String sessionName) {
        textView.setText(sessionName);
    }
}
