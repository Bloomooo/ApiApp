package com.api.projet.backend;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * La classe DatabaseConnexion représente une connexion à une base de données Firestore.
 */
public class DatabaseConnexion {

    /** Instance unique de DatabaseConnexion (Singleton) */
    private static DatabaseConnexion instance;

    /** Instance de FirebaseFirestore pour accéder à la base de données Firestore */
    private FirebaseFirestore db;

    /**
     * Constructeur privé de la classe DatabaseConnexion.
     * Initialise l'instance de FirebaseFirestore pour accéder à la base de données Firestore.
     */
    private DatabaseConnexion(){
        this.db = FirebaseFirestore.getInstance();
    }

    /**
     * Retourne l'instance unique de DatabaseConnexion (Singleton).
     * Si l'instance n'existe pas, elle est créée.
     * @return L'instance de DatabaseConnexion.
     */
    public static DatabaseConnexion getInstance(){
        if(instance == null){
            instance = new DatabaseConnexion();
        }
        return instance;
    }

    /**
     * Retourne l'instance de FirebaseFirestore pour accéder à la base de données Firestore.
     * @return L'instance de FirebaseFirestore.
     */
    public FirebaseFirestore getConnexionDatabase(){
        return this.db;
    }

    /*  Exemple d'écriture dans la base de données Firestore
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Alan");
        user.put("middle", "Mathison");
        user.put("last", "Turing");
        user.put("born", 1912);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot ajouté avec l'ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Erreur lors de l'ajout du document", e);
                    }
                });
     */

    /* Exemple de lecture dans la base de données Firestore
        db.collection("users")
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.w(TAG, "Erreur lors de la récupération des documents.", task.getException());
                }
            }
        });
     */
}
