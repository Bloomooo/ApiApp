package com.api.projet.inter;

/**
 * Interface permettant de gérer les intents.
 */
public interface IntentInterface {

    /**
     * Méthode pour effectuer un intent avec l'identifiant du lobby.
     * @param lobbyId L'identifiant du lobby pour l'intent.
     */
    void doIntent(String lobbyId);
}
