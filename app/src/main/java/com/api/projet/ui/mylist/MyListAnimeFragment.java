package com.api.projet.ui.mylist;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.api.projet.adapter.AdapterList;
import com.api.projet.entity.Anime;
import com.api.projet.databinding.FragmentMylistAnimeBinding;

import java.util.List;

public class MyListAnimeFragment extends Fragment {

    private FragmentMylistAnimeBinding binding;
    private RecyclerView recyclerView;
    private AdapterList adapterRecycleView;
    private EditText searchEditText;
    private MyListAnimeViewModel myListViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMylistAnimeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initComponents(root);

        myListViewModel =
                new ViewModelProvider(this).get(MyListAnimeViewModel.class);
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
        searchEditText = rootView.findViewById(R.id.searchEditText);

        GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                myListViewModel.itemAction(recyclerView,e);
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myListViewModel.newFilterAnimeList(searchEditText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                gestureDetector.onTouchEvent(e);

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }


        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}