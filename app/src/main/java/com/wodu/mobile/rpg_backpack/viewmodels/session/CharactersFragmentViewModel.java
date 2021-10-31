package com.wodu.mobile.rpg_backpack.viewmodels.session;

import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.repositories.CharacterRepository;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class CharactersFragmentViewModel extends ViewModel {

    private final String TAG = "UsersFragmentViewModel";

    private final CharacterRepository characterRepository = CharacterRepository.getInstance();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<List<Character>> characterMutableLiveData = new MutableLiveData<>();

    public CharactersFragmentViewModel() {
    }

    public MutableLiveData<List<Character>> getSessionCharacters(Integer sessionID) {
        loadSessionCharacters(sessionID);
        return characterMutableLiveData;
    }

    public void loadSessionCharacters(Integer sessionID) {
        characterRepository.getSessionCharacters(sessionID).subscribe(new Observer<List<Character>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull List<Character> characters) {
                characterMutableLiveData.postValue(characters);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}