package com.api.projet;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.backend.ConnexionAPI;
import com.api.projet.entity.Anime;
import com.api.projet.adapter.AdapterList;
import com.api.projet.inter.ListCallBackInterface;

import java.util.ArrayList;
import java.util.List;

public class ListMain extends AppCompatActivity implements ListCallBackInterface {
    private ConnexionAPI connexionAPI;
    private AdapterList adapterList;
    private List<Anime> animeList;

    private RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_recyclerview);
        init();
        testApiList();
    }

    private void init(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        animeList = new ArrayList<>();
        adapterList = new AdapterList(this, animeList);
        recyclerView.setAdapter(adapterList);
    }

    private void testApiList(){
        connexionAPI = ConnexionAPI.getInstance();
        connexionAPI.getListAnime(this);
    }

    @Override
    public void onSuccess(List<Anime> animeList) {
        if (animeList != null) {
            this.animeList.clear();
            this.animeList.addAll(animeList);
            adapterList.notifyDataSetChanged();
        } else {
            Log.e("ListMain", "La liste d'animes est null.");
        }

    }
}
