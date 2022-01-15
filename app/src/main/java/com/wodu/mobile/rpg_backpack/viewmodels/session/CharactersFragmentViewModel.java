package com.wodu.mobile.rpg_backpack.viewmodels.session;

import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.wodu.mobile.rpg_backpack.Application;
import com.wodu.mobile.rpg_backpack.models.Character;
import com.wodu.mobile.rpg_backpack.repositories.CharacterRepository;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Response;

public class CharactersFragmentViewModel extends ViewModel {

    private final String TAG = "CharactersFragmentViewModel";

    private final CharacterRepository characterRepository = CharacterRepository.getInstance();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<List<Character>> characterMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> characterUpdatedMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> characterKickedMutableLiveData = new MutableLiveData<>();

    public CharactersFragmentViewModel() {
    }

    public MutableLiveData<List<Character>> getSessionCharacters(Integer sessionID) {
        loadSessionCharacters(sessionID);
        return characterMutableLiveData;
    }

    public MutableLiveData<Boolean> updateCharacterLiveData(Integer characterID, String name, boolean isGameMaster, String image) {
        updateCharacter(characterID, name, isGameMaster, image);
        return characterUpdatedMutableLiveData;
    }

    public MutableLiveData<Boolean> kickCharacterLiveData(Integer characterID) {
        kickCharacter(characterID);
        return characterKickedMutableLiveData;
    }

    private void loadSessionCharacters(Integer sessionID) {
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

    private void updateCharacter(Integer characterID, String name, boolean isGameMaster, String image) {
        characterRepository.updateCharacter(characterID, name, isGameMaster, image).subscribe(new Observer<Response<JsonObject>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Response<JsonObject> jsonObjectResponse) {
                if (jsonObjectResponse.body().get("success").toString().trim().equals("true"))
                    characterUpdatedMutableLiveData.postValue(true);
                else
                    characterUpdatedMutableLiveData.postValue(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void kickCharacter(Integer characterID) {
        characterRepository.kickCharacter(characterID).subscribe(new Observer<Response<JsonObject>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Response<JsonObject> jsonObjectResponse) {
                if (jsonObjectResponse.body().get("success").toString().trim().equals("true"))
                    characterKickedMutableLiveData.postValue(true);
                else
                    characterKickedMutableLiveData.postValue(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {

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