package com.wodu.mobile.rpg_backpack.views;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private MainActivityViewModel viewModel = new MainActivityViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //For testing purposes only
//        viewModel.getSession(2).observe(this, new Observer<Session>() {
//            @Override
//            public void onChanged(Session session) {
//                //Log.d(TAG, "Session: " + session.toString());
//            }
//        });

        viewModel.getSessions(true).observe(this, new Observer<List<Session>>() {
            @Override
            public void onChanged(List<Session> session) {
//                Log.d(TAG, "Sessions: " + session.get(0).toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}