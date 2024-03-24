package com.api.projet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.api.projet.entity.Lobby;
import com.api.projet.inter.IntentInterface;
import com.api.projet.network.client.ClientSocket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;


public class LobbyAdapter extends RecyclerView.Adapter<LobbyAdapter.ViewHolder> {
        private List<Lobby> lobbyList;
        private Context context;
        private IntentInterface intent;

        public LobbyAdapter(Context context, List<Lobby> lobbyList, IntentInterface intent) {
            this.context = context;
            this.lobbyList = lobbyList;
            this.intent = intent;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lobby_item, parent, false);
            return new ViewHolder(view, intent);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Lobby lobby = lobbyList.get(position);
            holder.bind(lobby);
        }

        @Override
        public int getItemCount() {
            return lobbyList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameTextViewLobby;
            TextView authorTextViewLobby;
            private IntentInterface intent;
            private String lobbyId;

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

            public void bind(Lobby lobby) {
                lobbyId = lobby.getId();
                authorTextViewLobby.setText(lobby.getAuthor());
                nameTextViewLobby.setText(lobby.getName());
            }


        }

    public void setData(List<Lobby> newData) {
        lobbyList.clear();
        lobbyList.addAll(newData);
        notifyDataSetChanged();
    }

    public void addLobby(Lobby lobby) {
            if(!lobbyList.contains(lobby)){
                lobbyList.add(lobby);
            }
            notifyDataSetChanged();
    }




}
