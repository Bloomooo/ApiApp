package com.api.projet.ui.connexion;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.api.projet.AnimeListData;
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

import io.github.cdimascio.dotenv.Dotenv;

public class ConnexionViewModel extends ViewModel implements ListCallBackInterface {

    static  private  String CLIENT_ID;
    static  private  String URL_GETANIME;

    private HttpURLConnection httpURLConnection;
    public  ConnexionViewModel(){
        ConnexionAPI conn = ConnexionAPI.getInstance();
        CLIENT_ID = conn.getCLIENT_ID();
        URL_GETANIME = conn.getURLGETANIME();
    }


    public void control(String username){
        if(username != "" && username != null){
            apiCalling(username);
            // affiché chargement
        } else{
            // erreur
        }
    }
    @Override
    public void onSuccess(List<Anime> listAnime) {
        if(listAnime != null){
            AnimeListData.updateAnimeList(listAnime);
        } else{
            // erreur
        }
    }

    private void apiCalling(String username){
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
                        Log.i("Reponse code: ", String.valueOf(responseCode));

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
                                int epWatch = listStatus.getInt("num_episodes_watched");

                                Anime animeEntity = new Anime(i, title, imageUri, score, status, epWatch);
                                animeList.add(animeEntity);
                            }
                            Log.i("test",animeList.toString());
                        }else{
                            Log.i("ERREUR","HTTP REQUEST NOT OK");
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
