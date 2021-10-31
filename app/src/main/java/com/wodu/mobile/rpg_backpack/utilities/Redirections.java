package com.wodu.mobile.rpg_backpack.utilities;

import android.content.Context;
import android.content.Intent;

import com.wodu.mobile.rpg_backpack.views.SessionActivity;

public abstract class Redirections {

    public static void redirectToSessionActivity(Context context, int sessionID, String sessionName, boolean isGameMaster) {
        Intent intent = new Intent(context, SessionActivity.class);
        intent.putExtra("sessionID", sessionID);
        intent.putExtra("sessionName", sessionName);
        intent.putExtra("isGameMaster", isGameMaster);
        context.startActivity(intent);
    }
}
