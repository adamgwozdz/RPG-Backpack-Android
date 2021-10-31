package com.wodu.mobile.rpg_backpack.utilities;

import android.content.Context;
import android.content.Intent;

import com.wodu.mobile.rpg_backpack.views.CreateSessionActivity;
import com.wodu.mobile.rpg_backpack.views.JoinSessionActivity;
import com.wodu.mobile.rpg_backpack.views.LoginActivity;
import com.wodu.mobile.rpg_backpack.views.MainActivity;
import com.wodu.mobile.rpg_backpack.views.RegisterActivity;
import com.wodu.mobile.rpg_backpack.views.SessionActivity;

public abstract class Redirections {

    public static void redirectToLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void redirectToRegisterActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    public static void redirectToMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void redirectToJoinSessionActivity(Context context) {
        Intent intent = new Intent(context, JoinSessionActivity.class);
        context.startActivity(intent);
    }

    public static void redirectToCreateSessionActivity(Context context) {
        Intent intent = new Intent(context, CreateSessionActivity.class);
        context.startActivity(intent);
    }

    public static void redirectToSessionActivity(Context context, int sessionID, String sessionName, boolean isGameMaster) {
        Intent intent = new Intent(context, SessionActivity.class);
        intent.putExtra("sessionID", sessionID);
        intent.putExtra("sessionName", sessionName);
        intent.putExtra("isGameMaster", isGameMaster);
        context.startActivity(intent);
    }
}
