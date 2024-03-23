package com.api.projet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.api.projet.entity.Lobby;
import com.api.projet.entity.Player;
import com.api.projet.inter.IntentInterface;

import java.util.List;

public class PreLobbyAdapter extends RecyclerView.Adapter<PreLobbyAdapter.ViewHolder> {
        private List<Player> playerList;

        private Context context;



        public PreLobbyAdapter(Context context, List<Player> playerList){
            this.context = context;
            this.playerList = playerList;
        }

        @NonNull
        @Override
        public com.api.projet.adapter.PreLobbyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
            return new com.api.projet.adapter.PreLobbyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.api.projet.adapter.PreLobbyAdapter.ViewHolder holder, int position) {
            Player player = playerList.get(position);
            holder.nameTextViewPlayer.setText(player.getName());

        }

        @Override
        public int getItemCount() {
            return playerList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameTextViewPlayer;

            public ViewHolder(View itemView) {
                super(itemView);
                nameTextViewPlayer = itemView.findViewById(R.id.nameTextViewPlayer);
            }
        }

        public void setData(List<Player> newData) {
            playerList.clear();
            playerList.addAll(newData);
            notifyDataSetChanged();
        }

}
