package com.api.projet.inter;

import com.api.projet.entity.Anime;

import java.util.List;

public interface AnimeListObserver {

    public void onAnimeListUpdated(List<Anime> animeList);
}
