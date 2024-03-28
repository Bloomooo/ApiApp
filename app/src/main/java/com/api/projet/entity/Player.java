package com.api.projet.entity;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Cette classe représente un joueur dans l'application.
 */
public class Player {
    /** Identifiant du joueur */
    private Long id;

    /** Nom du joueur */
    private String name;

    /** Score du joueur */
    private int score;

    /** Réponse du joueur */
    private String answer;

    /** Couleur du joueur */
    private int color;

    /** Image du joueur encodée en base64 */
    private Bitmap imageBase64;

    /**
     * Constructeur de la classe Player.
     * @param id Identifiant du joueur.
     * @param name Nom du joueur.
     */
    public Player(Long id, String name) {
        this.id = id;
        this.name = name;
        this.score = 0;
        this.answer = "";
        this.color = Color.WHITE;
        this.imageBase64 = null;
    }

    /**
     * Retourne l'identifiant du joueur.
     * @return L'identifiant du joueur.
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du joueur.
     * @param id Le nouvel identifiant du joueur.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le nom du joueur.
     * @return Le nom du joueur.
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom du joueur.
     * @param name Le nouveau nom du joueur.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne le score du joueur.
     * @return Le score du joueur.
     */
    public int getScore() {
        return score;
    }

    /**
     * Modifie le score du joueur.
     * @param score Le nouveau score du joueur.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Retourne la réponse du joueur.
     * @return La réponse du joueur.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Modifie la réponse du joueur.
     * @param answer La nouvelle réponse du joueur.
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Retourne la couleur du joueur.
     * @return La couleur du joueur.
     */
    public int getColor() {
        return color;
    }

    /**
     * Modifie la couleur du joueur.
     * @param color La nouvelle couleur du joueur.
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Retourne l'image du joueur encodée en base64.
     * @return L'image du joueur encodée en base64.
     */
    public Bitmap getImageBase64(){
        return this.imageBase64;
    }

    /**
     * Modifie l'image du joueur encodée en base64.
     * @param imageBase64 La nouvelle image du joueur encodée en base64.
     */
    public void setImageBase64(Bitmap imageBase64){
        this.imageBase64 = imageBase64;
    }
}
