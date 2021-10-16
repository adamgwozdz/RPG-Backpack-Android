package com.wodu.mobile.rpg_backpack.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.utilities.AndroidUtilities;
import com.wodu.mobile.rpg_backpack.utilities.FIELDS;
import com.wodu.mobile.rpg_backpack.utilities.TextValidator;
import com.wodu.mobile.rpg_backpack.viewmodels.CreateSessionActivityViewModel;
import com.wodu.mobile.rpg_backpack.viewmodels.MainActivityViewModel;

import java.util.List;

public class CreateSessionActivity extends AppCompatActivity {

    private final String TAG = "CreateSessionActivity";

    private final CreateSessionActivityViewModel viewModel = new CreateSessionActivityViewModel();
    private EditText nameEditText;
    private EditText passwordEditText;
    private TextInputLayout nameTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private Button createButton;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        nameEditText = findViewById(R.id.activity_create_session_name_field_edit_text);
        passwordEditText = findViewById(R.id.activity_create_session_password_field_edit_text);
        nameTextInputLayout = findViewById(R.id.activity_create_session_name_field_text_input_layout);
        passwordTextInputLayout = findViewById(R.id.activity_create_session_password_field_text_input_layout);
        passwordEditText = findViewById(R.id.activity_create_session_password_field_edit_text);
        createButton = findViewById(R.id.activity_create_session_create_button);
        loadingProgressBar = findViewById(R.id.activity_create_loading_spinner);

        setupCreateButton();
        viewModel.validateInput(nameTextInputLayout, passwordTextInputLayout, nameEditText, passwordEditText);
    }

    private void setupCreateButton() {
        createButton.setOnClickListener(view -> {
            int nameFieldLength = nameEditText.getText().toString().trim().length();
            int passwordFieldLength = passwordEditText.getText().toString().trim().length();

            if (nameFieldLength == 0)
                Snackbar.make(view, "Session name cannot be empty", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            else if (passwordFieldLength == 0)
                Snackbar.make(view, "Password cannot be empty", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            else
                sendRequest();
        });
    }

    private void sendRequest() {
        int maxAttributes = viewModel.getMaxAttributesNumber(Application.getInstance().getEmailVerified());
        AndroidUtilities.loadingSpinner(loadingProgressBar, true);
        viewModel.createSession(nameEditText.getText().toString().trim(),
                passwordEditText.getText().toString().trim(),
                maxAttributes, null).observe(this, new androidx.lifecycle.Observer<Session>() {
            @Override
            public void onChanged(Session session) {
                Log.d(TAG, "Session: " + session.toString());
                createCharacter(session);
            }
        });
        AndroidUtilities.loadingSpinner(loadingProgressBar, true);
    }

    private void createCharacter(Session session) {
        AndroidUtilities.loadingSpinner(loadingProgressBar, true);
        viewModel.createCharacter(Application.getInstance().getUserID(), session.getSessionID(),
                "Edit name", true, null).observe(this, new Observer<Character>() {
            @Override
            public void onChanged(Character character) {

                AndroidUtilities.loadingSpinner(loadingProgressBar, false);
            }
        });
        AndroidUtilities.loadingSpinner(loadingProgressBar, true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}