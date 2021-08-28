package com.wodu.mobile.rpg_backpack.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.MainActivity;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.entities.UserService;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "RegisterActivity";
    private String accessToken = "Bearer ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText nameEditText = findViewById(R.id.activity_register_name_field_edit_text);
        EditText emailEditText = findViewById(R.id.activity_register_email_field_edit_text);
        EditText passwordEditText = findViewById(R.id.activity_register_password_field_edit_text);

        Button registerButton = findViewById(R.id.activity_register_signup_button);

        registerButton.setOnClickListener(view -> {
            HashMap request = new HashMap();
            request.put("name", nameEditText.getText().toString());
            request.put("email", emailEditText.getText().toString());
            request.put("password", passwordEditText.getText().toString());
            request.put("subscription", false);

            UserService userService = new UserService(RegisterActivity.this);
            userService.register(new UserService.VolleyResponseListener() {
                @Override
                public void onResponse(String response) {
                    accessToken += response;

                    Application.getInstance().setToken(accessToken);

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }

                @Override
                public void onError(String message) {
                    Log.i(TAG, "Error: " + message);
                }
            }, request);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(0,0);
    }
}