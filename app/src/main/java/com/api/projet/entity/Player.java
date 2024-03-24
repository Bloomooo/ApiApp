package com.api.projet.entity;

public class Player {

    private Long id;
    private String name;

    public Player(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
