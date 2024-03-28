package com.api.projet.itemDecoration;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ItemDecoration pour ajouter des marges uniformes autour des éléments de RecyclerView, avec une marge supplémentaire en haut.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;

    /**
     * Constructeur pour créer un SpaceItemDecoration avec une marge spécifiée.
     * @param space La taille de la marge à appliquer (en pixels).
     */
    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    /**
     * Ajoute les décalages d'élément pour chaque élément de RecyclerView, en définissant les marges appropriées.
     * @param outRect Le rectangle de décalage de l'élément.
     * @param view La vue de l'élément.
     * @param parent Le RecyclerView parent.
     * @param state L'état actuel de RecyclerView.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}
