package com.api.projet.entity;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class AnimeDetailed {
    private int id;
    private String title;
    private String titleJp;
    private String startDate;
    private String endDate;
    private String synopsis;
    private String noteMoy;
    private String rank;
    private String popularity;
    private List<String> genres;
    private List<String> pictures;
    private String imageUri;
    private String status;
    private String ep;

    public AnimeDetailed(int id, String title, String titleJp,
                         String startDate, String endDate, String synopsis, String noteMoy, String rank,
                         String popularity, List<String> genres, List<String> pictures,
                         String imageUri, String status, String ep) {
        this.id = id;
        this.title = title;
        this.titleJp = titleJp;
        this.startDate = startDate;
        this.endDate = endDate;
        this.synopsis = synopsis;
        this.noteMoy = noteMoy;
        this.rank = rank;
        this.popularity = popularity;
        this.genres = new ArrayList<>(genres);
        this.pictures = new ArrayList<>(pictures);
        this.imageUri = imageUri;
        this.status = status;
        this.ep = ep;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getEp() {
        return ep;
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

    public List<String> getGenres() {
        return genres;
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


    public String getTitleJp() {
        return titleJp;
    }

    @Override
    public String toString() {
        StringBuilder genresStringBuilder = new StringBuilder();
        for (String genre : genres) {
            genresStringBuilder.append(genre).append(", ");
        }
        String genresString = genresStringBuilder.toString();
        if (!genresString.isEmpty()) {
            genresString = genresString.substring(0, genresString.length() - 2); // Supprime la virgule et l'espace à la fin
        }

        StringBuilder picturesStringBuilder = new StringBuilder();
        for (String picture : pictures) {
            picturesStringBuilder.append(picture).append(", ");
        }
        String picturesString = picturesStringBuilder.toString();
        if (!picturesString.isEmpty()) {
            picturesString = picturesString.substring(0, picturesString.length() - 2); // Supprime la virgule et l'espace à la fin
        }

        return "AnimeDetailed{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", titleJp='" + titleJp + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", noteMoy='" + noteMoy + '\'' +
                ", rank='" + rank + '\'' +
                ", popularity='" + popularity + '\'' +
                ", genres=" + genresString +
                ", pictures=" + picturesString +
                ", imageUri='" + imageUri + '\'' +
                ", status='" + status + '\'' +
                ", ep='" + ep + '\'' +
                '}';
    }
}

