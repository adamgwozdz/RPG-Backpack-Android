package com.wodu.mobile.rpg_backpack.views.session;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wodu.mobile.rpg_backpack.R;
import com.wodu.mobile.rpg_backpack.viewmodels.session.UsersFragmentViewModel;

public class UsersFragment extends Fragment {

    private UsersFragmentViewModel mViewModel;

    public static UsersFragment newInstance() {
        return new UsersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView sessionIdTextView = view.findViewById(R.id.fragment_users_session_id);
        int sessionID = getActivity().getIntent().getIntExtra("sessionID", 0);
        boolean isGameMaster = getActivity().getIntent().getBooleanExtra("isGameMaster", false);
        sessionIdTextView.setText(sessionID + " " + isGameMaster);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UsersFragmentViewModel.class);
        // TODO: Use the ViewModel
    }

}