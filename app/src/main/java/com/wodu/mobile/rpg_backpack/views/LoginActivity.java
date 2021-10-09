package com.wodu.mobile.rpg_backpack.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.databinding.ActivityLoginBinding;
import com.wodu.mobile.rpg_backpack.databinding.ActivityMainBinding;
import com.wodu.mobile.rpg_backpack.viewmodels.LoginActivityViewModel;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    private LoginActivityViewModel viewModel = new LoginActivityViewModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        revokeToken();

        EditText emailEditText = findViewById(R.id.activity_login_email_field_edit_text);
        EditText passwordEditText = findViewById(R.id.activity_login_password_field_edit_text);

        Button loginButton = findViewById(R.id.activity_login_login_button);
        Button registerButton = findViewById(R.id.activity_login_signup_button);

        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            viewModel.login(email, password).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String response) {
                    if (response.length() == 191)
                        RedirectToMainActivity();
                    else if (response.equals("HTTP 401")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Incorrect E-mail or Password", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "UNAUTHORIZED");
                            }
                        });
                    }
                    viewModel.login(email, password).removeObserver(this);
                }
            });
        });

        registerButton.setOnClickListener(view ->
        {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }

    private void RedirectToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void revokeToken() {
        Application.getInstance().setToken("");
    }
}