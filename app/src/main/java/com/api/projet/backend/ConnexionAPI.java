package com.api.projet.backend;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.github.cdimascio.dotenv.Dotenv;

public class ConnexionAPI {

    private HttpURLConnection httpURLConnection;
    public ConnexionAPI(){
        this.httpURLConnection = null;
    }
    public void getListAnime() {
        AsyncTask<Void, Void, Integer> apiTask = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                try {
                    Dotenv dotenv = Dotenv.configure().filename(".env").load();

                    String CLIENT_ID = dotenv.get("CLIENT_ID");
                    String URL_GETANIME = dotenv.get("URL_GETANIME");

                    URL url = new URL(URL_GETANIME);
                    httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestProperty("X-MAL-CLIENT-ID",CLIENT_ID);

                    httpURLConnection.setRequestMethod("GET");
                    int responseCode = httpURLConnection.getResponseCode();
                    Log.i("ggez", String.valueOf(responseCode));
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            response.append(line);
                        }
                        bufferedReader.close();
                        inputStream.close();

                        JSONObject jsonResponse = new JSONObject(response.toString());
                        JSONArray dataArray = jsonResponse.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject anime = dataArray.getJSONObject(i);
                            JSONObject node = anime.getJSONObject("node");
                            JSONObject listStatus = anime.getJSONObject("list_status");

                            String title = node.getString("title");
                            String status = listStatus.getString("status");
                            int score = listStatus.getInt("score");
                            int numEpisodesWatched = listStatus.getInt("num_episodes_watched");

                            Log.i("Anime Info", "Title: " + title + ", Status: " + status + ", Score: " + score + ", Episodes Watched: " + numEpisodesWatched);
                        }
                        return 0;
                    } else {
                        return responseCode;
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    return HttpURLConnection.HTTP_BAD_REQUEST;
                }
            }

        };

        apiTask.execute();
    }
}
