package com.api.projet.entity;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Player {
    private Long id;
    private String name;
    private int score;
    private String answer;
    private int color;

    private Bitmap imageBase64;

    public Player(Long id, String name) {
        this.id = id;
        this.name = name;
        this.score = 0;
        this.answer = "";
        this.color = Color.WHITE;
        this.imageBase64 = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Bitmap getImageBase64(){
        return this.imageBase64;
    }

    public void setImageBase64(Bitmap imageBase64){
        this.imageBase64 = imageBase64;
    }
}
