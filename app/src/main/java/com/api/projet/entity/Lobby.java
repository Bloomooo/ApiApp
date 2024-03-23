package com.api.projet.entity;

public class Lobby {

    private String id ;

    public String name;

    public String author;

    public Lobby() {
    }

    public Lobby(String id, String name, String author){
        this.id = id;
        this.name = name;
        this.author = author;
    }
    public String getId() {
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getAuthor(){
        return this.author;
    }
}
