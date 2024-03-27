package com.api.projet.backend;

import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseConnexion {

    private static DatabaseConnexion instance;

    private FirebaseFirestore db;
    private DatabaseConnexion(){
        this.db = FirebaseFirestore.getInstance();
    }

    public static DatabaseConnexion getInstance(){
        if(instance == null){
            instance = new DatabaseConnexion();
        }
        return instance;
    }

    public FirebaseFirestore getConnexionDatabase(){
        return this.db;
    }

    /*  Exemple ecriture dans la bd
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
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
     */

    /* Exemple lecture dans la bd
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
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
     */
}
