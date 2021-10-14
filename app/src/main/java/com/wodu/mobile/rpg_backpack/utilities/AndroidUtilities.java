package com.wodu.mobile.rpg_backpack.utilities;

import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public abstract class AndroidUtilities extends AppCompatActivity {

    public static void loadingSpinner(ProgressBar progressBar, boolean hide) {
        if (hide)
            progressBar.setVisibility(View.GONE);
        else
            progressBar.setVisibility(View.VISIBLE);
    }
}
