package com.api.projet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.api.projet.entity.Anime;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder> {
    private List<Anime> animeList;
    private Context context;

    public AdapterList(Context context, List<Anime> animeList) {
        this.context = context;
        this.animeList = animeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listanime_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Anime anime = animeList.get(position);

        holder.titleTextView.setText(anime.getTitle());
        holder.statusTextView.setText(anime.getStatus());
        holder.scoreTextView.setText(String.valueOf(anime.getScore()));
        holder.episodesWatchedTextView.setText(String.valueOf(anime.getEpWatch()));

        Picasso.get()
                .load(anime.getImageUri())
                .into(holder.animeImageView);
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView animeImageView;
        TextView titleTextView;
        TextView statusTextView;
        TextView scoreTextView;
        TextView episodesWatchedTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            animeImageView = itemView.findViewById(R.id.animeImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            episodesWatchedTextView = itemView.findViewById(R.id.episodesWatchedTextView);
        }
    }
}
