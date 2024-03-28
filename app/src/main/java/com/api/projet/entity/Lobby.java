package com.api.projet.entity;

import java.util.Objects;

/**
 * Cette classe représente un lobby dans l'application.
 */
public class Lobby {

    /** Identifiant du lobby */
    private String id;

    /** Nom du lobby */
    public String name;

    /** Auteur du lobby */
    public String author;

    /**
     * Constructeur de la classe Lobby.
     * @param id Identifiant du lobby.
     * @param name Nom du lobby.
     * @param author Auteur du lobby.
     */
    public Lobby(String id, String name, String author){
        this.id = id;
        this.name = name;
        this.author = author;
    }

    /**
     * Retourne l'identifiant du lobby.
     * @return L'identifiant du lobby.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Retourne le nom du lobby.
     * @return Le nom du lobby.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Retourne l'auteur du lobby.
     * @return L'auteur du lobby.
     */
    public String getAuthor(){
        return this.author;
    }

    /**
     * Indique si cet objet est égal à un autre objet donné.
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lobby)) return false;
        Lobby lobby = (Lobby) o;
        return Objects.equals(getId(), lobby.getId());
    }

    /**
     * Retourne le code de hachage de cet objet.
     * @return Le code de hachage de cet objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
