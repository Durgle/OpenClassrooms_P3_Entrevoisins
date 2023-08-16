package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.slugify.Slugify;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.view.View;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.databinding.ActivityNeighbourDetailBinding;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

public class NeighbourDetailActivity extends AppCompatActivity {

    private ActivityNeighbourDetailBinding binding;
    private NeighbourApiService mApiService;
    private Neighbour mNeighbour;
    private static final String NEIGHBOUR_ID = "neighbour_id";

    public static void startActivity(Context context, long neighbourId) {
        Intent intent = new Intent(context,NeighbourDetailActivity.class);
        intent.putExtra(NeighbourDetailActivity.NEIGHBOUR_ID, neighbourId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getNeighbour();
        if(mNeighbour == null) {
            finish();
        }

        binding = ActivityNeighbourDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.fillData();
        this.configureToolbar();
        this.configureFab();
    }

    private void fillData(){

        if(mNeighbour.isFavorite()){
            binding.addFavorites.setColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorYellow,null));
        }

      Glide.with(binding.bgImage.getContext())
                .load(mNeighbour.getAvatarUrl())
                .into(binding.bgImage);

        binding.content.username.setText(mNeighbour.getName());
        binding.content.address.setText(mNeighbour.getAddress());
        binding.content.phoneNumber.setText(mNeighbour.getPhoneNumber());
        binding.content.facebook.setText(getString(R.string.facebook_url, Slugify.builder().build().slugify(mNeighbour.getName())));
        binding.content.description.setText(mNeighbour.getAboutMe());
    }

    private void getNeighbour() {

        mApiService = DI.getNeighbourApiService();
        Intent intent = getIntent();
        if(intent.hasExtra(NEIGHBOUR_ID)){
            long id = intent.getLongExtra(NEIGHBOUR_ID,-1);
            mNeighbour = mApiService.getNeighbour(id);
        }
    }

    private void configureToolbar() {

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        // Enable back button
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(mNeighbour.getName());
    }

    private void configureFab() {

        FloatingActionButton fab = binding.addFavorites;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiService.toggleFavorite(mNeighbour);
                if(mNeighbour.isFavorite()){
                    Snackbar.make(view,  R.string.added_to_favorite, Snackbar.LENGTH_LONG).show();
                    binding.addFavorites.setColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorYellow,null));
                } else {
                    Snackbar.make(view, R.string.removed_from_favorite, Snackbar.LENGTH_LONG).show();
                    binding.addFavorites.clearColorFilter();
                }
            }
        });
    }
}