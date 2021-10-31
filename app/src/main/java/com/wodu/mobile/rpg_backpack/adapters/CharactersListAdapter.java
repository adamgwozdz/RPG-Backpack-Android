package com.wodu.mobile.rpg_backpack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Character;

import java.util.List;

public class CharactersListAdapter extends RecyclerView.Adapter<CharactersListAdapter.ViewHolder> {

    private final String TAG = "CharactersListAdapter";

    private Context context;
    private List<Character> charactersList;
    private View view;

    public CharactersListAdapter(Context context, List<Character> charactersList) {
        this.context = context;
        this.charactersList = charactersList;

        removeGameMasterFromList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_character, parent, false);
        return new CharactersListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = charactersList.get(position).getName();

        holder.characterName.setText(name);
    }

    @Override
    public int getItemCount() {
        return charactersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView characterCardView;
        private final TextView characterName;
        private final MaterialButton kickButton;
        private final MaterialButton inspectButton;

        public ViewHolder(View view) {
            super(view);
            characterCardView = view.findViewById(R.id.item_character_card_view);
            characterName = view.findViewById(R.id.item_character_name_text_view);
            kickButton = view.findViewById(R.id.item_character_kick_button);
            inspectButton = view.findViewById(R.id.item_character_inspect_button);
        }

        public CardView getCharacterCardView() {
            return characterCardView;
        }

        public TextView getCharacterName() {
            return characterName;
        }

        public MaterialButton getKickButton() {
            return kickButton;
        }

        public MaterialButton getInspectButton() {
            return inspectButton;
        }
    }

    private void removeGameMasterFromList() {
        charactersList.remove(0);
    }
}
