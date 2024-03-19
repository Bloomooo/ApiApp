package com.api.projet.ui.mylist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.AnimeListData;
import com.api.projet.R;
import com.api.projet.adapter.AdapterList;
import com.api.projet.databinding.FragmentMylistBinding;
import com.api.projet.entity.Anime;

import java.util.List;

public class MyListFragment extends Fragment {

    private FragmentMylistBinding binding;
    private RecyclerView recyclerView;
    private AdapterList adapterRecycleView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMylistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initComponents(root);

        MyListViewModel myListViewModel =
                new ViewModelProvider(this).get(MyListViewModel.class);
        myListViewModel.getAnimeList().observe(getViewLifecycleOwner(), new Observer<List<Anime>>() {
            @Override
            public void onChanged(List<Anime> anime) {
                adapterRecycleView = new AdapterList(getContext(), anime);
                recyclerView.setAdapter(adapterRecycleView);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initComponents(View rootView){
        recyclerView = rootView.findViewById(R.id.recyclerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}