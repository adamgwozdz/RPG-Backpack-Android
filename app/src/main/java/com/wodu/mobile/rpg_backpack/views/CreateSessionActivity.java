package com.wodu.mobile.rpg_backpack.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.utilities.AndroidUtilities;
import com.wodu.mobile.rpg_backpack.viewmodels.CreateSessionActivityViewModel;
import com.wodu.mobile.rpg_backpack.viewmodels.MainActivityViewModel;

import java.util.List;

public class CreateSessionActivity extends AppCompatActivity {

    private final String TAG = "CreateSessionActivity";

    private final CreateSessionActivityViewModel viewModel = new CreateSessionActivityViewModel();
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        EditText nameEditText = findViewById(R.id.activity_create_session_name_field_edit_text);
        EditText passwordEditText = findViewById(R.id.activity_create_session_password_field_edit_text);

        Button createButton = findViewById(R.id.activity_create_session_create_button);

        int maxAttributes = viewModel.getMaxAttributesNumber(Application.getInstance().getEmailVerified());

        //TODO prevent input of empty name and password
        createButton.setOnClickListener(view -> {
            ProgressBar loadingProgressBar = findViewById(R.id.activity_create_loading_spinner);
            AndroidUtilities.loadingSpinner(loadingProgressBar, true);
            viewModel.createSession(nameEditText.getText().toString().trim(),
                    passwordEditText.getText().toString().trim(),
                    maxAttributes, null).observe(this, new androidx.lifecycle.Observer<Session>() {
                @Override
                public void onChanged(Session session) {
                    Log.d(TAG, "Session: " + session.toString());
                    createChar(session);
                    AndroidUtilities.loadingSpinner(loadingProgressBar, false);
                }
            });
            AndroidUtilities.loadingSpinner(loadingProgressBar, true);
        });

    }

    private void createChar(Session session) {
        viewModel.createCharacter(Application.getInstance().getUserID(), session.getSessionID(),
                "Edit name", true, null).observe(this, new Observer<Character>() {
            @Override
            public void onChanged(Character character) {

            }
        });
    }

    public Activity getActivity(Context context)
    {
        if (context == null)
        {
            return null;
        }
        else if (context instanceof ContextWrapper)
        {
            if (context instanceof Activity)
            {
                return (Activity) context;
            }
            else
            {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }

        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}