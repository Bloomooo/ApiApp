package com.api.projet.entity;

/**
 * Cette classe représente un utilisateur dans l'application.
 */
public class User {
    /** Nom de l'utilisateur */
    private String name;

    /**
     * Constructeur de la classe User.
     * @param name Le nom de l'utilisateur.
     */
    public User(String name){
        this.name = name;
    }

    /**
     * Retourne le nom de l'utilisateur.
     * @return Le nom de l'utilisateur.
     */
    public String getName(){
        return this.name;
    }
}
