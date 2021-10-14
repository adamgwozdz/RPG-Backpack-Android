package com.wodu.mobile.rpg_backpack.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Session;
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

            viewModel.createSession("test", "Qwerty1!", 10, null).observe(this, new androidx.lifecycle.Observer<Session>() {
            @Override
            public void onChanged(Session session) {
                Log.d(TAG, "onChanged: " + session.toString());

            }
        });
    }
}