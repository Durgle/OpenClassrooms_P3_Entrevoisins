package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;


public class NeighbourFragment extends Fragment implements NeighbourApiService.Observer {

    protected NeighbourApiService mApiService;
    protected List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;
    protected MyNeighbourRecyclerViewAdapter mAdapter;

    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance() {
        NeighbourFragment fragment = new NeighbourFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        initList();
        return view;
    }

    protected void getNeighbours() {
        mNeighbours = mApiService.getNeighbours();
    }

    /**
     * Init the List of neighbours
     */
    private void initList() {

        this.getNeighbours();
        mAdapter = new MyNeighbourRecyclerViewAdapter(mNeighbours,new MyNeighbourRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int position) {
                Neighbour neighbour = mNeighbours.get(position);
                mApiService.deleteNeighbour(neighbour);
            }

            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(requireContext(),NeighbourDetailActivity.class);
                Neighbour neighbour = mNeighbours.get(position);
                intent.putExtra(NeighbourDetailActivity.NEIGHBOUR_ID, neighbour.getId());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.getNeighbours();
        mAdapter.refreshList(mNeighbours);
        mApiService.addObserver(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onChange() {
        getNeighbours();
        mAdapter.refreshList(mNeighbours);
    }

    @Override
    public void onPause() {

        super.onPause();
        mApiService.removeObserver(this);
    }
}
