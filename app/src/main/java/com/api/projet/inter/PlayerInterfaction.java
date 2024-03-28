package com.api.projet.inter;

import com.api.projet.adapter.GameAdapter;

/**
 * Interface définissant les interactions possibles avec un joueur dans un jeu.
 */
public interface PlayerInterfaction {

    /**
     * Définit le point du joueur.
     * @param point Le point à définir.
     */
    void setPoint(int point);

    /**
     * Définit la couleur de fond de l'interface du joueur.
     * @param color La couleur à définir.
     */
    void setBackgroundColor(int color);

    /**
     * Active l'affichage du texte de réponse du joueur avec le texte spécifié.
     * @param answer Le texte de réponse à afficher.
     */
    void enableAnswerViewText(String answer);

    /**
     * Désactive l'affichage du texte de réponse du joueur.
     */
    void disableAnswerViewText();
}
