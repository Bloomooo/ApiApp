package com.api.projet.ui.mylist;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.api.projet.AnimeListData;
import com.api.projet.entity.Anime;
import com.api.projet.inter.AnimeListObserver;

import java.util.ArrayList;
import java.util.List;

public class MyListAnimeViewModel extends ViewModel implements AnimeListObserver {
    private MutableLiveData<List<Anime>> animeList;
    private List<Anime> animeListBase;
    public MyListAnimeViewModel(){
        animeList = new MutableLiveData<>();
        animeListBase = new ArrayList<>();
        AnimeListData.addObserver(this);
        List<Anime> list = AnimeListData.getAnimeList();
        if(!list.isEmpty()){
            animeList.setValue(list);
            animeListBase = new ArrayList<>(list);
        }
    }



    public LiveData<List<Anime>> getAnimeList() {
        return animeList;
    }

    @Override
    public void onAnimeListUpdated(List<Anime> newAnimeList) {
        animeList.setValue(newAnimeList);
        animeListBase = new ArrayList<>(newAnimeList);
    }

    public void newFilterAnimeList(String filter){
        List<Anime> list = new ArrayList<>();
        for(Anime a : animeListBase){
            if(a.getTitle().toLowerCase().contains(filter.toLowerCase())){
                list.add(a);
            }
        }
        animeList.setValue(list);
    }
}