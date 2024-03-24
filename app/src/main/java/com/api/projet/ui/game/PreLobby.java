package com.api.projet.ui.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.api.projet.adapter.PreLobbyAdapter;
import com.api.projet.backend.DatabaseQuery;
import com.api.projet.entity.Player;
import com.api.projet.network.client.ClientSocket;

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
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_prelobby);

        initComponent();
        initListener();
        query();
    }



    private void initComponent(){
        this.db = DatabaseQuery.getInstance();
        this.playerList = new ArrayList<>();
        this.recyclerView = findViewById(R.id.recyclerViewPreLobby);
        this.buttonStart = findViewById(R.id.startButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.preLobbyAdapter = new PreLobbyAdapter(this, new ArrayList<>());
        this.recyclerView.setAdapter(preLobbyAdapter);
        Intent intent = getIntent();
        lobbyId = intent.getStringExtra("lobbyId");
    }

    private void query(){
        playerList.clear();
        db.getPlayer(lobbyId).addOnSuccessListener(playerList ->{
            this.playerList.addAll(playerList);
            updateData();
        }).addOnFailureListener(e ->{
            Log.e("Error getPlayer() ", e.getMessage());
        });
    }

    private void initListener(){
        this.buttonStart.setOnClickListener(v -> {
            //Intent intent = new Intent(PreLobby.this, Game.class);
            //startActivity(intent);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ClientSocket.on("lobbyMessage", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args.length > 0 && args[0] instanceof String) {
                    String message = (String) args[0];
                    Log.d("SocketEvent", "Received lobby message: " + message);
                    query();
                    updateData();
                }
            }
        });
    }

    private void updateData(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                preLobbyAdapter.setData(playerList);
            }
        });
    }
}
