package com.api.projet.inter;

import com.api.projet.entity.Lobby;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("joinLobby")
    Call<Void> createLobby(@Body RequestBody requestBody);
}
