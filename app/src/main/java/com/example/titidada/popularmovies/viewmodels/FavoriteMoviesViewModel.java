package com.example.titidada.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.titidada.popularmovies.database.AppDatabase;
import com.example.titidada.popularmovies.model.Movie;

import java.util.List;

public class FavoriteMoviesViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public FavoriteMoviesViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        movies = database.moviesDao().getAllFavoriteMovies();

    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
