package com.api.projet.network.client;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class ClientSocket {

    private static Socket mSocket;

    public static void connectToServer() {
        new ConnectTask().execute();
    }

    private static class ConnectTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                mSocket = IO.socket("http://192.168.195.1:5500");
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
}
