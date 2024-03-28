package com.api.projet.backend;

import android.util.Log;

import androidx.annotation.NonNull;

import com.api.projet.entity.Lobby;
import com.api.projet.entity.Player;
import com.api.projet.entity.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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

    public Task<List<Lobby>> getLobby() {
        TaskCompletionSource<List<Lobby>> taskCompletionSource = new TaskCompletionSource<>();
        db.collection("lobby")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Lobby> lobbyList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = (String) document.get("id");
                            String name = (String) document.get("name");
                            String author = (String) document.get("author");
                            Lobby lobby = new Lobby(id , name, author);
                            lobbyList.add(lobby);
                        }
                        taskCompletionSource.setResult(lobbyList);
                    } else {
                        taskCompletionSource.setException(task.getException());
                    }
                });
        return taskCompletionSource.getTask();
    }
    public Task<List<Player>> getPlayers(String lobbyId) {
        TaskCompletionSource<List<Player>> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("lobby").document(lobbyId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            List<Player> playerList = new ArrayList<>();
                            Object playersObj = document.get("players");
                            if (playersObj instanceof List<?>) {
                                List<?> users = (List<?>) playersObj;
                                for (Object playerObj : users) {
                                    if (playerObj instanceof Map) {
                                        Map<String, Object> players = (Map<String, Object>) playerObj;
                                        String idString = (String) players.get("id");
                                        Long id = Long.parseLong(idString);
                                        String userName = (String) players.get("name");
                                        Player player = new Player(id, userName);
                                        playerList.add(player);
                                    } else {
                                        System.out.println("Unexpected data type in users list: " + playerObj.getClass().getName());
                                    }
                                }
                            }
                            taskCompletionSource.setResult(playerList);
                        } else {
                            taskCompletionSource.setException(new Exception("Lobby not found"));
                        }
                    } else {
                        taskCompletionSource.setException(task.getException());
                    }
                });

        return taskCompletionSource.getTask();
    }


    public Task<Player> foundHostLobbyById(String lobbyId) {
        TaskCompletionSource<Player> taskCompletionSource = new TaskCompletionSource<>();

        DocumentReference lobbyRef = db.collection("lobby").document(lobbyId);
        lobbyRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String hostName = document.getString("host.name");
                    String hostIdString = document.getString("host.id");
                    if (hostIdString != null) {
                        Long hostId = Long.parseLong(hostIdString);
                        taskCompletionSource.setResult(new Player(hostId, hostName));
                    } else {
                        taskCompletionSource.setException(new Exception("Host ID is null"));
                    }
                } else {
                    taskCompletionSource.setException(new FirebaseFirestoreException("Document does not exist", FirebaseFirestoreException.Code.NOT_FOUND));
                }
            } else {
                taskCompletionSource.setException(task.getException());
            }
        });

        return taskCompletionSource.getTask();
    }



    public static DatabaseQuery getInstance(){
        if(instance == null){
            instance = new DatabaseQuery();
        }
        return instance;
    }

}
