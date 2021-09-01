package com.wodu.mobile.rpg_backpack.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.wodu.mobile.rpg_backpack.R;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.activity_login_login_button);
        Button registerButton = findViewById(R.id.activity_login_signup_button);

        loginButton.setOnClickListener(view -> {

        });

        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }
}