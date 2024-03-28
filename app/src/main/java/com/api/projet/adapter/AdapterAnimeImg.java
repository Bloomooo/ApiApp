package com.api.projet.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.api.projet.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * AdapterAnimeImg est un adaptateur RecyclerView utilisé pour afficher une liste d'images.
 */
public class AdapterAnimeImg extends RecyclerView.Adapter<AdapterAnimeImg.ImageViewHolder> {
    /** Liste des URL d'images à afficher */
    private List<String> imageUrls;

    /**
     * Constructeur pour AdapterAnimeImg.
     * @param imageUrls Liste des URL d'images à afficher
     */
    public AdapterAnimeImg(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    /**
     * Gonfle la mise en page de l'élément et crée une nouvelle instance du ViewHolder.
     * @param parent Le ViewGroup dans lequel la nouvelle vue sera ajoutée après avoir été liée à une position d'adaptateur.
     * @param viewType Le type de vue de la nouvelle vue.
     * @return Un nouveau ViewHolder qui contient une vue du type de vue donné.
     */
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_img_anime, parent, false);
        return new ImageViewHolder(view);
    }

    /**
     * Appelé par RecyclerView pour afficher les données à la position spécifiée.
     * @param holder Le ViewHolder qui doit être mis à jour pour représenter le contenu de l'élément à la position donnée dans l'ensemble de données.
     * @param position La position de l'élément dans l'ensemble de données de l'adaptateur.
     */
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        Picasso.get().load(imageUrl).into(holder.imageView);
    }

    /**
     * Retourne le nombre total d'éléments dans l'ensemble de données détenu par l'adaptateur.
     * @return Le nombre total d'éléments dans cet adaptateur.
     */
    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    /**
     * ViewHolder pour contenir ImageView pour chaque élément dans RecyclerView.
     */
    static class ImageViewHolder extends RecyclerView.ViewHolder {
        /** ImageView pour afficher l'image */
        ImageView imageView;

        /**
         * Constructeur pour ImageViewHolder.
         * @param itemView L'objet View correspondant à la mise en page gonflée pour l'élément.
         */
        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    /**
     * Définit une nouvelle liste d'URL d'images à afficher.
     * @param newListeURL Nouvelle liste d'URL d'images à afficher.
     */
    public void setListeURL(List<String> newListeURL) {
        this.imageUrls.clear();
        this.imageUrls.addAll(newListeURL);
    }
}
