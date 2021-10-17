package com.wodu.mobile.rpg_backpack.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Session;
import com.wodu.mobile.rpg_backpack.utilities.Utilities;

import java.util.List;

public class SessionsListAdapter extends RecyclerView.Adapter<SessionsListAdapter.ViewHolder> {

    private final String TAG = "SessionsListAdapter";

    private List<Session> sessionList;
    private View view;

    public SessionsListAdapter(List<Session> sessionList) {
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
        TextView sessionModificationTime = holder.getModificationTime();
        TextView sessionPlayersCountTextView = holder.getPlayerCount();
        MaterialCardView sessionCardView = holder.getSessionCardView();

        setupSessionName(sessionNameTextView, position);
        setupPlayerCounter(sessionPlayersCountTextView, position);
        setupModificationTime(sessionModificationTime, position);
        setupSessionOwnerIndicator(sessionCardView, position);
    }

    private void setupSessionName(TextView sessionNameTextView, int position) {
        String sessionName = sessionList.get(position).getName();
        sessionNameTextView.setText(sessionName);
    }

    private void setupPlayerCounter(TextView sessionPlayersCountTextView, int position) {
        String playerCount = String.valueOf(sessionList.get(position).getCharacters().size());
        sessionPlayersCountTextView.setText(playerCount);
    }

    private void setupModificationTime(TextView sessionModificationTime, int position) {
        String lastModified;
        if (sessionList.get(position).getDateModified() == null)
            lastModified = "Last modified on " + Utilities.userFriendlyTimestamp(sessionList.get(position).getDateCreated());
        else
            lastModified = "Last modified on " + Utilities.userFriendlyTimestamp(sessionList.get(position).getDateModified());
        sessionModificationTime.setText(lastModified);
    }

    // Creates green border around sessions owned by currently logged in user
    private void setupSessionOwnerIndicator(MaterialCardView sessionCardView, int position) {
        int userID = Application.getInstance().getUserID();
        int characterUserId = sessionList.get(position).getCharacters().get(0).getUserID();
        boolean isGameMaster = sessionList.get(position).getCharacters().get(0).getGameMaster();
        if (userID == characterUserId && isGameMaster)
            sessionCardView.setStrokeColor(view.getResources().getColor(R.color.color_image_border));
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
            modificationTime = view.findViewById(R.id.item_session_modification_time_text_view);
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
