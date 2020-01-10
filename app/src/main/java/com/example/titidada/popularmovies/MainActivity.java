package com.example.titidada.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.titidada.popularmovies.databinding.ActivityMainBinding;
import com.example.titidada.popularmovies.model.Movie;
import com.example.titidada.popularmovies.utilities.NetworkUtils;
import com.example.titidada.popularmovies.viewmodels.FavoriteMoviesViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityMainBinding mBinding;
    private MovieAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private int numColsInGrid = 2;

    private String sortBy = "popular";

    private static final String FAVORITES = "favorites";
    private boolean isShowingFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_by_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.sortBySpinner.setAdapter(adapter);
        mBinding.sortBySpinner.setOnItemSelectedListener(this);
        mBinding.movieRecyclerview.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, numColsInGrid);
        mBinding.movieRecyclerview.setLayoutManager(layoutManager);
        mAdapter = new MovieAdapter(this, "");
        mBinding.movieRecyclerview.setAdapter(mAdapter);

        makeQuery(sortBy);
    }

    private void makeQuery(String sortBy) {
        if(FAVORITES.equals(sortBy)){
            showJsonDataView();
            setupMoviesFromViewModel();
        }
        else {
            URL movieDbQueryUrl = NetworkUtils.buildUrl(sortBy);
            new MovieDbQueryTask().execute(movieDbQueryUrl);
        }
    }

    private void showJsonDataView() {
        mBinding.errorMessageDisplay.setVisibility(View.INVISIBLE);
        mBinding.movieRecyclerview.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mBinding.movieRecyclerview.setVisibility(View.INVISIBLE);
        mBinding.errorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sortBy = (String) parent.getItemAtPosition(position);
        if(FAVORITES.equals(sortBy)){
            isShowingFavorites = true;
        }
        else{
            isShowingFavorites = false;
        }
        makeQuery(sortBy);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class MovieDbQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL movieDbUrl = params[0];
            String movieSearchResults = null;
            try {
                movieSearchResults = NetworkUtils.getResponseFromHttpUrl(movieDbUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieSearchResults;
        }

        @Override
        protected void onPostExecute(String movieSearchResults) {

            if (movieSearchResults != null && !movieSearchResults.equals("")) {
                showJsonDataView();
                mAdapter.refreshMovies(movieSearchResults);
                mAdapter.notifyDataSetChanged();


            } else {
                showErrorMessage();
            }
        }
    }

    private void setupMoviesFromViewModel() {
        FavoriteMoviesViewModel viewModel = ViewModelProviders.of(this).get(FavoriteMoviesViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if(isShowingFavorites){
                    mAdapter.refreshMovies(movies);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

    }



}
