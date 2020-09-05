package com.omkar.mhatre.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.omkar.mhatre.popularmovies.AsynLoader.AsyncLoader;
import com.omkar.mhatre.popularmovies.AsynLoader.SubscriptionBlock;
import com.omkar.mhatre.popularmovies.Movie.Movie;
import com.omkar.mhatre.popularmovies.Movie.MovieDetailsFragment;
import com.omkar.mhatre.popularmovies.Movie.MovieListAdapter;
import com.omkar.mhatre.popularmovies.Retrofit.TheMovieDB.TheMovieController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.fragment)
    FrameLayout fragment;

    private List<Movie> movieList =new ArrayList<>();

    MovieListAdapter adapter;
    private boolean fragView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        AsyncLoader.load(new SubscriptionBlock() {
            @Override
            public void subscribe() throws Exception {
                TheMovieController controller = new TheMovieController();
                controller.fetchMovies(MainActivity.this);
            }
        });

    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        adapter=new MovieListAdapter(movieList,this);
        recyclerView.setAdapter(adapter);
    }

    public void setList(List<Movie> movieList) {
        this.movieList=movieList;
        setupRecyclerView();
    }


    public void loadItemDetailsFragment(Movie movie) {
        fragmentLoad(true);
        setTitle(movie.getTitle());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, new MovieDetailsFragment(movie,this));
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

    }

    public void fragmentLoad(boolean b) {
        fragView = b;
        fragment.setVisibility(b==true ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fragView){
            fragmentLoad(false);
            setTitle(R.string.app_name);
        }
    }

}