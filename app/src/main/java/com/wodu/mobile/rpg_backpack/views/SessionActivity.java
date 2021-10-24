package com.wodu.mobile.rpg_backpack.views;

import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wodu.mobile.rpg_backpack.R;

public class SessionActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView;
        NavController navController = Navigation.findNavController(this, R.id.activity_session_fragment);
        boolean isGameMaster = getIntent().getBooleanExtra("isGameMaster", false);
        if (isGameMaster)
            bottomNavigationView = findViewById(R.id.activity_session_bottom_navigation_view_game_master);
        else
            bottomNavigationView = findViewById(R.id.activity_session_bottom_navigation_view);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}