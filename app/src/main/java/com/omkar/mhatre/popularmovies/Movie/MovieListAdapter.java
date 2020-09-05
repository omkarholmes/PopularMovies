package com.omkar.mhatre.popularmovies.Movie;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.omkar.mhatre.popularmovies.R;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<Movie> inventoryList;
    private Activity activity;

    public MovieListAdapter(List<Movie> inventoryList, Activity activity) {
        this.inventoryList=inventoryList;
        this.activity=activity;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return  new MovieViewHolder(inflater.inflate(R.layout.layout_movie_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.instantiate(inventoryList.get(position), activity);
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }
}