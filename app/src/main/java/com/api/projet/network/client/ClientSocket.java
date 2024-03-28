package com.api.projet.network.client;

import android.os.AsyncTask;
import android.util.Log;

import com.api.projet.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Cette classe gère les opérations de communication avec le serveur via un socket client.
 * Elle permet de se connecter au serveur, d'émettre des événements et d'écouter les événements du serveur.
 */
public class ClientSocket {

    /**
     * Le socket utilisé pour la connexion au serveur.
     */
    private static Socket mSocket;

    /**
     * Méthode pour se connecter au serveur.
     *
     * @param user L'utilisateur souhaitant se connecter.
     */
    public static void connectToServer(User user) {
        new ConnectTask(user).execute();
    }

    /**
     * Classe interne pour gérer la connexion asynchrone au serveur.
     */
    private static class ConnectTask extends AsyncTask<Void, Void, Void> {
        private User user;

        /**
         * Constructeur de la classe ConnectTask.
         *
         * @param user L'utilisateur souhaitant se connecter.
         */
        public ConnectTask(User user) {
            this.user = user;
        }

        /**
         * Méthode exécutée en arrière-plan pour établir la connexion au serveur.
         *
         * @return null
         */
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mSocket = IO.socket("http://10.192.115.26:5500");
            } catch (URISyntaxException e) {
                Log.e("ClientSocket", "Erreur de syntaxe d'URI", e);
                cancel(true);
            }
            return null;
        }

        /**
         * Méthode exécutée après l'établissement de la connexion au serveur.
         *
         * @param result Le résultat de l'exécution.
         */
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (mSocket != null) {
                mSocket.connect();
                Log.d("ClientSocket", "Tentative de connexion au serveur...");
                if (user != null) {
                    JSONObject userInfo = new JSONObject();
                    try {
                        userInfo.put("name", user.getName());

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    mSocket.emit("registerUser", userInfo);
                }
            }
        }

        /**
         * Méthode appelée lorsque la connexion au serveur est annulée ou échouée.
         */
        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.e("ClientSocket", "La connexion au serveur a été annulée ou a échoué lors de l'initialisation.");
        }
    }

    /**
     * Méthode pour émettre un événement vers le serveur avec des données JSON.
     *
     * @param event Le nom de l'événement à émettre.
     * @param data  Les données à envoyer avec l'événement.
     */
    public static void emit(String event, JSONObject data) {
        if (mSocket != null && mSocket.connected()) {
            Log.d("ClientSocket", "Emitting event: " + event + " with data: " + data.toString());
            mSocket.emit(event, data);
        } else {
            Log.e("ClientSocket", "Socket not connected or null");
        }
    }

    /**
     * Méthode pour émettre un événement vers le serveur avec un tableau JSON.
     *
     * @param event Le nom de l'événement à émettre.
     * @param data  Le tableau JSON à envoyer avec l'événement.
     */
    public static void emitJsonArray(String event, JSONArray data) {
        if (mSocket != null && mSocket.connected()) {
            Log.d("ClientSocket", "Emitting event: " + event + " with data: " + data.toString());
            mSocket.emit(event, data);
        } else {
            Log.e("ClientSocket", "Socket not connected or null");
        }
    }

    /**
     * Méthode pour écouter les événements du serveur.
     *
     * @param event    Le nom de l'événement à écouter.
     * @param listener L'écouteur d'événement à associer à cet événement.
     */
    public static void on(String event, Emitter.Listener listener) {
        if (mSocket != null) {
            mSocket.on(event, listener);
        } else {
            Log.e("ClientSocket", "Socket not initialized");
        }
    }

    /**
     * Méthode pour arrêter l'écoute d'un événement du serveur.
     *
     * @param event Le nom de l'événement à ne plus écouter.
     */
    public static void off(String event) {
        if (mSocket != null) {
            mSocket.off(event);
        } else {
            Log.e("ClientSocket", "Socket not initialized");
        }
    }
}

