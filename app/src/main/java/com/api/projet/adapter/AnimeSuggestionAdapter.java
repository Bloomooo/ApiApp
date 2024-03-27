package com.api.projet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.api.projet.entity.Player;
import com.api.projet.entity.User;
import com.api.projet.inter.GameInteraction;

import java.util.List;

public class AnimeSuggestionAdapter extends RecyclerView.Adapter<AnimeSuggestionAdapter.ViewHolder> {
    private List<String> suggestionList;

    private Context context;

    private GameInteraction gameInteraction;

    public AnimeSuggestionAdapter(Context context, List<String> suggestionList, GameInteraction gameInteraction){
        this.context = context;
        this.suggestionList = suggestionList;
        this.gameInteraction = gameInteraction;
    }

    @NonNull
    @Override
    public com.api.projet.adapter.AnimeSuggestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_item, parent, false);
        return new com.api.projet.adapter.AnimeSuggestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.api.projet.adapter.AnimeSuggestionAdapter.ViewHolder holder, int position) {
        String anime = suggestionList.get(position);
        holder.suggestionTextView.setText(anime);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameInteraction.setAnswer(anime);
            }
        });
    }

    @Override
    public int getItemCount() {
        return suggestionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView suggestionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            suggestionTextView = itemView.findViewById(R.id.tvAnimeSuggestion);
        }
    }

    public void setData(List<String> newData) {
        suggestionList.clear();
        suggestionList.addAll(newData);
        notifyDataSetChanged();
    }
}
