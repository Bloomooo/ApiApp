package com.api.projet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.api.projet.entity.Anime;
import com.api.projet.entity.Lobby;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LobbyAdapter extends RecyclerView.Adapter<LobbyAdapter.ViewHolder> {

    private List<Lobby> lobbyList;

    private Context context;

    public LobbyAdapter(Context context, List<Lobby> lobbyList){
        this.context = context;
        this.lobbyList = lobbyList;
    }

    @NonNull
    @Override
    public LobbyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lobby_item, parent, false);
        return new LobbyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LobbyAdapter.ViewHolder holder, int position) {
        Lobby lobby = lobbyList.get(position);
        Log.i("ggez",lobby.getName()+" "+lobby.getAuthor());
        holder.authorTextViewLobby.setText(lobby.getAuthor());
        holder.nameTextViewLobby.setText(lobby.getName());

    }

    @Override
    public int getItemCount() {
        return lobbyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextViewLobby;
        TextView authorTextViewLobby;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextViewLobby = itemView.findViewById(R.id.titleTextViewLobby);
            authorTextViewLobby = itemView.findViewById(R.id.authorTextViewLobby);
        }
    }

    public void setData(List<Lobby> newData) {
        lobbyList.clear();
        lobbyList.addAll(newData);
        notifyDataSetChanged();
    }

}
