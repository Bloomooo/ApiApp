package com.api.projet.network.client;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ClientSocket {

    private static Socket mSocket;

    public static void connectToServer() {
        new ConnectTask().execute();
    }

    private static class ConnectTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                mSocket = IO.socket("http://172.20.10.2:5500");
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
