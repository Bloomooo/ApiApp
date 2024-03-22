package com.api.projet.inter;

import com.api.projet.entity.Lobby;

import java.util.List;

public interface LobbyListObserver {
    public void onLobbyListUpdated(List<Lobby> lobbyList);
}
