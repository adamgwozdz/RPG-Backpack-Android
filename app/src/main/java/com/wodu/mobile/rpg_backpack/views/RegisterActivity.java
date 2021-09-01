package com.wodu.mobile.rpg_backpack.views;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import com.wodu.mobile.rpg_backpack.R;


public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(0, 0);
    }
}