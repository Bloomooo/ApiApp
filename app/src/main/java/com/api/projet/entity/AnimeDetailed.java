package com.api.projet.entity;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

/**
 * This class represents the details of an anime, including various information such as the ID, title,
 * start and end date, synopsis, average rating, rank, popularity, genres, pictures, image URI,
 * status, and number of episodes.
 */
public class AnimeDetailed {

    /**
     * The ID of the anime.
     */
    private int id;

    /**
     * The title of the anime.
     */
    private String title;

    /**
     * The Japanese title of the anime.
     */
    private String titleJp;

    /**
     * The start date of the anime.
     */
    private String startDate;

    /**
     * The end date of the anime.
     */
    private String endDate;

    /**
     * The synopsis of the anime.
     */
    private String synopsis;

    /**
     * The average rating of the anime.
     */
    private String noteMoy;

    /**
     * The rank of the anime.
     */
    private String rank;

    /**
     * The popularity of the anime.
     */
    private String popularity;

    /**
     * The list of genres of the anime.
     */
    private List<String> genres;

    /**
     * The list of pictures of the anime.
     */
    private List<String> pictures;

    /**
     * The image URI of the anime.
     */
    private String imageUri;

    /**
     * The status of the anime.
     */
    private String status;

    /**
     * The number of episodes of the anime.
     */
    private String ep;

    /**
     * Constructor for the AnimeDetailed class.
     *
     * @param id         The ID of the anime.
     * @param title      The title of the anime.
     * @param titleJp    The Japanese title of the anime.
     * @param startDate  The start date of the anime.
     * @param endDate    The end date of the anime.
     * @param synopsis   The synopsis of the anime.
     * @param noteMoy    The average rating of the anime.
     * @param rank       The rank of the anime.
     * @param popularity The popularity of the anime.
     * @param genres     The list of genres of the anime.
     * @param pictures   The list of pictures of the anime.
     * @param imageUri   The image URI of the anime.
     * @param status     The status of the anime.
     * @param ep         The number of episodes of the anime.
     */
    public AnimeDetailed(int id, String title, String titleJp, String startDate, String endDate, String synopsis,
                         String noteMoy, String rank, String popularity, List<String> genres, List<String> pictures,
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

    /**
     * Method to get the ID of the anime.
     *
     * @return The ID of the anime.
     */
    public int getId() {
        return id;
    }

    /**
     * Method to get the title of the anime.
     *
     * @return The title of the anime.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to get the Japanese title of the anime.
     *
     * @return The Japanese title of the anime.
     */
    public String getTitleJp() {
        return titleJp;
    }

    /**
     * Method to get the list of pictures of the anime.
     *
     * @return The list of pictures of the anime.
     */
    public List<String> getPictures() {
        return pictures;
    }

    /**
     * Method to get the start date of the anime.
     *
     * @return The start date of the anime.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Method to get the end date of the anime.
     *
     * @return The end date of the anime.
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Method to get the list of genres of the anime.
     *
     * @return The list of genres of the anime.
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * Method to get the image URI of the anime.
     *
     * @return The image URI of the anime.
     */
    public String getImageUri() {
        return imageUri;
    }

    /**
     * Method to get the average rating of the anime.
     *
     * @return The average rating of the anime.
     */
    public String getNoteMoy() {
        return noteMoy;
    }

    /**
     * Method to get the popularity of the anime.
     *
     * @return The popularity of the anime.
     */
    public String getPopularity() {
        return popularity;
    }

    /**
     * Method to get the rank of the anime.
     *
     * @return The rank of the anime.
     */
    public String getRank() {
        return rank;
    }

    /**
     * Method to get the status of the anime.
     *
     * @return The status of the anime.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Method to get the synopsis of the anime.
     *
     * @return The synopsis of the anime.
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Method to get the number of episodes of the anime.
     *
     * @return The number of episodes of the anime.
     */
    public String getEp() {
        return ep;
    }

    /**
     * Method to get a textual representation of the anime details.
     *
     * @return A textual representation of the anime details.
     */
    @Override
    public String toString() {
        StringBuilder genresStringBuilder = new StringBuilder();
        for (String genre : genres) {
            genresStringBuilder.append(genre).append(", ");
        }
        String genresString = genresStringBuilder.toString();
        if (!genresString.isEmpty()) {
            genresString = genresString.substring(0, genresString.length() - 2);
        }

        StringBuilder picturesStringBuilder = new StringBuilder();
        for (String picture : pictures) {
            picturesStringBuilder.append(picture).append(", ");
        }
        String picturesString = picturesStringBuilder.toString();
        if (!picturesString.isEmpty()) {
            picturesString = picturesString.substring(0, picturesString.length() - 2);
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


