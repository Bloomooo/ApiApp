package com.api.projet.ui.animedetails;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.api.projet.databinding.FragmentHomeBinding;

public class AnimeDetailsFragment extends Fragment {

    private AnimeDetailsViewModel mViewModel;
    private FragmentHomeBinding binding;

    private View root;

    public static AnimeDetailsFragment newInstance() {
        return new AnimeDetailsFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        return  root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}