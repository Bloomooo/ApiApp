package com.api.projet.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.api.projet.adapter.LobbyAdapter;
import com.api.projet.databinding.FragmentHomeBinding;
import com.api.projet.entity.Lobby;
import com.api.projet.inter.ApiService;
import com.api.projet.inter.IntentInterface;
import com.api.projet.ui.game.PreLobby;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements IntentInterface {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private LobbyAdapter adapterRecycleView;

    private ApiService apiService;

    private Button createLobbyButton;

    private FirebaseAuth mAuth;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        initComponent(root);
        initListener();
        setupLiveDataObservers(homeViewModel);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initComponent(View root) {
        this.recyclerView = root.findViewById(R.id.recyclerViewLobby);
        createLobbyButton = root.findViewById(R.id.createLobby);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterRecycleView = new LobbyAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setAdapter(adapterRecycleView);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.10.2:5500/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        mAuth = FirebaseAuth.getInstance();

    }

    private void initListener(){
        createLobbyButton.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_lobby, null);
            builder.setView(dialogView)
                    .setTitle("Créer un lobby")
                    .setPositiveButton("Créer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText lobbyNameEditText = dialogView.findViewById(R.id.lobbyNameEditText);
                            String lobbyName = lobbyNameEditText.getText().toString();

                            createLobby(lobbyName, mAuth.getCurrentUser().getEmail());
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }

    private void createLobby(String name, String host){
        Gson gson = new Gson();

        Map<String, Object> lobbyDetails = new HashMap<>();
        lobbyDetails.put("name", name);
        lobbyDetails.put("author", host);

        Map<String, Object> lobbyWrapper = new HashMap<>();
        lobbyWrapper.put("lobby", lobbyDetails);

        String jsonBody = gson.toJson(lobbyWrapper);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);

        Call<Void> call = apiService.createLobby(requestBody);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("CreateLobby", "Lobby created successfully");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("CreateLobby", "Failed to create lobby");
            }
        });
    }

    private void setupLiveDataObservers(HomeViewModel homeViewModel) {
        homeViewModel.getLobby().observe(getViewLifecycleOwner(), new Observer<List<Lobby>>() {
            @Override
            public void onChanged(List<Lobby> lobby) {
                adapterRecycleView.setData(lobby);
            }
        });
    }

    @Override
    public void doIntent(String lobbyId) {
        Intent intent = new Intent(getContext(), PreLobby.class);
        intent.putExtra("lobbyId", lobbyId);
        Log.i("GAIGAJFGAIJFJIAA",lobbyId);
        startActivity(intent);
    }
}
