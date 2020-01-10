package com.example.titidada.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.titidada.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface FavoriteMoviesDao {

    @Query("SELECT * FROM favorite_movies")
    LiveData<List<Movie>> getAllFavoriteMovies();

    @Insert
    void addToFavorites(Movie movie);

    @Delete
    void deleteFavorites(Movie movie);

    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    LiveData<Movie> getMovie(String id);
}
