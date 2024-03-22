package com.api.projet.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.api.projet.adapter.AdapterList;
import com.api.projet.adapter.LobbyAdapter;

import com.api.projet.databinding.FragmentHomeBinding;
import com.api.projet.entity.Anime;
import com.api.projet.entity.Lobby;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private LobbyAdapter adapterRecycleView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        initComponent(root);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterRecycleView = new LobbyAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapterRecycleView);
    }


    private void setupLiveDataObservers(HomeViewModel homeViewModel) {
        homeViewModel.getLobby().observe(getViewLifecycleOwner(), new Observer<List<Lobby>>() {
            @Override
            public void onChanged(List<Lobby> lobby) {
                adapterRecycleView.setData(lobby);
            }
        });
    }
}
