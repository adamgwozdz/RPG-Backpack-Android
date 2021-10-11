package com.wodu.mobile.rpg_backpack.views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.viewmodels.MainActivityViewModel;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private final MainActivityViewModel viewModel = new MainActivityViewModel();
    private boolean isFabOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeHeader();
        initializeButtons();

        viewModel.getSessions(true).observe(this, new Observer<List<Session>>() {
            @Override
            public void onChanged(List<Session> session) {
//                Log.d(TAG, "Sessions: " + session.get(0).toString());
            }
        });
    }

    private void initializeHeader() {
        TextView usernameTextView = findViewById(R.id.activity_main_user_name);
        TextView emailConfirmationMessage = findViewById(R.id.activity_main_email_confirmation_message);

        viewModel.setUserName(usernameTextView);
        viewModel.scaleTextSize(usernameTextView);
        viewModel.setSubscriptionIconVisibility(usernameTextView);

        viewModel.setEmailConfirmationMessage(emailConfirmationMessage);
    }

    private void initializeButtons() {
        ExtendedFloatingActionButton fabMain = findViewById(R.id.activity_main_action_button);
        ExtendedFloatingActionButton fabCreate = findViewById(R.id.activity_main_action_button_create);
        ExtendedFloatingActionButton fabJoin = findViewById(R.id.activity_main_action_button_join);
        List<ExtendedFloatingActionButton> buttonList = new LinkedList<>();
        buttonList.add(fabMain);
        buttonList.add(fabCreate);
        buttonList.add(fabJoin);
        viewModel.setupFloatingActionButtons(buttonList);
    }

    @Override
    public void onBackPressed() {
    }
}