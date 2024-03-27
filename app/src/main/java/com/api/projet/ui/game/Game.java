package com.api.projet.ui.game;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.AnimeListData;
import com.api.projet.R;
import com.api.projet.adapter.AnimeSuggestionAdapter;
import com.api.projet.adapter.GameAdapter;
import com.api.projet.adapter.PreLobbyAdapter;
import com.api.projet.backend.DatabaseQuery;
import com.api.projet.entity.Anime;
import com.api.projet.entity.Player;
import com.api.projet.inter.GameInteraction;
import com.api.projet.network.client.ClientSocket;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity implements GameInteraction {

    private TextView compteurTextView;

    private TextView answerQuizTextView;

    private TextView timerTextView;


    private ImageView animeImageView;

    private EditText answerEditText;

    private CountDownTimer timer;

    private DatabaseQuery db;

    private long timerSeconds = 20;

    private List<Player> playerList;

    private RecyclerView recyclerViewPlayer;
    private RecyclerView recyclerViewSuggestion;

    private GameAdapter gameAdapter;

    private AnimeSuggestionAdapter animeSuggestionAdapter;

    private String lobbyId;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_game);
        initComponent();
        loadPlayer();
    }
    @Override
    public void onStart(){
        super.onStart();
        searchAnswer();
        ClientSocket.on("sendAnime", args -> {
            try {
                JSONObject jsonObject = (JSONObject) args[0];
                String title = jsonObject.getString("title");
                String imageUriString = jsonObject.getString("image");
                String length = jsonObject.getString("length");
                String index = jsonObject.getString("index");
                Log.i("GGEZEZEZAEAEORZAOKF2AOKF",title);
                runOnUiThread(() -> {
                    compteurTextView.setText(index+"/"+length);
                    Picasso.get().load(imageUriString).into(animeImageView);
                    for(Player player: playerList){
                        player.setColor(Color.WHITE);
                        player.setAnswer("");
                    }
                    gameAdapter.setData(playerList);
                    answerQuizTextView.setText("?");
                    answerEditText.setEnabled(true);
                    timerSeconds = 20;

                    timer = new CountDownTimer(20000, 1000) {
                        public void onTick(long millisUntilFinished) {

                            timerSeconds--;
                            timerTextView.setText("Timer: " + timerSeconds);
                        }

                        public void onFinish() {
                            answerQuizTextView.setText(title);

                            JSONObject data = new JSONObject();
                            try {
                                data.put("lobbyId", lobbyId);
                                data.put("username", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                                data.put("answer", answerEditText.getText().toString());
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            ClientSocket.emit("sendResults", data);

                            answerEditText.setText("");
                            answerEditText.setEnabled(false);

                            if(index == length){
                                answerQuizTextView.setText(answerQuizTextView.getText().toString() + "\nFin de la partie");
                               new CountDownTimer(10000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        Intent intent = new Intent(Game.this, PreLobby.class);
                                        intent.putExtra("lobbyId", lobbyId);
                                        startActivity(intent);
                                    }
                                }.start();

                            }
                        }
                    }.start();
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        ClientSocket.on("gameResults", args -> {
            JSONArray jsonArray = (JSONArray) args[0];

            for(int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject data = jsonArray.getJSONObject(i);
                    String username = data.getString("username");
                    String answer = data.getString("answer");
                    boolean isCorrect = data.getBoolean("isCorrect");
                    for (Player player : playerList) {

                        if (player.getName().equals(username)) {
                            player.setAnswer(answer);
                            if(isCorrect){
                                player.setColor(Color.GREEN);
                                player.setScore(player.getScore() + 1);
                            }else{
                                player.setColor(Color.RED);
                            }


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            runOnUiThread(() -> {
                gameAdapter.setData(playerList);
            });

        });


    }

    private void updateData(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                gameAdapter.setData(playerList);
            }
        });
    }
    private void initComponent(){
        this.db = DatabaseQuery.getInstance();
        TextInputLayout tilAnswer = findViewById(R.id.tilAnswer);
        this.answerEditText = tilAnswer.getEditText();
        this.animeImageView = findViewById(R.id.ivAnimeImage);
        this.compteurTextView = findViewById(R.id.tvTotalAnimeCount);
        this.answerQuizTextView = findViewById(R.id.tvQuizAnswer);
        this.timerTextView = findViewById(R.id.tvTimer);
        this.playerList = new ArrayList<>();
        this.recyclerViewPlayer = findViewById(R.id.rvPlayers);
        this.recyclerViewPlayer.setLayoutManager(new LinearLayoutManager(this));
        this.gameAdapter = new GameAdapter(this, new ArrayList<>());
        this.recyclerViewPlayer.setAdapter(gameAdapter);

        this.recyclerViewSuggestion = findViewById(R.id.rvSuggestions);
        recyclerViewSuggestion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.animeSuggestionAdapter = new AnimeSuggestionAdapter(this, new ArrayList<>(), this);
        this.recyclerViewSuggestion.setAdapter(animeSuggestionAdapter);
    }

    private void searchAnswer(){
        if (this.answerEditText != null) {
            this.answerEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    answerSuggestion(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else {
            Log.e("Game", "answerEditText is null. Check if TextInputLayout's child is correctly referenced.");
        }
    }

    private void answerSuggestion(String query){
        if(!query.isEmpty()){
            List<String> answerSuggestionList = new ArrayList<>();
            for(String anime : AnimeListData.getAnimeListLobby()){
                if (anime.toLowerCase().contains(query.toLowerCase())){
                    answerSuggestionList.add(anime);
                }
            }
            animeSuggestionAdapter.setData(answerSuggestionList);
        }else{
            animeSuggestionAdapter.setData(new ArrayList<>());
        }

    }

    private void loadPlayer(){
        Intent intent = getIntent();
        lobbyId = intent.getStringExtra("lobbyId");
        db.getPlayers(lobbyId).addOnSuccessListener(playerList ->{
            this.playerList.clear();
            this.playerList.addAll(playerList);
            updateData();
            gameAdapter.setData(this.playerList);
        }).addOnFailureListener(e ->{
            Log.e("Error getPlayer() ", e.getMessage());
        });
    }

    @Override
    public void setAnswer(String answer) {
        this.answerEditText.setText(answer);
    }
}