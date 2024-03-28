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

public class ClientSocket {

    private static Socket mSocket;

    public static void connectToServer(User user) {
        new ConnectTask(user).execute();
    }

    private static class ConnectTask extends AsyncTask<Void, Void, Void> {
        private User user;

        public ConnectTask(User user) {
            this.user = user;
        }

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

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.e("ClientSocket", "La connexion au serveur a été annulée ou a échoué lors de l'initialisation.");
        }
    }

    public static void emit(String event, JSONObject data) {
        if (mSocket != null && mSocket.connected()) {
            Log.d("ClientSocket", "Emitting event: " + event + " with data: " + data.toString());
            mSocket.emit(event, data);
        } else {
            Log.e("ClientSocket", "Socket not connected or null");
        }
    }

    public static void emitJsonArray(String event, JSONArray data) {
        if (mSocket != null && mSocket.connected()) {
            Log.d("ClientSocket", "Emitting event: " + event + " with data: " + data.toString());
            mSocket.emit(event, data);
        } else {
            Log.e("ClientSocket", "Socket not connected or null");
        }
    }

    public static void on(String event, Emitter.Listener listener) {
        if (mSocket != null) {
            mSocket.on(event, listener);
        } else {
            Log.e("ClientSocket", "Socket not initialized");
        }
    }
    public static void off(String event) {
        if (mSocket != null) {
            mSocket.off(event);
        } else {
            Log.e("ClientSocket", "Socket not initialized");
        }
    }


}
