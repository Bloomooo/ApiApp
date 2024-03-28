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

/**
 * AdapterList est un adaptateur RecyclerView utilisé pour afficher une liste d'animes.
 */
public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder> {
    /** Liste des animes à afficher */
    private List<Anime> animeList;
    /** Contexte de l'application */
    private Context context;

    /**
     * Constructeur pour AdapterList.
     * @param context Contexte de l'application.
     * @param animeList Liste des animes à afficher.
     */
    public AdapterList(Context context, List<Anime> animeList) {
        this.context = context;
        this.animeList = animeList;
    }

    /**
     * Gonfle la mise en page de l'élément et crée une nouvelle instance du ViewHolder.
     * @param parent Le ViewGroup dans lequel la nouvelle vue sera ajoutée après avoir été liée à une position d'adaptateur.
     * @param viewType Le type de vue de la nouvelle vue.
     * @return Un nouveau ViewHolder qui contient une vue du type de vue donné.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listanime_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Appelé par RecyclerView pour afficher les données à la position spécifiée.
     * @param holder Le ViewHolder qui doit être mis à jour pour représenter le contenu de l'élément à la position donnée dans l'ensemble de données.
     * @param position La position de l'élément dans l'ensemble de données de l'adaptateur.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Anime anime = animeList.get(position);

        holder.titleTextView.setText(anime.getTitle());
        String statutFirstLetterCapitalized = String.valueOf(Character.toUpperCase(String.valueOf(anime.getStatus()).charAt(0)));
        String statutNotFirstLetter = String.valueOf(anime.getStatus().substring(1)).replace("_"," ");

        holder.statusTextView.setText(statutFirstLetterCapitalized + statutNotFirstLetter);
        holder.scoreTextView.setText(anime.getScore()+ "⭐");
        String nbepString = String.valueOf(anime.getEpWatch());
        String txtWatched;
        if(anime.getStatus().equals("completed")){
            txtWatched = nbepString + " / " + nbepString;
        } else{
            txtWatched = nbepString + " / ?";
        }
        holder.episodesWatchedTextView.setText(txtWatched);

        Picasso.get()
                .load(anime.getImageUri())
                .into(holder.animeImageView);
    }

    /**
     * Retourne le nombre total d'éléments dans l'ensemble de données détenu par l'adaptateur.
     * @return Le nombre total d'éléments dans cet adaptateur.
     */
    @Override
    public int getItemCount() {
        return animeList.size();
    }

    /**
     * ViewHolder pour contenir les vues des éléments de l'adapter.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /** ImageView pour afficher l'image de l'anime */
        ImageView animeImageView;
        /** TextView pour afficher le titre de l'anime */
        TextView titleTextView;
        /** TextView pour afficher le statut de l'anime */
        TextView statusTextView;
        /** TextView pour afficher le score de l'anime */
        TextView scoreTextView;
        /** TextView pour afficher le nombre d'épisodes regardés de l'anime */
        TextView episodesWatchedTextView;

        /**
         * Constructeur pour ViewHolder.
         * @param itemView La vue correspondant à la mise en page gonflée pour l'élément.
         */
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
