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

import java.util.List;

/**
 * PreLobbyAdapter est un adaptateur RecyclerView utilisé pour afficher une liste de joueurs avant le lancement du lobby.
 */
public class PreLobbyAdapter extends RecyclerView.Adapter<PreLobbyAdapter.ViewHolder> {
    /** Liste des joueurs */
    private List<Player> playerList;
    /** Contexte de l'application */
    private Context context;

    /**
     * Constructeur pour PreLobbyAdapter.
     * @param context Contexte de l'application.
     * @param playerList Liste des joueurs.
     */
    public PreLobbyAdapter(Context context, List<Player> playerList){
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
        Player player = playerList.get(position);
        holder.nameTextViewPlayer.setText(player.getName());
        if(player.getImageBase64() != null){
            holder.photoImageView.setImageBitmap(player.getImageBase64());
        } else {
            holder.photoImageView.setImageResource(R.drawable.ic_menu_profil);
        }
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
     * ViewHolder pour contenir les vues des éléments de l'adapter.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /** TextView pour afficher le nom du joueur */
        TextView nameTextViewPlayer;
        /** ImageView pour afficher la photo du joueur */
        ImageView photoImageView;

        /**
         * Constructeur pour ViewHolder.
         * @param itemView La vue correspondant à la mise en page gonflée pour l'élément.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextViewPlayer = itemView.findViewById(R.id.nameTextViewPlayer);
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
