package com.api.projet.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.api.projet.backend.DatabaseQuery;
import com.api.projet.entity.Lobby;
import com.api.projet.inter.AnimeListObserver;
import com.api.projet.inter.LobbyListObserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeViewModel extends ViewModel implements LobbyListObserver {

    private DatabaseQuery db;

    private MutableLiveData<List<Lobby>> listLobby;
    public HomeViewModel() {
        this.db = DatabaseQuery.getInstance();
        initComponent();
    }


    public LiveData<List<Lobby>> getLobby(){
        return listLobby;
    }

    private void initComponent(){
        this.listLobby = new MutableLiveData<>();
        this.listLobby.setValue(db.getLobby());
    }

    @Override
    public void onLobbyListUpdated(List<Lobby> newLobbyList){
        this.listLobby.setValue(newLobbyList);
    }
}