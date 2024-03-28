package com.api.projet.inter;

import com.api.projet.entity.AnimeDetailed;

import java.util.List;

/**
 * Interface pour la gestion des rappels (callbacks) des requêtes Anime.
 */
public interface AnimeCallBackInterface {
    /**
     * Méthode appelée lorsque la requête Anime est réussie.
     * @param anime L'objet Anime détaillé retourné par la requête.
     */
    void onSuccess(AnimeDetailed anime);
}
