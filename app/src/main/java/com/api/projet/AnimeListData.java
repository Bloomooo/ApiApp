package com.api.projet;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.backend.ConnexionAPI;
import com.api.projet.entity.Anime;
import com.api.projet.adapter.AdapterList;
import com.api.projet.inter.ListCallBackInterface;

import java.util.ArrayList;
import java.util.List;

public class AnimeListData{
    private static MutableLiveData<List<Anime>> animeList;

    public static LiveData<List<Anime>> getAnimeList() {
        if(animeList == null){
            animeList = new MutableLiveData<>();
            animeList.setValue(new ArrayList<>());
        }
        return animeList;
    }

    public static void updateAnimeList(List<Anime> animeList) {
        if(AnimeListData.animeList == null){
            AnimeListData.animeList = new MutableLiveData<>();
            AnimeListData.animeList.setValue(new ArrayList<>());
        }
        AnimeListData.animeList.getValue().clear();
        AnimeListData.animeList.getValue().addAll(animeList);
    }
}
