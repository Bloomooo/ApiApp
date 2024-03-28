package com.api.projet.inter;

import com.api.projet.entity.Lobby;

import java.util.List;

/**
 * Interface définissant une méthode de rappel pour une liste de lobbies.
 */
public interface LobbyListObserver {

    /**
     * Méthode invoquée lorsqu'une liste de lobbies est mise à jour.
     * @param lobbyList La liste mise à jour des lobbies.
     */
    void onLobbyListUpdated(List<Lobby> lobbyList);
}
