package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    public static final long NEIGHBOUR_ID = 1;
    public static final NeighbourApiService.Observer OBSERVER = new NeighbourApiService.Observer() {
        @Override
        public void onChange() {

        }
    };

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void createNeighbourWithSuccess() {
        Neighbour newNeighbour = new Neighbour(13, "Mickael", "https://i.pravatar.cc/150?u=a042581f4e29026704d", "Saint-Pierre-du-Mont ; 5km","+33 6 86 57 90 14",  "Bonjour !Je souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !J'aime les jeux de cartes tels la belote et le tarot..");
        service.createNeighbour(newNeighbour);
        assertTrue(service.getNeighbours().contains(newNeighbour));
    }

    @Test
    public void toggleFavoriteWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);
        Boolean defaultFavorite = neighbour.isFavorite();
        service.toggleFavorite(neighbour);
        assertNotEquals(defaultFavorite,neighbour.isFavorite());
        service.toggleFavorite(neighbour);
        assertEquals(defaultFavorite,neighbour.isFavorite());
    }

    @Test
    public void getNeighbourWithSuccess() {
        Neighbour neighbour = service.getNeighbour(NEIGHBOUR_ID);
        assertEquals(NEIGHBOUR_ID, neighbour.getId());
    }

    @Test
    public void addObserverWithSuccess() {
        service.addObserver(OBSERVER);
        assertTrue(service.getObservers().contains(OBSERVER));
    }

    @Test
    public void removeObserverWithSuccess() {
        service.removeObserver(OBSERVER);
        assertFalse(service.getObservers().contains(OBSERVER));
    }

}
