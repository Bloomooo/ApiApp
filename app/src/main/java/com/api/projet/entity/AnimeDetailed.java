package com.api.projet.entity;

import java.util.List;

import java.util.List;

public class AnimeDetailed {
    private int id;
    private String title;
    private String titleEn;
    private String titleJp;
    private String startDate;
    private String endDate;
    private String synopsis;
    private String noteMoy;
    private String rank;
    private String popularity;
    private String genre;
    private List<String> pictures;
    private String imageUri;
    private int score;
    private String status;
    private int ep;

    public AnimeDetailed(int id, String title, String titleEn, String titleJp,
                         String startDate, String endDate, String synopsis, String noteMoy, String rank,
                         String popularity, String genre, List<String> pictures,
                         String imageUri, int score, String status, int ep) {
        this.id = id;
        this.title = title;
        this.titleEn = titleEn;
        this.titleJp = titleJp;
        this.startDate = startDate;
        this.endDate = endDate;
        this.synopsis = synopsis;
        this.noteMoy = noteMoy;
        this.rank = rank;
        this.popularity = popularity;
        this.genre = genre;
        this.pictures = pictures;
        this.imageUri = imageUri;
        this.score = score;
        this.status = status;
        this.ep = ep;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getEp() {
        return ep;
    }

    public int getScore() {
        return score;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getGenre() {
        return genre;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getNoteMoy() {
        return noteMoy;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getRank() {
        return rank;
    }

    public String getStatus() {
        return status;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public String getTitleJp() {
        return titleJp;
    }
}

