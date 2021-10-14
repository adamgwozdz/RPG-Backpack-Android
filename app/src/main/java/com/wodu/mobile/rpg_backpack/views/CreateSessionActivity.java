package com.wodu.mobile.rpg_backpack.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.utilities.AndroidUtilities;
import com.wodu.mobile.rpg_backpack.viewmodels.CreateSessionActivityViewModel;
import com.wodu.mobile.rpg_backpack.viewmodels.MainActivityViewModel;

import java.util.List;

public class CreateSessionActivity extends AppCompatActivity {

    private final String TAG = "CreateSessionActivity";

    private final CreateSessionActivityViewModel viewModel = new CreateSessionActivityViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        EditText nameEditText = findViewById(R.id.activity_create_session_name_field_edit_text);
        EditText passwordEditText = findViewById(R.id.activity_create_session_password_field_edit_text);

        Button createButton = findViewById(R.id.activity_create_session_create_button);

        int maxAttributes = viewModel.getMaxAttributesNumber(Application.getInstance().getEmailVerified());

        createButton.setOnClickListener(view -> {
            ProgressBar loadingProgressBar = findViewById(R.id.activity_create_loading_spinner);
            AndroidUtilities.loadingSpinner(loadingProgressBar, true);
            viewModel.createSession(nameEditText.getText().toString().trim(),
                    passwordEditText.getText().toString().trim(),
                    maxAttributes, null).observe(this, new androidx.lifecycle.Observer<Session>() {
                @Override
                public void onChanged(Session session) {
                    Log.d(TAG, "Session: " + session.toString());
                    AndroidUtilities.loadingSpinner(loadingProgressBar, false);
                }
            });
            AndroidUtilities.loadingSpinner(loadingProgressBar, true);
        });

    }


}