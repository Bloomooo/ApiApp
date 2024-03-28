package com.api.projet.ui.connexion;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.api.projet.model.AnimeListData;
import com.api.projet.backend.ConnexionAPI;
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

/**
 * ViewModel class for handling network requests and updating UI accordingly in the Connection fragment.
 */
public class ConnexionViewModel extends ViewModel implements ListCallBackInterface {

    /** The client ID used for making API calls. */
    private static String CLIENT_ID;

    /** The URL for fetching anime data. */
    private static String URL_GETANIME;

    /** The HTTP connection used for API requests. */
    private HttpURLConnection httpURLConnection;

    /**
     * Constructor to initialize ViewModel and retrieve client ID and URL from ConnexionAPI singleton instance.
     */
    public ConnexionViewModel(){
        ConnexionAPI conn = ConnexionAPI.getInstance();
        CLIENT_ID = conn.getClient_id();
        URL_GETANIME = conn.getUrl();
    }

    /**
     * Method to control the user input and initiate API call.
     *
     * @param username The username entered by the user.
     */
    public void control(String username){
        if(username != null && !username.isEmpty()){
            apiCalling(username);
            // Show loading
        } else{
            // Show error
        }
    }

    @Override
    public void onSuccess(List<Anime> listAnime) {
        if(listAnime != null){
            AnimeListData.updateAnimeList(listAnime);
        } else{
            // Show error
        }
    }

    /**
     * Method to initiate the API call to fetch anime data.
     *
     * @param username The username of the user.
     */
    private void apiCalling(final String username){
        AsyncTask<Void, Void, List<Anime>> apiTask = new AsyncTask<Void, Void, List<Anime>>() {
            @Override
            protected List<Anime> doInBackground(Void... voids) {
                List<Anime> animeList = new ArrayList<>();
                try {
                    String customUrl = URL_GETANIME.replace("#",username);
                    URL url = new URL(customUrl);
                    httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestProperty("X-MAL-CLIENT-ID", CLIENT_ID);
                    httpURLConnection.setRequestMethod("GET");
                    int responseCode = httpURLConnection.getResponseCode();
                    Log.i("Response code: ", String.valueOf(responseCode));

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
                        Log.i("Length :","" + dataArray.length());
                        Log.i("URL",customUrl);
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject anime = dataArray.getJSONObject(i);
                            JSONObject node = anime.getJSONObject("node");
                            JSONObject listStatus = anime.getJSONObject("list_status");
                            JSONObject image = node.getJSONObject("main_picture");
                            int id = node.getInt("id");
                            String title = node.getString("title");
                            String status = listStatus.getString("status");
                            int score = listStatus.getInt("score");
                            String imageUri = image.getString("medium");
                            int epWatch = listStatus.getInt("num_episodes_watched");

                            Anime animeEntity = new Anime(id, title, imageUri, score, status, epWatch);
                            animeList.add(animeEntity);
                        }
                    }else{
                        Log.i("ERROR","HTTP REQUEST NOT OK");
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
                onSuccess(result);
            }
        };
        apiTask.execute();
    }
}
