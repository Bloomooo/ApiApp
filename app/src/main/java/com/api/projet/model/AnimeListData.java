package com.api.projet.model;

import com.api.projet.entity.Anime;
import com.api.projet.inter.AnimeListObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente un modèle pour stocker et gérer une liste d'anime.
 */
public class AnimeListData {

    // Liste des anime
    private static List<Anime> animeList = new ArrayList<>();

    // Liste des observateurs
    private static List<AnimeListObserver> observers = new ArrayList<>();

    // Liste des noms d'anime pour le lobby
    private static List<String> animeListLobby = new ArrayList<>();

    /**
     * Ajoute un observateur à la liste des observateurs.
     * @param observer L'observateur à ajouter.
     */
    public static void addObserver(AnimeListObserver observer) {
        observers.add(observer);
    }

    /**
     * Supprime un observateur de la liste des observateurs.
     * @param observer L'observateur à supprimer.
     */
    public static void removeObserver(AnimeListObserver observer) {
        observers.remove(observer);
    }

    /**
     * Met à jour la liste d'anime et notifie tous les observateurs.
     * @param newAnimeList La nouvelle liste d'anime.
     */
    public static void updateAnimeList(List<Anime> newAnimeList) {
        animeList = new ArrayList<>(newAnimeList);
        notifyObservers();
    }

    /**
     * Notifie tous les observateurs de la mise à jour de la liste d'anime.
     */
    private static void notifyObservers() {
        for (AnimeListObserver observer : observers) {
            observer.onAnimeListUpdated(animeList);
        }
    }

    /**
     * Renvoie la liste d'anime actuelle.
     * @return La liste d'anime.
     */
    public static List<Anime> getAnimeList() {
        return new ArrayList<>(animeList);
    }

    /**
     * Renvoie la liste des noms d'anime pour le lobby.
     * @return La liste des noms d'anime pour le lobby.
     */
    public static List<String> getAnimeListLobby(){
        return new ArrayList<>(animeListLobby);
    }

    /**
     * Met à jour la liste des noms d'anime pour le lobby.
     * @param listLobby La nouvelle liste des noms d'anime pour le lobby.
     */
    public static void setAnimeListLobby(List<String> listLobby){
        animeListLobby = new ArrayList<>(listLobby);
    }
}
