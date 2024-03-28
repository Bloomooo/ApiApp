package com.api.projet.inter;

import com.api.projet.entity.Anime;

import java.util.List;

/**
 * Interface pour observer les mises à jour de la liste d'animes.
 */
public interface AnimeListObserver {
    /**
     * Méthode appelée lorsqu'une mise à jour de la liste d'animes est effectuée.
     * @param animeList La liste mise à jour des animes.
     */
    void onAnimeListUpdated(List<Anime> animeList);
}
