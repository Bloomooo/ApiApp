package com.api.projet.ui.mylist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.api.projet.AnimeListData;
import com.api.projet.entity.Anime;
import com.api.projet.inter.AnimeListObserver;

import java.util.List;

public class MyListAnimeViewModel extends ViewModel implements AnimeListObserver {
    private MutableLiveData<List<Anime>> animeList;
    public MyListAnimeViewModel(){
        animeList = new MutableLiveData<>();
        animeList.setValue(AnimeListData.getAnimeList());
        AnimeListData.addObserver(this);
    }



    public LiveData<List<Anime>> getAnimeList() {
        return animeList;
    }

    @Override
    public void onAnimeListUpdated(List<Anime> newAnimeList) {
        animeList.setValue(newAnimeList);
    }
}