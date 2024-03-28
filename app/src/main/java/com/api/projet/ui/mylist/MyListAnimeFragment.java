package com.api.projet.ui.mylist;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.api.projet.entity.Anime;
import com.api.projet.databinding.FragmentMylistAnimeBinding;
import com.api.projet.adapter.AdapterList;

import java.util.List;

/**
 * Fragment displaying the user's anime list.
 */
public class MyListAnimeFragment extends Fragment {

    /** View binding object for the fragment. */
    private FragmentMylistAnimeBinding binding;

    /** RecyclerView to display the list of anime. */
    private RecyclerView recyclerView;

    /** Adapter for the RecyclerView. */
    private AdapterList adapterRecycleView;

    /** EditText for searching anime in the list. */
    private EditText searchEditText;

    /** ViewModel for managing the data of the fragment. */
    private MyListAnimeViewModel myListViewModel;

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMylistAnimeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initComponents(root);

        myListViewModel = new ViewModelProvider(this).get(MyListAnimeViewModel.class);
        myListViewModel.getAnimeList().observe(getViewLifecycleOwner(), new Observer<List<Anime>>() {
            @Override
            public void onChanged(List<Anime> anime) {
                adapterRecycleView = new AdapterList(getContext(), anime);
                recyclerView.setAdapter(adapterRecycleView);
            }
        });

        return root;
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view.
     * @param view The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * Initializes the components of the fragment.
     * @param rootView The root view of the fragment.
     */
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myListViewModel.newFilterAnimeList(searchEditText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                gestureDetector.onTouchEvent(e);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {}

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });
    }

    /**
     * Called when the fragment's view is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
