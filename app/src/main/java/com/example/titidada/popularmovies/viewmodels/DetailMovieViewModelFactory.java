package com.example.titidada.popularmovies.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.titidada.popularmovies.database.AppDatabase;

public class DetailMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private AppDatabase mDb;
    private String movieId;

    public DetailMovieViewModelFactory(AppDatabase mDb, String movieId) {
        this.mDb = mDb;
        this.movieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailMovieViewModel(mDb, movieId);
    }
}
