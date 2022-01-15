package com.wodu.mobile.rpg_backpack.views.session;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.adapters.CharactersListAdapter;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.utilities.Loading;
import com.wodu.mobile.rpg_backpack.viewmodels.session.CharactersFragmentViewModel;

import java.util.List;

public class CharactersFragment extends Fragment {

    private CharactersFragmentViewModel viewModel;
    private ProgressBar loadingProgressBar;

    private List<Character> charactersList;
    private Integer sessionID;
    private String sessionName;

    public static CharactersFragment newInstance() {
        return new CharactersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_characters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CharactersFragmentViewModel.class);

        loadingProgressBar = view.findViewById(R.id.fragment_characters_loading_spinner);

        getIntentData();
        sendGetCharactersRequest(view);
    }

    private void getIntentData() {
        sessionID = getActivity().getIntent().getIntExtra("sessionID", 0);
    }

    private void sendGetCharactersRequest(View view) {
        Loading.showLoading(loadingProgressBar);
        viewModel.getSessionCharacters(sessionID).observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<List<Character>>() {

            @Override
            public void onChanged(List<Character> characters) {
                Loading.hideLoading(loadingProgressBar);
                charactersList = characters;
                setupCharactersList(view);
            }
        });
        Loading.showLoading(loadingProgressBar);
    }

    private void setupCharactersList(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.fragment_characters_body_sessions_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        CharactersListAdapter adapter = new CharactersListAdapter(view.getContext(), charactersList);
        recyclerView.setAdapter(adapter);

        observeCharacterName(adapter);
        observeKickingCharacter(adapter);
    }

    private void observeCharacterName(CharactersListAdapter adapter) {
        adapter.characterMutableLiveData.observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<Character>() {
            @Override
            public void onChanged(Character character) {
                viewModel.updateCharacterLiveData(character.getCharacterID(), character.getName(), character.getGameMaster(), character.getImage())
                        .observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean aBoolean) {

                            }
                        });
            }
        });
    }

    private void observeKickingCharacter(CharactersListAdapter adapter) {
        adapter.kickedCharacterLiveData.observe(getViewLifecycleOwner(), new Observer<Character>() {
            @Override
            public void onChanged(Character character) {
                viewModel.kickCharacterLiveData(character.getCharacterID()).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {

                    }
                });
            }
        });
    }
}