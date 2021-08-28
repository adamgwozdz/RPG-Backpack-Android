package com.wodu.mobile.rpg_backpack;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.wodu.mobile.rpg_backpack.entities.Session;
import com.wodu.mobile.rpg_backpack.services.SessionService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        SessionService sessionService = new SessionService(MainActivity.this);
//        sessionService.getCurrentUserSessionNames(new SessionService.VolleyResponseListener() {
//            @Override
//            public void onResponse(List<Session> response) {
//                Log.i(TAG, "Response: " + response.get(0).toString());
//            }
//
//            @Override
//            public void onError(String message) {
//                Log.i(TAG, "Error: " + message);
//            }
//        }, Application.getInstance().getAccessToken());
    }
}