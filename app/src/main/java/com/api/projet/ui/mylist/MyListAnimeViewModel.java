package com.api.projet.ui.mylist;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.AnimeListData;
import com.api.projet.R;
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

    public void itemAction(@NonNull RecyclerView rv, @NonNull MotionEvent e){
        int position = rv.getChildAdapterPosition(rv.findChildViewUnder(e.getX(), e.getY()));
        Anime animeClicked = animeList.getValue().get(position);
        Log.i("Anime :", animeClicked.toString());
        Bundle bundle = new Bundle();
        bundle.putInt("animeId", animeClicked.getId());
        NavController navController = Navigation.findNavController(rv);
        navController.navigate(R.id.nav_anime_details, bundle);

    }

}