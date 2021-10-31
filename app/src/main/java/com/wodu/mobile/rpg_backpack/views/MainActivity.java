package com.wodu.mobile.rpg_backpack.views;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.adapters.SessionsListAdapter;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.utilities.Loading;
import com.wodu.mobile.rpg_backpack.viewmodels.MainActivityViewModel;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private MainActivityViewModel viewModel;
    private ProgressBar loadingProgressBar;

    private List<Session> sessionList;
    private int sessionCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        loadingProgressBar = findViewById(R.id.activity_main_loading_spinner);

        setupHeader();
        setupButtons();
        sendGetSessionsRequest();
    }

    private void setupHeader() {
        TextView usernameTextView = findViewById(R.id.activity_main_session_name);
        TextView emailConfirmationMessageTextView = findViewById(R.id.activity_main_email_confirmation_message);
        TextView sessionCountTextView = findViewById(R.id.activity_main_sessions_count);

        viewModel.setUserName(usernameTextView);
        viewModel.scaleTextSize(usernameTextView);
        viewModel.setSubscriptionIconVisibility(usernameTextView);
        viewModel.setEmailConfirmationMessage(emailConfirmationMessageTextView);
        viewModel.setSessionCount(sessionCountTextView, sessionCount);
    }

    private void setupButtons() {
        ExtendedFloatingActionButton fabMain = findViewById(R.id.activity_main_action_button);
        ExtendedFloatingActionButton fabLogout = findViewById(R.id.activity_main_action_button_logout);
        ExtendedFloatingActionButton fabSubscribe = findViewById(R.id.activity_main_action_button_subscription);
        ExtendedFloatingActionButton fabCreate = findViewById(R.id.activity_main_action_button_create);
        ExtendedFloatingActionButton fabJoin = findViewById(R.id.activity_main_action_button_join);
        RecyclerView recyclerView = findViewById(R.id.activity_main_body_sessions_recycler_view);

        List<ExtendedFloatingActionButton> buttonList = new LinkedList<>();
        buttonList.add(fabMain);
        buttonList.add(fabJoin);
        buttonList.add(fabCreate);
        buttonList.add(fabSubscribe);
        buttonList.add(fabLogout);
        viewModel.setupFloatingActionButtons(buttonList, recyclerView);
    }

    private void sendGetSessionsRequest() {
        Loading.showLoading(loadingProgressBar);
        viewModel.getSessions(true).observe(this, new androidx.lifecycle.Observer<List<Session>>() {
            @Override
            public void onChanged(List<Session> sessions) {
                Loading.hideLoading(loadingProgressBar);
                sessionList = sessions;
                sessionCount = sessions.size();
                setupHeader();
                setupSessionsList();
            }
        });
        Loading.showLoading(loadingProgressBar);
    }

    private void setupSessionsList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.activity_main_body_sessions_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        SessionsListAdapter adapter = new SessionsListAdapter(this, sessionList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
    }
}