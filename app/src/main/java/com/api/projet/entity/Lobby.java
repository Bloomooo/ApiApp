package com.api.projet.entity;

import java.util.Objects;

public class Lobby {

    private String id ;

    public String name;

    public String author;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lobby)) return false;
        Lobby lobby = (Lobby) o;
        return Objects.equals(getId(), lobby.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
