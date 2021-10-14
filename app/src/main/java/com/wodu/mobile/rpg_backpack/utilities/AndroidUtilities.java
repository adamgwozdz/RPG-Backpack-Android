package com.wodu.mobile.rpg_backpack.utilities;

import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public abstract class AndroidUtilities extends AppCompatActivity {

    public static void loadingSpinner(ProgressBar progressBar, boolean show) {
        if (show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }
}
