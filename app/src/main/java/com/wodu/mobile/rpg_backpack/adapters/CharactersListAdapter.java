package com.wodu.mobile.rpg_backpack.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.models.Character;

import java.util.List;

public class CharactersListAdapter extends RecyclerView.Adapter<CharactersListAdapter.ViewHolder> {

    private final String TAG = "CharactersListAdapter";

    private final Context context;
    private final List<Character> charactersList;
    private View view;

    public final MutableLiveData<Character> characterMutableLiveData = new MutableLiveData<>();

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
        StringBuilder name = new StringBuilder(charactersList.get(position).getName());
        Integer userID = charactersList.get(position).getUserID();
        Integer currentUserID = Application.getInstance().getUserID();

        TextView characterNameTextView = holder.getCharacterName();
        MaterialButton editButton = holder.getEditButton();
        characterNameTextView.setText(name);

        if (userID.equals(currentUserID)) {
            editButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final EditText edittext = new EditText(context);
                builder.setTitle("Set your character name");

                builder.setView(edittext);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        name.setLength(0);
                        name.append(edittext.getText().toString());
                        characterNameTextView.setText(name);

                        // Save character name
                        charactersList.get(holder.getAdapterPosition()).setName(name.toString());
                        characterMutableLiveData.postValue(charactersList.get(holder.getAdapterPosition()));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();
            });
        }
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
        private final MaterialButton editButton;

        public ViewHolder(View view) {
            super(view);
            characterCardView = view.findViewById(R.id.item_character_card_view);
            characterName = view.findViewById(R.id.item_character_name_text_view);
            kickButton = view.findViewById(R.id.item_character_kick_button);
            inspectButton = view.findViewById(R.id.item_character_inspect_button);
            editButton = view.findViewById(R.id.item_character_edit_button);
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

        public MaterialButton getEditButton() {
            return editButton;
        }
    }

    private void removeGameMasterFromList() {
        charactersList.remove(0);
    }
}
