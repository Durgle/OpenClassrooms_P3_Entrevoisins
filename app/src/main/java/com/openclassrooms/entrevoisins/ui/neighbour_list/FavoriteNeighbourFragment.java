package com.openclassrooms.entrevoisins.ui.neighbour_list;

public class FavoriteNeighbourFragment extends NeighbourFragment {

    /**
     * Create and return a new instance
     * @return @{@link FavoriteNeighbourFragment}
     */
    public static FavoriteNeighbourFragment newInstance() {
        return new FavoriteNeighbourFragment();
    }

    protected void getNeighbours() {
        mNeighbours = mApiService.getFavoriteNeighbours();
    }

}
