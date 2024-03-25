package com.api.projet.ui.game;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.api.projet.R;
import com.api.projet.network.client.ClientSocket;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class Game extends AppCompatActivity {

    private TextView compteurTextView;

    private TextView answerQuizTextView;

    private ImageView animeImageView;

    private EditText answerEditText;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_game);
        initComponent();
    }
    @Override
    public void onStart(){
        super.onStart();
        ClientSocket.on("gameData", args -> {
            try {
                JSONObject jsonObject = (JSONObject) args[0];
                String quizAnswer = jsonObject.getString("quizAnswer");
                String imageUriString = jsonObject.getString("imageUri");
                
                runOnUiThread(() -> {
                    answerQuizTextView.setText(quizAnswer);
                    if (isValidUri(imageUriString)) {
                        Uri imageUri = Uri.parse(imageUriString);
                        animeImageView.setImageURI(imageUri);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean isValidUri(String uriString) {
        if (uriString == null || uriString.isEmpty()) {
            return false;
        }
        try {
            Uri.parse(uriString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void initComponent(){
        TextInputLayout tilAnswer = findViewById(R.id.tilAnswer);
        this.answerEditText = tilAnswer.getEditText();
        this.animeImageView = findViewById(R.id.ivAnimeImage);
        this.compteurTextView = findViewById(R.id.tvTotalAnimeCount);
        this.answerQuizTextView = findViewById(R.id.tvQuizAnswer);
    }



}
