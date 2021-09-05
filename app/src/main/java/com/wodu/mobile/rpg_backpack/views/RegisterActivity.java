package com.wodu.mobile.rpg_backpack.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.viewmodels.RegisterActivityViewModel;


public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "RegisterActivity";

    private RegisterActivityViewModel viewModel = new RegisterActivityViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText nameEditText = findViewById(R.id.activity_register_name_field_edit_text);
        EditText emailEditText = findViewById(R.id.activity_register_email_field_edit_text);
        EditText passwordEditText = findViewById(R.id.activity_register_password_field_edit_text);
        Button registerButton = findViewById(R.id.activity_register_signup_button);

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            viewModel.register(email, name, password, false).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String token) {
                    if (token.length() > 120)
                        RedirectToMainActivity();
                    else
                        Log.i(TAG, "Error: Incorrect token, couldn't redirect to MainActivity");
                }
            });
        });
    }

    private void RedirectToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(0, 0);
    }
}