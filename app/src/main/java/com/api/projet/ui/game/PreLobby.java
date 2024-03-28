package com.api.projet.ui.game;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.AnimeListData;
import com.api.projet.R;
import com.api.projet.adapter.PreLobbyAdapter;
import com.api.projet.backend.DatabaseQuery;
import com.api.projet.entity.Anime;
import com.api.projet.entity.Lobby;
import com.api.projet.entity.Player;
import com.api.projet.entity.User;
import com.api.projet.network.client.ClientSocket;
import com.api.projet.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;

public class PreLobby extends AppCompatActivity {

    private DatabaseQuery db;

    private RecyclerView recyclerView;

    private PreLobbyAdapter preLobbyAdapter;

    private List<Player> playerList;

    private String lobbyId;
    private Button buttonStart;

    private ImageButton buttonExit;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_prelobby);

        initComponent();

        queryPlayer();

        queryLobby();
    }

    private void initComponent(){
        this.db = DatabaseQuery.getInstance();
        this.playerList = new ArrayList<>();
        this.recyclerView = findViewById(R.id.recyclerViewPreLobby);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.preLobbyAdapter = new PreLobbyAdapter(this, new ArrayList<>());
        this.recyclerView.setAdapter(preLobbyAdapter);
        this.buttonExit = findViewById(R.id.backButton);
        Intent intent = getIntent();
        lobbyId = intent.getStringExtra("lobbyId");
    }

    private void queryPlayer(){
        playerList.clear();
        db.getPlayers(lobbyId).addOnSuccessListener(playerList ->{
            this.playerList.addAll(playerList);
            loadIcon();
        }).addOnFailureListener(e ->{
            Log.e("Error getPlayer() ", e.getMessage());
        });
    }

    private void queryLobby(){
        db.foundHostLobbyById(lobbyId).addOnSuccessListener(host->{
            for(Player player : playerList){
                if(host.getId().equals(player.getId()) && host.getName().equals(player.getName())){
                    this.buttonStart = findViewById(R.id.startButton);
                    this.buttonStart.setVisibility(View.VISIBLE);
                    initListener();
                }
            }
        });
    }

    private void initListener(){
        this.buttonStart.setOnClickListener(v -> {
            startGame();

        });
        this.buttonExit.setOnClickListener(v->{
            JSONObject data = new JSONObject();

            try {
                data.put("lobbyId", lobbyId);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            ClientSocket.emit("disconnectFromLobby",data);
            Intent intent = new Intent(PreLobby.this, HomeFragment.class);
            startActivity(intent);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ClientSocket.on("updatePlayersList", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                queryPlayer();
                updateData();
            }
        });
        ClientSocket.on("gameStarted", args->{
            if (args != null && args.length > 0) {
                JSONArray data = (JSONArray) args[0];
                List<String> suggestedAnimeList = new ArrayList<>();
                for(int i = 0; i< data.length(); i++){
                    try {
                        JSONObject jsonObject = data.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        suggestedAnimeList.add(title);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
                AnimeListData.setAnimeListLobby(suggestedAnimeList);
                Intent intent = new Intent(PreLobby.this, Game.class);
                intent.putExtra("lobbyId", lobbyId);
                startActivity(intent);
            }
        });
        ClientSocket.on("imageDownloadResponse", args->{
            new Handler(Looper.getMainLooper()).post(() -> {
                try{
                    JSONObject data = (JSONObject) args[0];
                    String name = data.getString("name");
                    String imageBase64 = data.getString("imageBase64");
                    byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
                    Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    boolean updated = false;
                    for (Player player : playerList){
                        if(player.getName().equals(name)){
                            player.setImageBase64(decodedBitmap);
                            updated = true;
                        }
                    }
                    if (updated) {
                        updateData();
                    }
                }catch (JSONException e){
                    Log.e("ERREUR JSON", e.getMessage());
                }
            });
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        ClientSocket.off("updatePlayersList");
    }

    private void loadIcon(){
        for(Player player: playerList){
            if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null){
                try{
                    JSONObject data = new JSONObject();
                    data.put("name", player.getName());
                    ClientSocket.emit("downloadImage", data);
                }catch (JSONException e){
                    Log.e("ERREUR JSON", e.getMessage());
                }
            }
        }

    }
    private void updateData(){


        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                preLobbyAdapter.setData(playerList);
            }
        });
    }

    private void startGame() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("lobbyId", lobbyId);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        ClientSocket.emit("startGame", jsonObject);
    }


}
