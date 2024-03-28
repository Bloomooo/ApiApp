package com.api.projet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.api.projet.entity.Lobby;
import com.api.projet.inter.IntentInterface;

import java.util.List;

/**
 * LobbyAdapter est un adaptateur RecyclerView utilisé pour afficher une liste de lobby.
 */
public class LobbyAdapter extends RecyclerView.Adapter<LobbyAdapter.ViewHolder> {
    /** Liste des lobbies */
    private List<Lobby> lobbyList;
    /** Contexte de l'application */
    private Context context;
    /** Interface d'intention pour gérer les actions */
    private IntentInterface intent;

    /**
     * Constructeur pour LobbyAdapter.
     * @param context Contexte de l'application.
     * @param lobbyList Liste des lobbies.
     * @param intent Interface d'intention pour gérer les actions.
     */
    public LobbyAdapter(Context context, List<Lobby> lobbyList, IntentInterface intent) {
        this.context = context;
        this.lobbyList = lobbyList;
        this.intent = intent;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lobby_item, parent, false);
        return new ViewHolder(view, intent);
    }

    /**
     * Appelé par RecyclerView pour afficher les données à la position spécifiée.
     * @param holder Le ViewHolder qui doit être mis à jour pour représenter le contenu de l'élément à la position donnée dans l'ensemble de données.
     * @param position La position de l'élément dans l'ensemble de données de l'adaptateur.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lobby lobby = lobbyList.get(position);
        holder.bind(lobby);
    }

    /**
     * Retourne le nombre total d'éléments dans l'ensemble de données détenu par l'adaptateur.
     * @return Le nombre total d'éléments dans cet adaptateur.
     */
    @Override
    public int getItemCount() {
        return lobbyList.size();
    }

    /**
     * ViewHolder pour contenir les vues des éléments de l'adapter.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /** TextView pour afficher le nom du lobby */
        TextView nameTextViewLobby;
        /** TextView pour afficher l'auteur du lobby */
        TextView authorTextViewLobby;
        /** Interface d'intention pour gérer les actions */
        private IntentInterface intent;
        /** Identifiant du lobby */
        private String lobbyId;

        /**
         * Constructeur pour ViewHolder.
         * @param itemView La vue correspondant à la mise en page gonflée pour l'élément.
         * @param intent Interface d'intention pour gérer les actions.
         */
        public ViewHolder(View itemView, IntentInterface intent) {
            super(itemView);
            this.intent = intent;
            nameTextViewLobby = itemView.findViewById(R.id.titleTextViewLobby);
            authorTextViewLobby = itemView.findViewById(R.id.authorTextViewLobby);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lobbyId != null) {
                        intent.doIntent(lobbyId);
                    }
                }
            });
        }

        /**
         * Associe les données du lobby à la vue correspondante dans le ViewHolder.
         * @param lobby Le lobby à lier.
         */
        public void bind(Lobby lobby) {
            lobbyId = lobby.getId();
            authorTextViewLobby.setText(lobby.getAuthor());
            nameTextViewLobby.setText(lobby.getName());
        }
    }

    /**
     * Définit de nouvelles données pour la liste de lobbies et notifie les changements.
     * @param newData Nouvelles données de la liste de lobbies.
     */
    public void setData(List<Lobby> newData) {
        lobbyList.clear();
        lobbyList.addAll(newData);
        notifyDataSetChanged();
    }

    /**
     * Ajoute un nouveau lobby à la liste si celui-ci n'existe pas déjà.
     * @param lobby Le lobby à ajouter.
     */
    public void addLobby(Lobby lobby) {
        if (!lobbyList.contains(lobby)) {
            lobbyList.add(lobby);
        }
        notifyDataSetChanged();
    }

}
