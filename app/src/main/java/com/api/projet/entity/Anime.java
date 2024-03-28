package com.api.projet.entity;

/**
 * La classe Anime représente un objet Anime avec ses attributs.
 */
public class Anime {

    /** Identifiant de l'anime */
    private int id;
    /** Titre de l'anime */
    private String title;
    /** URI de l'image de l'anime */
    private String imageUri;
    /** Score de l'anime */
    private int score;
    /** Statut de l'anime */
    private String status;
    /** Nombre d'épisodes regardés de l'anime */
    private int epWatch;

    /**
     * Constructeur de la classe Anime.
     * @param id Identifiant de l'anime.
     * @param title Titre de l'anime.
     * @param imageUri URI de l'image de l'anime.
     * @param score Score de l'anime.
     * @param status Statut de l'anime.
     * @param epWatch Nombre d'épisodes regardés de l'anime.
     */
    public Anime(int id, String title, String imageUri, int score, String status, int epWatch){
        this.id = id;
        this.title = title;
        this.imageUri = imageUri;
        this.score = score;
        this.status = status;
        this.epWatch = epWatch;
    }

    /**
     * Retourne l'identifiant de l'anime.
     * @return L'identifiant de l'anime.
     */
    public int getId(){
        return id;
    }

    /**
     * Retourne le titre de l'anime.
     * @return Le titre de l'anime.
     */
    public String getTitle(){
        return title;
    }

    /**
     * Retourne l'URI de l'image de l'anime.
     * @return L'URI de l'image de l'anime.
     */
    public String getImageUri(){
        return imageUri;
    }

    /**
     * Retourne le score de l'anime.
     * @return Le score de l'anime.
     */
    public int getScore(){
        return score;
    }

    /**
     * Retourne le statut de l'anime.
     * @return Le statut de l'anime.
     */
    public String getStatus(){
        return status;
    }

    /**
     * Retourne le nombre d'épisodes regardés de l'anime.
     * @return Le nombre d'épisodes regardés de l'anime.
     */
    public int getEpWatch(){
        return epWatch;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet Anime.
     * @return La représentation sous forme de chaîne de caractères de l'objet Anime.
     */
    @Override
    public String toString(){
        return " id : "+ this.id + " titre : "+ this.title +" imageUri : "+ this.imageUri +" score : "+ this.score +" status : "+ this.status;
    }
}
