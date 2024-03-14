package com.api.projet.backend;

import android.os.AsyncTask;
import android.util.Log;

import com.api.projet.entity.Anime;
import com.api.projet.inter.ListCallBackInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;

public class ConnexionAPI {
    private static ConnexionAPI instance;
    private HttpURLConnection httpURLConnection;

    private String CLIENT_ID;
    private String URL_GETANIME;

    private ConnexionAPI() {
        this.httpURLConnection = null;
        CLIENT_ID = "9e6d20d3be7307e202a88d5e1b131840";
        URL_GETANIME = "https://api.myanimelist.net/v2/users/Seele_/animelist?fields=list_status&limit=1000";
    }

    public static synchronized ConnexionAPI getInstance() {
        if (instance == null) {
            instance = new ConnexionAPI();
        }
        return instance;
    }

    public void getListAnime(ListCallBackInterface callback) {
        AsyncTask<Void, Void, List<Anime>> apiTask = new AsyncTask<Void, Void, List<Anime>>() {
            @Override
            protected List<Anime> doInBackground(Void... voids) {
                List<Anime> animeList = new ArrayList<>();
                try {
                    URL url = new URL(URL_GETANIME);
                    httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestProperty("X-MAL-CLIENT-ID", CLIENT_ID);
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
                            JSONObject image = node.getJSONObject("main_picture");
                            String title = node.getString("title");
                            String status = listStatus.getString("status");
                            int score = listStatus.getInt("score");
                            String imageUri = image.getString("medium");
                            Log.i("ggez", imageUri);
                            int epWatch = listStatus.getInt("num_episodes_watched");

                            Anime animeEntity = new Anime(i, title, imageUri, score, status, epWatch);
                            animeList.add(animeEntity);
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
                return animeList;
            }

            @Override
            protected void onPostExecute(List<Anime> result) {
                super.onPostExecute(result);
                callback.onSuccess(result);
            }
        };

        apiTask.execute();
    }
}
