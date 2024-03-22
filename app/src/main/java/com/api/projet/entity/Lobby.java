package com.api.projet.entity;

public class Lobby {

    public String name;

    public String author;

    public Lobby() {
    }

    public Lobby(String name, String author){
        this.name = name;
        this.author = author;
    }

    public String getName(){
        return this.name;
    }

    public String getAuthor(){
        return this.author;
    }
}
