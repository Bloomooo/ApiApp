package com.api.projet.model;

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
import com.api.projet.inter.AnimeListObserver;
import com.api.projet.inter.ListCallBackInterface;

import java.util.ArrayList;
import java.util.List;

public class AnimeListData {
    private static List<Anime> animeList = new ArrayList<>();
    private static List<AnimeListObserver> observers = new ArrayList<>();

    private static List<String> animeListLobby = new ArrayList<>();

    public static void addObserver(AnimeListObserver observer) {
        observers.add(observer);
    }

    public static void removeObserver(AnimeListObserver observer) {
        observers.remove(observer);
    }

    public static void updateAnimeList(List<Anime> newAnimeList) {
        animeList = new ArrayList<>();
        animeList.addAll(newAnimeList);
        notifyObservers();
    }

    private static void notifyObservers() {
        for (AnimeListObserver observer : observers) {
            observer.onAnimeListUpdated(animeList);
        }
    }

    public static List<Anime> getAnimeList() {
        return animeList;
    }

    public static List<String> getAnimeListLobby(){
        return animeListLobby;
    }

    public static void setAnimeListLobby(List<String> listLobby){
        if(!animeListLobby.isEmpty()){
            animeListLobby.clear();
        }
        animeListLobby.addAll(listLobby);
    }
}
