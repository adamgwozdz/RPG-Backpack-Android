package com.wodu.mobile.rpg_backpack.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.response_wrappers.Event;
import com.wodu.mobile.rpg_backpack.response_wrappers.ResponseWrapperJsonObject;
import com.wodu.mobile.rpg_backpack.utilities.AndroidUtilities;
import com.wodu.mobile.rpg_backpack.utilities.Converters;
import com.wodu.mobile.rpg_backpack.utilities.Loading;
import com.wodu.mobile.rpg_backpack.utilities.Redirections;
import com.wodu.mobile.rpg_backpack.viewmodels.CreateSessionActivityViewModel;
import com.wodu.mobile.rpg_backpack.viewmodels.RegisterActivityViewModel;

public class CreateSessionActivity extends AppCompatActivity {

    private final String TAG = "CreateSessionActivity";

    private CreateSessionActivityViewModel viewModel;
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
        viewModel = new ViewModelProvider(this).get(CreateSessionActivityViewModel.class);
        AndroidUtilities.setupUI(this, findViewById(R.id.activity_create_master_view));

        nameEditText = findViewById(R.id.activity_create_session_name_field_edit_text);
        passwordEditText = findViewById(R.id.activity_create_session_password_field_edit_text);
        nameTextInputLayout = findViewById(R.id.activity_create_session_name_field_text_input_layout);
        passwordTextInputLayout = findViewById(R.id.activity_create_session_password_field_text_input_layout);
        passwordEditText = findViewById(R.id.activity_create_session_password_field_edit_text);
        createButton = findViewById(R.id.activity_create_session_create_button);
        loadingProgressBar = findViewById(R.id.activity_create_session_loading_spinner);

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
                sendCreateSessionRequest(view);
        });
    }

    private void sendCreateSessionRequest(View view) {
        int maxAttributes = viewModel.getMaxAttributesNumber(Application.getInstance().getEmailVerified());
        Loading.showLoading(loadingProgressBar);
        viewModel.createSession(nameEditText.getText().toString().trim(),
                passwordEditText.getText().toString().trim(),
                maxAttributes, null).observe(this, new Observer<Event<ResponseWrapperJsonObject>>() {
            @Override
            public void onChanged(Event<ResponseWrapperJsonObject> responseWrapper) {
                if (!responseWrapper.hasBeenHandled()) {
                    ResponseWrapperJsonObject response = responseWrapper.getContentIfNotHandled();
                    if (response.getErrorMessage() == null) {
                        Session session = Converters.convertToSession(response.getBody());
                        sendCreateCharacterRequest(view, session);
                    } else {
                        Loading.hideLoading(loadingProgressBar);
                        Snackbar.make(view, response.getErrorMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });
        Loading.showLoading(loadingProgressBar);
    }

    private void sendCreateCharacterRequest(View view, Session session) {
        Loading.showLoading(loadingProgressBar);
        viewModel.createCharacter(Application.getInstance().getUserID(), session.getSessionID(),
                "Game master", true, null).observe(this, new Observer<Event<ResponseWrapperJsonObject>>() {
            @Override
            public void onChanged(Event<ResponseWrapperJsonObject> responseWrapper) {
                if (!responseWrapper.hasBeenHandled()) {
                    ResponseWrapperJsonObject response = responseWrapper.getContentIfNotHandled();
                    if (response.getErrorMessage() == null) {
                        Character character = Converters.convertToCharacter(response.getBody());
                        //Character character = viewModel.convertToCharacter(response.getBody());
                        redirectToSessionActivity(session.getSessionID(), session.getName(), character.getGameMaster());
                    } else {
                        Loading.hideLoading(loadingProgressBar);
                        Snackbar.make(view, response.getErrorMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });
        Loading.showLoading(loadingProgressBar);
    }

    private void redirectToSessionActivity(int sessionID, String sessionName, boolean isGameMaster) {
        Redirections.redirectToSessionActivity(this, sessionID, sessionName, isGameMaster);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Redirections.redirectToMainActivity(this);
        overridePendingTransition(0, 0);
    }
}