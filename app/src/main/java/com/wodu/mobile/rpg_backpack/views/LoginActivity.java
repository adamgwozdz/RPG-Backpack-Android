package com.wodu.mobile.rpg_backpack.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.wodu.mobile.rpg_backpack.Event;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.ResponseWrapper;
import com.wodu.mobile.rpg_backpack.utilities.AndroidUtilities;
import com.wodu.mobile.rpg_backpack.utilities.Loading;
import com.wodu.mobile.rpg_backpack.viewmodels.LoginActivityViewModel;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";

    private LoginActivityViewModel viewModel = new LoginActivityViewModel();
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private Button loginButton;
    private Button registerButton;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AndroidUtilities.setupUI(this, findViewById(R.id.activity_login_master_view));
        viewModel.revokeToken();

        emailEditText = findViewById(R.id.activity_login_email_field_edit_text);
        passwordEditText = findViewById(R.id.activity_login_password_field_edit_text);
        emailTextInputLayout = findViewById(R.id.activity_login_email_field_text_input_layout);
        passwordTextInputLayout = findViewById(R.id.activity_login_password_field_text_input_layout);
        loginButton = findViewById(R.id.activity_login_login_button);
        registerButton = findViewById(R.id.activity_login_signup_button);
        loadingProgressBar = findViewById(R.id.activity_login_loading_spinner);

        setupLoginButton();
        viewModel.validateInput(emailTextInputLayout, passwordTextInputLayout, emailEditText, passwordEditText);

        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }

    private void setupLoginButton() {
        loginButton.setOnClickListener(view -> {
            int emailFieldLength = emailEditText.getText().toString().trim().length();
            int passwordFieldLength = passwordEditText.getText().toString().trim().length();

            if (emailFieldLength == 0)
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
        String password = passwordEditText.getText().toString().trim();

        Loading.showLoading(loadingProgressBar);
        viewModel.login(email, password).observe(this, new Observer<Event<ResponseWrapper>>() {
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
}