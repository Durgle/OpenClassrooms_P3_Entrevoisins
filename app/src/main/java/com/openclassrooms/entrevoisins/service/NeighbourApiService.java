package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Get favorite Neighbours
     * @return {@link List}
     */
    List<Neighbour> getFavoriteNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour Neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour Neighbour
     */
    void createNeighbour(Neighbour neighbour);

    /**
     * Get a neighbour
     * @param neighbourId Neighbour Id
     * @return Neighbour
     */
    Neighbour getNeighbour(long neighbourId);

    /**
     * Toggle favorite
     * @param neighbour Neighbour
     */
    void toggleFavorite(Neighbour neighbour);

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    List<Observer> getObservers();

    interface Observer {
        void onChange();
    }
}
