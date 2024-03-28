package com.api.projet.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.api.projet.backend.DatabaseQuery;
import com.api.projet.entity.Lobby;
import com.api.projet.inter.LobbyListObserver;

import java.util.List;

/**
 * ViewModel for managing the home screen data.
 */
public class HomeViewModel extends ViewModel implements LobbyListObserver {

    /** Instance of the DatabaseQuery class for database operations. */
    private DatabaseQuery db;

    /** LiveData object for storing the list of lobbies. */
    private MutableLiveData<List<Lobby>> listLobby;

    /**
     * Constructor for HomeViewModel.
     */
    public HomeViewModel() {
        this.db = DatabaseQuery.getInstance();
        initComponent();
        fetchLobbyData();
    }

    /**
     * Gets the LiveData object containing the list of lobbies.
     * @return LiveData object containing the list of lobbies.
     */
    public LiveData<List<Lobby>> getLobby(){
        return listLobby;
    }

    /**
     * Initializes the components of the ViewModel.
     */
    private void initComponent(){
        this.listLobby = new MutableLiveData<>();
    }

    /**
     * Fetches lobby data from the database and updates the LiveData object.
     */
    private void fetchLobbyData() {
        db.getLobby().addOnSuccessListener(lobbyList -> {
            listLobby.setValue(lobbyList);
        }).addOnFailureListener(e -> {
            Log.e("HomeViewModel", "Failed to fetch lobby data: " + e.getMessage());
        });
    }

    /**
     * Updates the list of lobbies when changes occur.
     * @param newLobbyList The updated list of lobbies.
     */
    @Override
    public void onLobbyListUpdated(List<Lobby> newLobbyList){
        this.listLobby.setValue(newLobbyList);
    }
 }
