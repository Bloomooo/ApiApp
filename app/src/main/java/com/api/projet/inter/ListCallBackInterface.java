package com.api.projet.inter;

import com.api.projet.entity.Anime;

import java.util.List;

/**
 * Interface définissant une méthode de rappel pour une liste d'objets Anime.
 */
public interface ListCallBackInterface {

    /**
     * Méthode invoquée en cas de succès avec une liste d'animes.
     * @param listAnime La liste d'animes récupérée avec succès.
     */
    void onSuccess(List<Anime> listAnime);
}
