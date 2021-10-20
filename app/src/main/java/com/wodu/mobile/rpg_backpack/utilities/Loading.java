package com.wodu.mobile.rpg_backpack.utilities;

import android.view.View;
import android.widget.ProgressBar;

public abstract class Loading {

    public static void showLoading(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    public static void hideLoading(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
    }
}
