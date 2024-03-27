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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.AnimeListData;
import com.api.projet.R;
import com.api.projet.adapter.LobbyAdapter;
import com.api.projet.databinding.FragmentHomeBinding;
import com.api.projet.entity.Anime;
import com.api.projet.entity.Lobby;
import com.api.projet.inter.IntentInterface;
import com.api.projet.network.client.ClientSocket;
import com.api.projet.ui.game.PreLobby;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;

public class HomeFragment extends Fragment implements IntentInterface {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private LobbyAdapter adapterRecycleView;

    private Button createLobbyButton;

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
                            RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);
                            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                            String selectedValue = "";
                            if (selectedRadioButtonId != -1) {
                                RadioButton selectedRadioButton = dialogView.findViewById(selectedRadioButtonId);
                                selectedValue = selectedRadioButton.getText().toString();

                            } else {
                                Log.d("SelectedRadioButton", "No radio button selected");
                            }
                            createLobby(lobbyName, selectedValue);
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

    private void createLobby(String name, String nb){
        JSONObject lobbyDetails = new JSONObject();
        JSONArray animeJsonArray = new JSONArray();
        try {
            lobbyDetails.put("name", name);
            lobbyDetails.put("nb", nb);
            for(Anime anime: AnimeListData.getAnimeList()){
                JSONObject animeObject = new JSONObject();
                animeObject.put("title", anime.getTitle());
                animeObject.put("image", anime.getImageUri());
                animeJsonArray.put(animeObject);
            }
            lobbyDetails.put("animeList", animeJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ClientSocket.emit("createLobby", lobbyDetails);

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
        joinLobby(lobbyId);
        Intent intent = new Intent(getContext(), PreLobby.class);
        intent.putExtra("lobbyId", lobbyId);
        startActivity(intent);
    }
    private void joinLobby(String lobbyId){
        JSONObject jsonObject = new JSONObject();
        JSONArray animeJsonArray = new JSONArray();
        try{
            jsonObject.put("lobbyId", lobbyId);
            for(Anime anime: AnimeListData.getAnimeList()){
                JSONObject animeObject = new JSONObject();
                animeObject.put("title", anime.getTitle());
                animeObject.put("image", anime.getImageUri());
                animeJsonArray.put(animeObject);
            }
            jsonObject.put("animeList", animeJsonArray);
        }catch (JSONException e){
            Log.e("JOINLOBBY", e.getMessage());
        }
        ClientSocket.emit("joinLobby", jsonObject);
    }
    @Override
    public void onStart() {
        super.onStart();
        ClientSocket.on("lobbyCreated", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Lobby lobby = null;
                        try {
                            lobby = new Lobby(data.getString("lobbyId"), data.getString("name"), data.getString("username"));
                            adapterRecycleView.addLobby(lobby);

                            Log.d("HomeFragment", "Lobby created: " + data.getString("lobbyId"));
                            if(data.getString("username").equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())){
                                Intent intent = new Intent(getContext(), PreLobby.class);
                                intent.putExtra("lobbyId", data.getString("lobbyId"));
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });

        ClientSocket.on("removeLobby", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HomeViewModel homeViewModel =
                                new ViewModelProvider(HomeFragment.this).get(HomeViewModel.class);
                        setupLiveDataObservers(homeViewModel);
                    }
                });
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        ClientSocket.off("createLobby");
    }

}
