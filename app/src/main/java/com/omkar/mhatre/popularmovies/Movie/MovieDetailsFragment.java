package com.omkar.mhatre.popularmovies.Movie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omkar.mhatre.popularmovies.AsynLoader.AsyncLoader;
import com.omkar.mhatre.popularmovies.AsynLoader.SubscriptionBlock;
import com.omkar.mhatre.popularmovies.MainActivity;
import com.omkar.mhatre.popularmovies.R;
import com.omkar.mhatre.popularmovies.Retrofit.TheMovieDB.TheMovieController;
import com.omkar.mhatre.popularmovies.Video.ReviewFeedbacks;
import com.omkar.mhatre.popularmovies.Video.VideoTrailers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsFragment extends Fragment {

    @BindView(R.id.trailerRecyclerView)
    RecyclerView trailerRecyclerView;
    @BindView(R.id.reviewRecyclerView)
    RecyclerView reviewRecyclerView;

    @BindView(R.id.moviePoster)
    ImageView moviePoster;
    @BindView(R.id.movieName)
    TextView movieName;
    @BindView(R.id.movieRating) TextView movieRating;
    @BindView(R.id.ratingsBar)
    ProgressBar ratingsBar;
    //@BindView(R.id.movieGenre) TextView movieGenre;
    @BindView(R.id.movieOverview) TextView movieOverview;

    Movie movie;
    Activity activity;

    List<VideoTrailers> videoTrailers = new ArrayList<>();
    List<ReviewFeedbacks> reviewFeedBacks = new ArrayList<>();


    public MovieDetailsFragment(Movie movie, MainActivity mainActivity) {
        this.movie=movie;
        this.activity=mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_movie_details, container, false);
        ButterKnife.bind(this,rootView);
        setupDetailsView();

        setupTrailerRecyclerViews(container.getContext());



        return rootView;
    }


    private void setupTrailerRecyclerViews(Context context) {

        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        trailerRecyclerView.setAdapter(new TrailerListAdapter(videoTrailers));

    }

    private void setupFeedbackRecyclerViews(Context context) {

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        reviewRecyclerView.setAdapter(new ReviewListAdapter(reviewFeedBacks));

    }

    @SuppressLint("SetTextI18n")
    private void setupDetailsView() {

        Picasso.get().load(TheMovieController.POSTER_URL+movie.getPosterPath()).fit().into(moviePoster);

        movieName.setText(movie.getTitle());
        movieRating.setText(""+(int)(movie.getVoteAverage()*10));
        ratingsBar.setProgress(Integer.parseInt(""+(int)(movie.getVoteAverage()*10)));
        movieOverview.setText(movie.getOverview());


        AsyncLoader.load(new SubscriptionBlock() {
            @Override
            public void subscribe() throws Exception {
                TheMovieController controller = new TheMovieController();
                controller.fetchVideos(""+movie.getId());
            }
        });

    }

    private class TrailerListAdapter extends RecyclerView.Adapter<TrailerViewHolder> {
        List<VideoTrailers> videoTrailers;

        public TrailerListAdapter(List<VideoTrailers> videoTrailers) {
            this.videoTrailers=videoTrailers;
        }

        @NonNull
        @Override
        public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return videoTrailers.size();
        }
    }

    private class TrailerViewHolder extends RecyclerView.ViewHolder {
        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class ReviewListAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

        List<ReviewFeedbacks> reviewFeedBacks;

        public ReviewListAdapter(List<ReviewFeedbacks> reviewFeedBacks) {
            this.reviewFeedBacks=reviewFeedBacks;
        }

        @NonNull
        @Override
        public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return reviewFeedBacks.size();
        }
    }

    private class ReviewViewHolder extends RecyclerView.ViewHolder {
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
