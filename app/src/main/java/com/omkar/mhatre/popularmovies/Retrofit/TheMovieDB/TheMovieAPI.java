package com.omkar.mhatre.popularmovies.Retrofit.TheMovieDB;

import com.google.gson.JsonObject;
import com.omkar.mhatre.popularmovies.Movie.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieAPI {

    @GET("movie/popular")
    Call<JsonObject> getPopularMovies();

    @GET("movie/{movie_id}/videos")
    Call<JsonObject> getVideos(@Path("movie_id")String id);

    @GET("movie/{movie_id}/reviews")
    Call<JsonObject> getReviews(@Path("movie_id")String id);


//    @GET("users/{username}")
//    Call<User> getUser(@Path("username") String username);
//
//    @GET("group/{id}/users")
//    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);
//
//    @POST("users/new")
//    Call<User> createUser(@Body User user);

}