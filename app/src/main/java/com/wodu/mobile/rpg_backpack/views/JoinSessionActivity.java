package com.wodu.mobile.rpg_backpack.views;

import androidx.appcompat.app.AppCompatActivity;

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
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.response_wrappers.Event;
import com.wodu.mobile.rpg_backpack.response_wrappers.ResponseWrapperJsonObject;
import com.wodu.mobile.rpg_backpack.utilities.AndroidUtilities;
import com.wodu.mobile.rpg_backpack.utilities.Converters;
import com.wodu.mobile.rpg_backpack.utilities.Loading;
import com.wodu.mobile.rpg_backpack.viewmodels.JoinSessionViewModel;

public class JoinSessionActivity extends AppCompatActivity {

    private final String TAG = "JoinSessionActivity";

    private final JoinSessionViewModel viewModel = new JoinSessionViewModel();
    private EditText sessionIdEditText;
    private EditText passwordEditText;
    private TextInputLayout sessionIdTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private Button joinButton;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_session);
        AndroidUtilities.setupUI(this, findViewById(R.id.activity_join_master_view));

        sessionIdEditText = findViewById(R.id.activity_join_session_id_field_edit_text);
        passwordEditText = findViewById(R.id.activity_join_session_password_field_edit_text);
        sessionIdTextInputLayout = findViewById(R.id.activity_join_session_id_field_text_input_layout);
        passwordTextInputLayout = findViewById(R.id.activity_join_session_password_field_text_input_layout);
        passwordEditText = findViewById(R.id.activity_join_session_password_field_edit_text);
        joinButton = findViewById(R.id.activity_join_session_join_button);
        loadingProgressBar = findViewById(R.id.activity_join_session_loading_spinner);

        setupJoinButton();
        viewModel.validateInput(sessionIdTextInputLayout, passwordTextInputLayout, sessionIdEditText, passwordEditText);
    }

    private void setupJoinButton() {
        joinButton.setOnClickListener(view -> {
            int sessionIdFieldLength = sessionIdEditText.getText().toString().trim().length();
            int passwordFieldLength = passwordEditText.getText().toString().trim().length();

            if (sessionIdFieldLength == 0)
                Snackbar.make(view, "Session ID cannot be empty", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            else if (passwordFieldLength == 0)
                Snackbar.make(view, "Password cannot be empty", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            else
                sendJoinSessionRequest(view);
        });
    }

    private void sendJoinSessionRequest(View view) {
        Application userData = Application.getInstance();
        Loading.showLoading(loadingProgressBar);
        viewModel.joinSession(Integer.valueOf(sessionIdEditText.getText().toString().trim()),
                userData.getUserID(), passwordEditText.getText().toString().trim(), userData.getName(),
                false, null).observe(this, new androidx.lifecycle.Observer<Event<ResponseWrapperJsonObject>>() {
            @Override
            public void onChanged(Event<ResponseWrapperJsonObject> responseWrapper) {
                if (!responseWrapper.hasBeenHandled()) {
                    ResponseWrapperJsonObject response = responseWrapper.getContentIfNotHandled();
                    if (response.getErrorMessage() == null) {
                        Session session = Converters.convertToSession(response.getBody());
                        redirectToSessionActivity(session.getSessionID(), getCurrentUsersCharacter(session).getGameMaster());
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

    private Character getCurrentUsersCharacter(Session session) {
        Integer userID = Application.getInstance().getUserID();
        Character character = new Character();
        for (int i = 0; i < session.getCharacters().size(); i++) {
            if (session.getCharacters().get(i).getUserID().equals(userID)) {
                character = session.getCharacters().get(i);
                break;
            }
        }
        return character;
    }

    private void redirectToSessionActivity(int sessionID, boolean isGameMaster) {
        Intent intent = new Intent(this, SessionActivity.class);
        intent.putExtra("sessionID", sessionID);
        intent.putExtra("isGameMaster", isGameMaster);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}