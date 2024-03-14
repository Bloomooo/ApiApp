package com.api.projet.entity;
public class Anime {

    private int id;

    private String title;

    private String imageUri;

    private int score;

    private String status;

    private int epWatch;

    public Anime(int id, String title, String imageUri, int score, String status, int epWatch){
        this.id = id;
        this.title = title;
        this.imageUri = imageUri;
        this.score = score;
        this.status = status;
        this.epWatch = epWatch;
    }
    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getImageUri(){
        return imageUri;
    }

    public int getScore(){
        return score;
    }

    public String getStatus(){
        return status;
    }

    public int getEpWatch(){
        return epWatch;
    }
    @Override
    public String toString(){
        return " id : "+ this.id + " titre : "+ this.title +" imageUri : "+ this.imageUri +" score : "+ this.score +" status : "+ this.status;
    }
}
