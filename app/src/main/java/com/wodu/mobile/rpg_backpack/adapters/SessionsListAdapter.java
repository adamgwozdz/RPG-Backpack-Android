package com.wodu.mobile.rpg_backpack.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.views.SessionActivity;

import java.util.List;

public class SessionsListAdapter extends RecyclerView.Adapter<SessionsListAdapter.ViewHolder> {

    private final String TAG = "SessionsListAdapter";

    private Context context;
    private List<Session> sessionList;
    private View view;

    public SessionsListAdapter(Context context, List<Session> sessionList) {
        this.context = context;
        this.sessionList = sessionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_session, parent, false);
        return new SessionsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView sessionNameTextView = holder.getSessionName();
        TextView sessionIdTextView = holder.getModificationTime();
        TextView sessionPlayersCountTextView = holder.getPlayerCount();
        MaterialCardView sessionCardView = holder.getSessionCardView();

        Session session = sessionList.get(position);
        Character character = getCurrentUsersCharacter(session);

        int sessionID = session.getSessionID();
        boolean isGameMaster = character.getGameMaster();

        setupSessionName(sessionNameTextView, position);
        setupPlayerCounter(sessionPlayersCountTextView, position);
        setupSessionID(sessionIdTextView, sessionID);
        setupSessionOwnerIndicator(sessionCardView, position);
        setupSessionButtons(sessionCardView, sessionID, isGameMaster);
    }

    private void setupSessionName(TextView sessionNameTextView, int position) {
        String sessionName = sessionList.get(position).getName();
        sessionNameTextView.setText(sessionName);
    }

    private void setupPlayerCounter(TextView sessionPlayersCountTextView, int position) {
        String playerCount = String.valueOf(sessionList.get(position).getCharacters().size());
        sessionPlayersCountTextView.setText(playerCount);
    }

    private void setupSessionID(TextView sessionIdTextView, int sessionID) {
        String sessionIdString = "Session ID: " + sessionID;
        sessionIdTextView.setText(sessionIdString);
    }

    // Creates green border around sessions owned by currently logged in user
    private void setupSessionOwnerIndicator(MaterialCardView sessionCardView, int position) {
        int userID = Application.getInstance().getUserID();
        int characterUserId = sessionList.get(position).getCharacters().get(0).getUserID();
        boolean isGameMaster = sessionList.get(position).getCharacters().get(0).getGameMaster();
        if (userID == characterUserId && isGameMaster)
            sessionCardView.setStrokeColor(view.getResources().getColor(R.color.color_image_border));
    }

    private void setupSessionButtons(MaterialCardView sessionCardView, int sessionID, boolean isGameMaster) {
        sessionCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToSessionActivity(sessionID, isGameMaster);
            }
        });
    }

    private void redirectToSessionActivity(int sessionID, boolean isGameMaster) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(context, SessionActivity.class);
        intent.putExtra("sessionID", sessionID);
        intent.putExtra("isGameMaster", isGameMaster);
        context.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    private Character getCurrentUsersCharacter(Session session) {
        Integer userID = Application.getInstance().getUserID();
        Character character = new Character();
        for (int i = 0; i < session.getCharacters().size(); i++) {
            if (session.getCharacters().get(i).getUserID().equals(userID)) {
                character = session.getCharacters().get(i);
                break;
            }
        }
        return character;
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView sessionName;
        private final TextView modificationTime;
        private final TextView playerCount;
        private final MaterialCardView sessionCardView;

        public ViewHolder(View view) {
            super(view);
            sessionName = view.findViewById(R.id.item_session_title_text_view);
            modificationTime = view.findViewById(R.id.item_session_session_id);
            playerCount = view.findViewById(R.id.item_session_player_count_text_view);
            sessionCardView = view.findViewById(R.id.item_session_card_view);
        }

        public TextView getSessionName() {
            return sessionName;
        }

        public TextView getModificationTime() {
            return modificationTime;
        }

        public TextView getPlayerCount() {
            return playerCount;
        }

        public MaterialCardView getSessionCardView() {
            return sessionCardView;
        }
    }
}
