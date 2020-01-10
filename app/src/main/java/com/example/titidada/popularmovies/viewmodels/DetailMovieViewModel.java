package com.example.titidada.popularmovies.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.titidada.popularmovies.database.AppDatabase;
import com.example.titidada.popularmovies.model.Movie;

public class DetailMovieViewModel extends ViewModel {
    private LiveData<Movie> movie;

    public DetailMovieViewModel(AppDatabase db, String movieId) {
        this.movie = db.moviesDao().getMovie(movieId);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
