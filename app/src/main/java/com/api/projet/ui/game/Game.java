package com.api.projet.ui.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.api.projet.R;
import com.api.projet.network.client.ClientSocket;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class Game extends AppCompatActivity {

    private TextView compteurTextView;

    private TextView answerQuizTextView;

    private TextView timerTextView;

    private ImageView animeImageView;

    private EditText answerEditText;

    private CountDownTimer timer;

    private long timerSeconds = 20;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_game);
        initComponent();
    }
    @Override
    public void onStart(){
        super.onStart();
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
                    answerQuizTextView.setText("?");
                    answerEditText.setEnabled(true);
                    timerSeconds = 20;
                    if (timer != null) {
                        timer.cancel();
                    }
                    timer = new CountDownTimer(20000, 1000) {
                        public void onTick(long millisUntilFinished) {

                            timerSeconds--;
                            timerTextView.setText("Timer: " + timerSeconds);
                        }

                        public void onFinish() {
                            answerQuizTextView.setText(title);
                            answerEditText.setEnabled(false);
                        }
                    }.start();
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }



    private void initComponent(){
        TextInputLayout tilAnswer = findViewById(R.id.tilAnswer);
        this.answerEditText = tilAnswer.getEditText();
        this.animeImageView = findViewById(R.id.ivAnimeImage);
        this.compteurTextView = findViewById(R.id.tvTotalAnimeCount);
        this.answerQuizTextView = findViewById(R.id.tvQuizAnswer);
        this.timerTextView = findViewById(R.id.tvTimer);
    }





}
