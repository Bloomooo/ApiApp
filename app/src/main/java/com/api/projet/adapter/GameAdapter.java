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
import com.api.projet.entity.Player;
import com.api.projet.inter.PlayerInterfaction;

import java.util.List;

/**
 * GameAdapter est un adaptateur RecyclerView utilisé pour afficher une liste de joueurs dans un jeu.
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> implements PlayerInterfaction {
    /** Liste des joueurs */
    private List<Player> playerList;
    /** Contexte de l'application */
    private Context context;
    /** ViewHolder actuel */
    private ViewHolder holder;

    /**
     * Constructeur pour GameAdapter.
     * @param context Contexte de l'application.
     * @param playerList Liste des joueurs.
     */
    public GameAdapter(Context context, List<Player> playerList){
        this.context = context;
        this.playerList = playerList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Appelé par RecyclerView pour afficher les données à la position spécifiée.
     * @param holder Le ViewHolder qui doit être mis à jour pour représenter le contenu de l'élément à la position donnée dans l'ensemble de données.
     * @param position La position de l'élément dans l'ensemble de données de l'adaptateur.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.holder = holder;
        Player player = playerList.get(position);
        holder.nameTextViewPlayer.setText(player.getName());
        holder.pointPlayer.setText(String.valueOf(player.getScore()));
        if (player.getAnswer() != null && !player.getAnswer().isEmpty()) {
            holder.playerAnswerTextView.setText(player.getAnswer());
            holder.playerAnswerTextView.setVisibility(View.VISIBLE);
        } else {
            holder.playerAnswerTextView.setVisibility(View.GONE);
        }
        if(player.getImageBase64() != null){
            holder.photoImageView.setImageBitmap(player.getImageBase64());
        }else{
            holder.photoImageView.setImageResource(R.drawable.ic_menu_profil);
        }
        holder.itemView.setBackgroundColor(player.getColor());
    }

    /**
     * Retourne le nombre total d'éléments dans l'ensemble de données détenu par l'adaptateur.
     * @return Le nombre total d'éléments dans cet adaptateur.
     */
    @Override
    public int getItemCount() {
        return playerList.size();
    }

    /**
     * Définit les points du joueur.
     * @param point Le nombre de points à définir.
     */
    @Override
    public void setPoint(int point) {
        this.holder.pointPlayer.setText(String.valueOf(point));
    }

    /**
     * Définit la couleur de fond de l'élément de l'adaptateur.
     * @param color La couleur à définir.
     */
    @Override
    public void setBackgroundColor(int color) {
        this.holder.itemView.setBackgroundColor(color);
    }

    /**
     * Active la vue de la réponse du joueur avec le texte donné.
     * @param answer La réponse à afficher.
     */
    @Override
    public void enableAnswerViewText(String answer) {
        this.holder.playerAnswerTextView.setVisibility(View.VISIBLE);
        this.holder.playerAnswerTextView.setText(answer);
    }

    /**
     * Désactive la vue de la réponse du joueur.
     */
    @Override
    public void disableAnswerViewText() {
        this.holder.playerAnswerTextView.setVisibility(View.GONE);
    }

    /**
     * ViewHolder pour contenir les vues des éléments de l'adapter.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /** TextView pour afficher le nom du joueur */
        TextView nameTextViewPlayer;
        /** TextView pour afficher les points du joueur */
        TextView pointPlayer;
        /** TextView pour afficher la réponse du joueur */
        TextView playerAnswerTextView;
        /** ImageView pour afficher la photo du joueur */
        ImageView photoImageView;

        /**
         * Constructeur pour ViewHolder.
         * @param itemView La vue correspondant à la mise en page gonflée pour l'élément.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextViewPlayer = itemView.findViewById(R.id.nameTextViewPlayer);
            playerAnswerTextView = itemView.findViewById(R.id.tvPlayerAnswer);
            pointPlayer = itemView.findViewById(R.id.pointPlayer);
            pointPlayer.setVisibility(View.VISIBLE);
            photoImageView = itemView.findViewById(R.id.ivPlayerIcon);
        }
    }

    /**
     * Définit de nouvelles données pour la liste de joueurs et notifie les changements.
     * @param newData Nouvelles données de la liste de joueurs.
     */
    public void setData(List<Player> newData) {
        playerList.clear();
        playerList.addAll(newData);
        notifyDataSetChanged();
    }
}
