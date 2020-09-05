package com.omkar.mhatre.popularmovies.Retrofit.TheMovieDB;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.omkar.mhatre.popularmovies.MainActivity;
import com.omkar.mhatre.popularmovies.Movie.Movie;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omkar.mhatre.popularmovies.Video.ReviewFeedbacks;
import com.omkar.mhatre.popularmovies.Video.VideoTrailers;

import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheMovieController {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String POSTER_URL = "https://image.tmdb.org/t/p/w500/";
    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    public static final String VIMEO_URL = "https://vimeo.com/";
    public static final String API_KEY = "4e44d9029b1270a757cddc766a1bcb63";

    OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .addQueryParameter("language", "en-IN")
                    .addQueryParameter("page", "1")
                    .build();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    });

    public void fetchMovies(MainActivity mainActivity) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        TheMovieAPI movieAPI = retrofit.create(TheMovieAPI.class);


        Call<JsonObject> call = movieAPI.getPopularMovies();
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    JsonObject body = response.body();
                    System.out.println(body);
                    Type listType = new TypeToken<ArrayList<Movie>>(){}.getType();
                    List<Movie> movieList = new Gson().fromJson(body.get("results"),listType);
                    movieList.forEach(movie -> System.out.println(movie.getTitle()));
                    mainActivity.setList(movieList);
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    public void fetchVideos(String id)
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        TheMovieAPI movieAPI = retrofit.create(TheMovieAPI.class);


        Call<JsonObject> call = movieAPI.getVideos(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    JsonObject body = response.body();
                    Type listType = new TypeToken<ArrayList<Movie>>(){}.getType();
                    List<VideoTrailers> videoList = new Gson().fromJson(body.get("results"),listType);

                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }


    public void fetchReviews(String id)
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        TheMovieAPI movieAPI = retrofit.create(TheMovieAPI.class);


        Call<JsonObject> call = movieAPI.getReviews(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    JsonObject body = response.body();
                    Type listType = new TypeToken<ArrayList<Movie>>(){}.getType();
                    List<ReviewFeedbacks> reviewList = new Gson().fromJson(body.get("results"),listType);

                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

}
