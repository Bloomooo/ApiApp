package com.api.projet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.api.projet.inter.GameInteraction;

import java.util.List;

/**
 * AnimeSuggestionAdapter est un adaptateur RecyclerView utilisé pour afficher une liste de suggestions d'animes.
 */
public class AnimeSuggestionAdapter extends RecyclerView.Adapter<AnimeSuggestionAdapter.ViewHolder> {
    /** Liste des suggestions d'animes */
    private List<String> suggestionList;
    /** Contexte de l'application */
    private Context context;
    /** Interaction de jeu pour gérer les réponses */
    private GameInteraction gameInteraction;

    /**
     * Constructeur pour AnimeSuggestionAdapter.
     * @param context Contexte de l'application.
     * @param suggestionList Liste des suggestions d'animes.
     * @param gameInteraction Interaction de jeu pour gérer les réponses.
     */
    public AnimeSuggestionAdapter(Context context, List<String> suggestionList, GameInteraction gameInteraction){
        this.context = context;
        this.suggestionList = suggestionList;
        this.gameInteraction = gameInteraction;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Appelé par RecyclerView pour afficher les données à la position spécifiée.
     * @param holder Le ViewHolder qui doit être mis à jour pour représenter le contenu de l'élément à la position donnée dans l'ensemble de données.
     * @param position La position de l'élément dans l'ensemble de données de l'adaptateur.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String anime = suggestionList.get(position);
        holder.suggestionTextView.setText(anime);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameInteraction.setAnswer(anime);
            }
        });
    }

    /**
     * Retourne le nombre total d'éléments dans l'ensemble de données détenu par l'adaptateur.
     * @return Le nombre total d'éléments dans cet adaptateur.
     */
    @Override
    public int getItemCount() {
        return suggestionList.size();
    }

    /**
     * ViewHolder pour contenir les vues des éléments de l'adapter.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /** TextView pour afficher la suggestion d'anime */
        TextView suggestionTextView;

        /**
         * Constructeur pour ViewHolder.
         * @param itemView La vue correspondant à la mise en page gonflée pour l'élément.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            suggestionTextView = itemView.findViewById(R.id.tvAnimeSuggestion);
        }
    }

    /**
     * Définit de nouvelles données pour la liste de suggestions d'animes et notifie les changements.
     * @param newData Nouvelles données de la liste de suggestions d'animes.
     */
    public void setData(List<String> newData) {
        suggestionList.clear();
        suggestionList.addAll(newData);
        notifyDataSetChanged();
    }
}
