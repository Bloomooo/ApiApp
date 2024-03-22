package com.api.projet.backend;

import android.util.Log;

import androidx.annotation.NonNull;

import com.api.projet.entity.Lobby;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseQuery {

    private static DatabaseQuery instance;
    private DatabaseConnexion databaseConnexion;

    private FirebaseFirestore db;

    private boolean isSuccess = false;

    private List<Lobby> lobbyList;
    private DatabaseQuery(){
        this.databaseConnexion = DatabaseConnexion.getInstance();
        this.db = databaseConnexion.getConnexionDatabase();
        this.lobbyList = new ArrayList<>();
    }

    public boolean addLobby(Map<Integer, Lobby> lobby){
        this.db.collection("lobby")
                .add(lobby)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("ggez", "tgggzgzjfiogjzogouzougzuho");
                        isSuccess = true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("erreur addlobby", e.getMessage());
                        isSuccess = false;
                    }
                });
        return isSuccess;
    }

    public List<Lobby> getLobby(){
        this.db.collection("lobby")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                long id = (long) document.get("id");
                                Map<String, String> map = (Map<String, String>) document.get("Lobby");
                                Lobby lobby = new Lobby(map.get("name"), map.get("author"));
                                lobbyList.add(lobby);
                                Log.i("DATA ", id+" " +lobby.getName()+" "+lobby.getAuthor());
                            }
                        }
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Data getLobby()", "Pas de data");
                    }
                });
        return lobbyList;

    }

    public static DatabaseQuery getInstance(){
        if(instance == null){
            instance = new DatabaseQuery();
        }
        return instance;
    }

}
