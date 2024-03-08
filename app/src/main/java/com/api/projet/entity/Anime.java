package com.api.projet.entity;
public class Anime {

    private int id;

    private String titre;

    private String imageUri;

    private int score;

    private String status;

    public Anime(int id, String titre, String imageUri, int score, String status){
        this.id = id;
        this.titre = titre;
        this.imageUri = imageUri;
        this.score = score;
        this.status = status;
    }
    public int getId(){
        return id;
    }

    public String getTitre(){
        return titre;
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
    @Override
    public String toString(){
        return " id : "+ this.id + " titre : "+ this.titre +" imageUri : "+ this.imageUri +" score : "+ this.score +" status : "+ this.status;
    }
}
