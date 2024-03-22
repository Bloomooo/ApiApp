package com.api.projet.backend;

import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseConnexion {

    private DatabaseConnexion instance;

    private FirebaseFirestore db;
    private DatabaseConnexion(){
        this.db = FirebaseFirestore.getInstance();
    }

    public DatabaseConnexion getInstance(){
        if(instance == null){
            instance =  new DatabaseConnexion();
        }
        return instance;
    }

    
}
