package com.wodu.mobile.rpg_backpack.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.Event;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.ResponseWrapper;
import com.wodu.mobile.rpg_backpack.utilities.AndroidUtilities;
import com.wodu.mobile.rpg_backpack.utilities.Loading;
import com.wodu.mobile.rpg_backpack.viewmodels.RegisterActivityViewModel;


public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "RegisterActivity";

    private RegisterActivityViewModel viewModel = new RegisterActivityViewModel();
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextInputLayout nameTextInputLayout;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private Button registerButton;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AndroidUtilities.setupUI(this, findViewById(R.id.activity_register_master_view));
        viewModel.revokeToken();

        nameEditText = findViewById(R.id.activity_register_name_field_edit_text);
        emailEditText = findViewById(R.id.activity_register_email_field_edit_text);
        passwordEditText = findViewById(R.id.activity_register_password_field_edit_text);
        nameTextInputLayout = findViewById(R.id.activity_register_name_field_text_input_layout);
        emailTextInputLayout = findViewById(R.id.activity_register_email_field_text_input_layout);
        passwordTextInputLayout = findViewById(R.id.activity_register_password_field_text_input_layout);
        registerButton = findViewById(R.id.activity_register_signup_button);
        loadingProgressBar = findViewById(R.id.activity_register_loading_spinner);

        setupRegisterButton();
        viewModel.validateInput(nameTextInputLayout, emailTextInputLayout, passwordTextInputLayout,
                nameEditText, emailEditText, passwordEditText);
    }

    private void setupRegisterButton() {
        registerButton.setOnClickListener(view -> {
            int nameFieldLength = nameEditText.getText().toString().trim().length();
            int emailFieldLength = emailEditText.getText().toString().trim().length();
            int passwordFieldLength = passwordEditText.getText().toString().trim().length();

            if (nameFieldLength == 0)
                Snackbar.make(view, "Name cannot be empty", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            else if (emailFieldLength == 0)
                Snackbar.make(view, "E-mail cannot be empty", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            else if (passwordFieldLength == 0)
                Snackbar.make(view, "Password cannot be empty", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            else
                sendRequest(view);
        });
    }

    private void sendRequest(View view) {
        String email = emailEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        Loading.showLoading(loadingProgressBar);
        viewModel.register(email, name, password, false).observe(this, new Observer<Event<ResponseWrapper>>() {
            @Override
            public void onChanged(Event<ResponseWrapper> responseWrapper) {
                if (!responseWrapper.hasBeenHandled()) {
                    String error = responseWrapper.getContentIfNotHandled().getErrorMessage();
                    if (error == null) {
                        redirectToMainActivity();
                    } else {
                        Loading.hideLoading(loadingProgressBar);
                        Snackbar.make(view, error, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });
        Loading.showLoading(loadingProgressBar);
    }

    private void redirectToMainActivity() {
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