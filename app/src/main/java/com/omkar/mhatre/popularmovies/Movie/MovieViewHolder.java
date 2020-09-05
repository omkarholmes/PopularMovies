package com.omkar.mhatre.popularmovies.Movie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.omkar.mhatre.popularmovies.MainActivity;
import com.omkar.mhatre.popularmovies.R;
import com.omkar.mhatre.popularmovies.Retrofit.TheMovieDB.TheMovieAPI;
import com.omkar.mhatre.popularmovies.Retrofit.TheMovieDB.TheMovieController;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.moviePoster) ImageView moviePoster;
    @BindView(R.id.movieName) TextView movieName;
    @BindView(R.id.movieRating) TextView movieRating;
    @BindView(R.id.ratingsBar) ProgressBar ratingsBar;
    @BindView(R.id.movieGenre) TextView movieGenre;
    @BindView(R.id.movieOverview) TextView movieOverview;


    private Movie movie;
    private Activity activity;
    private View view;

    public MovieViewHolder(View itemView) {
        super(itemView);
        this.view=itemView;
        ButterKnife.bind(itemView,itemView);

    }

    public void instantiate(Movie movie, Activity activity) {
        this.movie=movie;
        this.activity=activity;
        initialiseView();
        setupView();
    }

    private void initialiseView() {
        moviePoster = view.findViewById(R.id.moviePoster);
        movieName = view.findViewById(R.id.movieName);
        movieRating = view.findViewById(R.id.movieRating);
        ratingsBar = view.findViewById(R.id.ratingsBar);
        movieGenre = view.findViewById(R.id.movieGenre);
        movieOverview = view.findViewById(R.id.movieOverview);
        view.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void setupView() {

        Picasso.get().load(TheMovieController.POSTER_URL+movie.getPosterPath()).fit().into(moviePoster);

        movieName.setText(movie.getTitle());
        movieRating.setText(""+(int)(movie.getVoteAverage()*10));
        ratingsBar.setProgress(Integer.parseInt(""+(int)(movie.getVoteAverage()*10)));
        movieOverview.setText(movie.getOverview());

    }


    @Override
    public void onClick(View v) {
        ((MainActivity)activity).loadItemDetailsFragment(movie);
    }
}
