package com.api.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.api.projet.adapter.AdapterAnimeImg;
import com.api.projet.entity.AnimeDetailed;

import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.api.projet.backend.ConnexionAPI;
import com.api.projet.inter.AnimeCallBackInterface;
import com.api.projet.itemDecoration.SpaceItemDecoration;
import com.squareup.picasso.Picasso;

public class AnimeDetailActivity extends AppCompatActivity implements AnimeCallBackInterface {
    private String client_id;
    private String URL;
    private HttpURLConnection httpURLConnection;
    private AnimeDetailed animeDetailed;

    private RecyclerView recyclerView;
    private AdapterAnimeImg adapter;

    private TextView animeTitle;
    private TextView animeTitleJp;
    private TextView description;
    private TextView dateDebut;
    private TextView dateFin;
    private TextView noteMoy;
    private TextView rank;
    private TextView popularity;
    private TextView genres;
    private TextView status;
    private TextView ep;

    private ImageView imgAnime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_detail);
        ConnexionAPI conn = ConnexionAPI.getInstance();
        client_id = conn.getClient_id();
        URL = "https://api.myanimelist.net/v2/anime/#" +
                "?fields=id,title,main_picture,alternative_titles," +
                "start_date,end_date,synopsis,mean,rank,popularity," +
                "num_list_users,num_scoring_users,nsfw,created_at," +
                "updated_at,media_type,status,genres,my_list_status," +
                "num_episodes,start_season,broadcast,source,average_episode_duration," +
                "rating,pictures,background,related_anime,related_manga,recommendations," +
                "studios,statistics";
        Intent intent = getIntent();
        int animeId = intent.getIntExtra("id",0);
        initcomponents();
        loadInfoAnime(animeId);
    }

    private void initcomponents(){
        recyclerView = findViewById(R.id.recyclerViewAnimeDetailed);

        // 2. Créez une instance de votre AdapterAnimeImg
        List<String> urls = new ArrayList<>();
        adapter = new AdapterAnimeImg(urls);

        // 3. Configurez votre RecyclerView pour utiliser votre adaptateur
        recyclerView.setAdapter(adapter);

        // 4. Définissez le gestionnaire de disposition de votre RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(8));

        animeTitle = findViewById(R.id.textViewTitle);
        animeTitleJp = findViewById(R.id.textViewTitlejp);
        description = findViewById(R.id.textViewDescription);
        dateDebut = findViewById(R.id.textViewStartDateValue);
        dateFin = findViewById(R.id.textViewEndDateValue);
        noteMoy = findViewById(R.id.textViewNoteMoyValue);
        rank = findViewById(R.id.textViewRankValue);
        popularity = findViewById(R.id.textViewPopularityValue);
        genres = findViewById(R.id.textViewGenresValue);
        status = findViewById(R.id.textViewStatusValue);
        ep = findViewById(R.id.textViewEpValue);
        imgAnime = findViewById(R.id.imageViewAnime);




    }

    private void showData(){
        adapter.setListeURL(animeDetailed.getPictures());
        adapter.notifyDataSetChanged();

        animeTitle.setText(animeDetailed.getTitle());
        animeTitleJp.setText(animeDetailed.getTitleJp());
        description.setText(animeDetailed.getSynopsis());
        dateDebut.setText(animeDetailed.getStartDate());
        dateFin.setText(animeDetailed.getEndDate());
        noteMoy.setText(animeDetailed.getNoteMoy() + "⭐");
        rank.setText("#"+animeDetailed.getRank());
        popularity.setText("#"+animeDetailed.getPopularity());
        status.setText(animeDetailed.getStatus().replace("_"," "));
        ep.setText(animeDetailed.getEp());

        String genreTxt = animeDetailed.getGenres().toString().replace("[","").replace("]","");

        genres.setText(genreTxt);


        Picasso.get().load(animeDetailed.getImageUri()).into(imgAnime);
    }


    private void loadInfoAnime(int animeId) {
        AsyncTask<Void, Void, AnimeDetailed> apiTask = new AsyncTask<Void, Void, AnimeDetailed>(){
            @Override
            protected AnimeDetailed doInBackground(Void... voids) {
                AnimeDetailed anime;
                try {
                    String customUrl = URL.replace("#", String.valueOf(animeId));
                    URL url = new URL(customUrl);
                    httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestProperty("X-MAL-CLIENT-ID", client_id);
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
                        Log.i("data", jsonResponse.toString());
                        int id = jsonResponse.getInt("id");
                        String title = jsonResponse.getString("title");
                        String titleJp = jsonResponse.getJSONObject("alternative_titles").getString("ja");
                        String startDate = jsonResponse.getString("start_date");
                        String endDate = jsonResponse.getString("end_date");
                        String synopsis = jsonResponse.getString("synopsis");
                        String moyNote = jsonResponse.getString("mean");
                        String rank = jsonResponse.getString("rank");
                        String popularity =jsonResponse.getString("popularity");
                        String status = jsonResponse.getString("status");
                        JSONArray genresArray = jsonResponse.getJSONArray("genres");
                        List<String> genres = new ArrayList<>();
                        for (int i = 0; i < genresArray.length(); i++) {
                            genres.add(genresArray.getJSONObject(i).getString("name"));
                        }
                        String ep = jsonResponse.getString("num_episodes");
                        JSONArray picturesArray = jsonResponse.getJSONArray("pictures");
                        List<String> pictures = new ArrayList<>();
                        for (int i = 0; i < picturesArray.length(); i++) {
                            pictures.add(picturesArray.getJSONObject(i).getString("medium"));
                        }
                        String img = jsonResponse.getJSONObject("main_picture").getString("medium");
                        anime = new AnimeDetailed(id,title,titleJp,startDate,endDate,synopsis,moyNote,rank,popularity,genres,pictures,img,status,ep);

                    } else {
                        Log.i("ERREUR", "HTTP REQUEST NOT OK");
                        anime = null;
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    anime = null;
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
                return anime;
            }

            @Override
            protected void onPostExecute(AnimeDetailed anime) {
                super.onPostExecute(anime);
                if(anime != null){
                    onSuccess(anime);
                }
                else {
                    Log.e("GETANIMEREQUEST","RETURNED VALUE IS NULL");
                }

            }
        };
        apiTask.execute();

    }


    @Override
    public void onSuccess(AnimeDetailed anime) {

        Log.i("anime: ", anime.toString());
        animeDetailed = anime;
        showData();
    }
}