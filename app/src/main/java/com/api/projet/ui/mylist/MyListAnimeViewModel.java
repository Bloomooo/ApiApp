package com.api.projet.ui.mylist;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.AnimeDetailActivity;
import com.api.projet.model.AnimeListData;
import com.api.projet.entity.Anime;
import com.api.projet.inter.AnimeListObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel class for managing the data of the MyListAnimeFragment.
 */
public class MyListAnimeViewModel extends ViewModel implements AnimeListObserver {
    /** LiveData object representing the list of anime. */
    private MutableLiveData<List<Anime>> animeList;

    /** Base list of anime. */
    private List<Anime> animeListBase;

    /**
     * Constructor for MyListAnimeViewModel.
     * Initializes the LiveData and base list of anime.
     */
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

    /**
     * Retrieves the LiveData object representing the list of anime.
     * @return LiveData object representing the list of anime.
     */
    public LiveData<List<Anime>> getAnimeList() {
        return animeList;
    }

    /**
     * Method called when the list of anime is updated.
     * @param newAnimeList The updated list of anime.
     */
    @Override
    public void onAnimeListUpdated(List<Anime> newAnimeList) {
        animeList.setValue(newAnimeList);
        animeListBase = new ArrayList<>(newAnimeList);
    }

    /**
     * Filters the anime list based on the provided filter.
     * @param filter The filter string.
     */
    public void newFilterAnimeList(String filter){
        List<Anime> list = new ArrayList<>();
        for(Anime a : animeListBase){
            if(a.getTitle().toLowerCase().contains(filter.toLowerCase())){
                list.add(a);
            }
        }
        animeList.setValue(list);
    }

    /**
     * Handles the action to be performed when an item in the RecyclerView is clicked.
     * @param rv The RecyclerView instance.
     * @param e The MotionEvent object representing the touch event.
     */
    public void itemAction(@NonNull RecyclerView rv, @NonNull MotionEvent e){
        int position = rv.getChildAdapterPosition(rv.findChildViewUnder(e.getX(), e.getY()));
        Anime animeClicked = animeList.getValue().get(position);

        Intent intent = new Intent(rv.getContext(), AnimeDetailActivity.class);
        intent.putExtra("id",animeClicked.getId());

        rv.getContext().startActivity(intent);
    }
}
