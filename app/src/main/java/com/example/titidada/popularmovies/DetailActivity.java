package com.example.titidada.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.titidada.popularmovies.database.AppDatabase;
import com.example.titidada.popularmovies.databinding.ActivityDetailBinding;
import com.example.titidada.popularmovies.model.Movie;
import com.example.titidada.popularmovies.utilities.CommonUtils;
import com.example.titidada.popularmovies.utilities.NetworkUtils;
import com.example.titidada.popularmovies.viewmodels.DetailMovieViewModel;
import com.example.titidada.popularmovies.viewmodels.DetailMovieViewModelFactory;

import java.io.IOException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding mBinding;
//    private String reviewQueryResult;
//    private String videoQueryResult;
    private ReviewAdapter reviewAdapter;
    private VideoAdapter videoAdapter;
    private RecyclerView.LayoutManager reviewLayoutManager;
    private RecyclerView.LayoutManager videoLayoutManager;


    private static String REVIEWS = "reviews";
    private static String VIDEOS = "videos";

    private String movieId;
    private String movieTitle;
    private String releaseDate;
    private Double voteAvg;
    private String posterPath;
    private String overview;

    private AppDatabase mDb;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        mDb = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        if(intent.hasExtra(getResources().getString(R.string.title))){
            movieTitle = intent.getStringExtra(getResources().getString(R.string.title));
            mBinding.titleText.setText(movieTitle);

        }
        if(intent.hasExtra(getResources().getString(R.string.overview))){
            overview = intent.getStringExtra(getResources().getString(R.string.overview));
            mBinding.overview.setText("Overview:\n" + overview);

        }
        if(intent.hasExtra(getResources().getString(R.string.release_date))){
            releaseDate = intent.getStringExtra(getResources().getString(R.string.release_date));
            mBinding.releaseDate.setText("Release Date:" + releaseDate);
        }
        if(intent.hasExtra(getResources().getString(R.string.vote_average))){
            voteAvg = intent.getDoubleExtra(getResources().getString(R.string.vote_average), 0);
            mBinding.voteAvg.setText("Vote Average:" + voteAvg);
        }
        if(intent.hasExtra(getResources().getString(R.string.poster_path))){
            posterPath = intent.getStringExtra(getResources().getString(R.string.poster_path));
            CommonUtils.loadImageByPicasso(mBinding.posterImage, posterPath);
        }
        if(intent.hasExtra(getResources().getString(R.string.id))){
            movieId = intent.getStringExtra(getResources().getString(R.string.id));

            mBinding.videoRecyclerview.setHasFixedSize(true);
            videoLayoutManager = new LinearLayoutManager(this);
            mBinding.videoRecyclerview.setLayoutManager(videoLayoutManager);
            makeQuery(intent.getStringExtra(getResources().getString(R.string.id)), getResources().getString(R.string.videos));
            videoAdapter = new VideoAdapter(this, "");
            mBinding.videoRecyclerview.setAdapter(videoAdapter);


            mBinding.reviewRecyclerview.setHasFixedSize(true);
            reviewLayoutManager = new LinearLayoutManager(this);
            mBinding.reviewRecyclerview.setLayoutManager(reviewLayoutManager);
            makeQuery(intent.getStringExtra(getResources().getString(R.string.id)), getResources().getString(R.string.reviews));
            reviewAdapter = new ReviewAdapter("");
            mBinding.reviewRecyclerview.setAdapter(reviewAdapter);


        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void makeQuery(String id, String option) {

        URL queryUrl = NetworkUtils.buildUrl(id, option);
        new APIQueryTask(this, option).execute(queryUrl);
    }

    public class APIQueryTask extends AsyncTask<URL, Void, String>{

        private String option;
        private Context ctx;

        public APIQueryTask(Context ctx, String option){
            this.option = option;
            this.ctx = ctx;
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String queryResults = null;
            try {
                queryResults = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return queryResults;
        }
        @Override
        protected void onPostExecute(String searchResults) {

            if (searchResults != null && !searchResults.equals("")) {
                showJsonDataView(option);

                if(VIDEOS.equals(option)){
//                    videoQueryResult = searchResults;
//                    videoAdapter = new VideoAdapter(ctx, searchResults);
//                    mBinding.videoRecyclerview.setAdapter(videoAdapter);
                    videoAdapter.refreshVideos(searchResults);
                    videoAdapter.notifyDataSetChanged();
                }
                else if(REVIEWS.equals(option)){
//                    reviewQueryResult = searchResults;
//                    reviewAdapter = new ReviewAdapter(searchResults);
//                    mBinding.reviewRecyclerview.setAdapter(reviewAdapter);
                    reviewAdapter.refreshReviews(searchResults);
                    reviewAdapter.notifyDataSetChanged();
                }


            } else {
                showErrorMessage(option);
            }
        }
    }

    private void showJsonDataView(String option) {
        if(VIDEOS.equals(option)){
            mBinding.videoErrorMessageDisplay.setVisibility(View.GONE);
            mBinding.videoRecyclerview.setVisibility(View.VISIBLE);
        }
        else if(REVIEWS.equals(option)){
            mBinding.reviewErrorMessageDisplay.setVisibility(View.GONE);
            mBinding.reviewRecyclerview.setVisibility(View.VISIBLE);
        }

    }

    private void showErrorMessage(String option) {
        if(VIDEOS.equals(option)){
            mBinding.videoErrorMessageDisplay.setVisibility(View.VISIBLE);
            mBinding.videoRecyclerview.setVisibility(View.GONE);
        }
        else if(REVIEWS.equals(option)){
            mBinding.reviewErrorMessageDisplay.setVisibility(View.VISIBLE);
            mBinding.reviewRecyclerview.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        updateDetailViewModel(movieId);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                processFavorite(movieId, movieTitle, releaseDate, posterPath, voteAvg, overview);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void processFavorite(final String movieId, final String title, final String releaseDate, final String posterPath, final Double voteAverage, final String overview){
        DetailMovieViewModelFactory factory = new DetailMovieViewModelFactory(mDb, movieId);
        DetailMovieViewModel viewModel = ViewModelProviders.of(this, factory).get(DetailMovieViewModel.class);
        final Movie movie = viewModel.getMovie().getValue();
        if(movie == null){
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.moviesDao().addToFavorites(new Movie(movieId, title, releaseDate, posterPath, voteAverage, overview));
            }
            });
        }
        else{
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.moviesDao().deleteFavorites(movie);
                }
            });
        }

    }

    private void updateDetailViewModel(String movieId){

        if(movieId != null && !movieId.isEmpty()){
            DetailMovieViewModelFactory factory = new DetailMovieViewModelFactory(mDb, movieId);
            final DetailMovieViewModel viewModel = ViewModelProviders.of(this, factory).get(DetailMovieViewModel.class);
            viewModel.getMovie().observe(this, new Observer<Movie>() {
                @Override
                public void onChanged(@Nullable Movie movie) {
                    if(movie!= null){
                        menu.getItem(0).setIcon(R.drawable.remove_favorite);
                    }
                    else{
                        menu.getItem(0).setIcon(R.drawable.redheart);
                    }

                }
            });
        }

    }

}
