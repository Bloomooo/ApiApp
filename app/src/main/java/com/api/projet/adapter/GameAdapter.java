package com.api.projet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.api.projet.entity.Player;
import com.api.projet.entity.User;
import com.api.projet.inter.PlayerInterfaction;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> implements PlayerInterfaction {
    private List<Player> playerList;

    private Context context;

    private ViewHolder holder;

    public GameAdapter(Context context, List<Player> playerList){
        this.context = context;
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public com.api.projet.adapter.GameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        return new com.api.projet.adapter.GameAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.api.projet.adapter.GameAdapter.ViewHolder holder, int position) {
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

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    @Override
    public void setPoint(int point) {
        this.holder.pointPlayer.setText(String.valueOf(point));;
    }

    @Override
    public void setBackgroundColor(int color) {
        this.holder.itemView.setBackgroundColor(color);
    }

    @Override
    public void enableAnswerViewText(String answer) {
        this.holder.playerAnswerTextView.setVisibility(View.VISIBLE);
        this.holder.playerAnswerTextView.setText(answer);
    }

    @Override
    public void disableAnswerViewText() {
        this.holder.playerAnswerTextView.setVisibility(View.GONE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextViewPlayer;
        TextView pointPlayer;

        TextView playerAnswerTextView;

        ImageView photoImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextViewPlayer = itemView.findViewById(R.id.nameTextViewPlayer);
            playerAnswerTextView = itemView.findViewById(R.id.tvPlayerAnswer);
            pointPlayer = itemView.findViewById(R.id.pointPlayer);
            pointPlayer.setVisibility(View.VISIBLE);
            photoImageView = itemView.findViewById(R.id.ivPlayerIcon);
        }
    }

    public void setData(List<Player> newData) {
        playerList.clear();
        playerList.addAll(newData);
        notifyDataSetChanged();
    }
}
