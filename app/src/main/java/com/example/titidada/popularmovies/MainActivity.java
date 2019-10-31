package com.example.titidada.popularmovies;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.titidada.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView mErrorMessageDisplay;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private int numColsInGrid = 2;

    private String sortBy = "popular";
    private String searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessageDisplay = findViewById(R.id.error_message_display);
        Spinner sortBySpinner = findViewById(R.id.sort_by_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_by_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBySpinner.setAdapter(adapter);
        sortBySpinner.setOnItemSelectedListener(this);

        recyclerView = findViewById(R.id.movie_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, numColsInGrid);
        recyclerView.setLayoutManager(layoutManager);

        makeQuery(sortBy);
        mAdapter = new MovieAdapter(this, searchResult);
        recyclerView.setAdapter(mAdapter);

    }

    private void makeQuery(String sortBy) {
        URL movieDbQueryUrl = NetworkUtils.buildUrl(sortBy);
        new MovieDbQueryTask(this).execute(movieDbQueryUrl);
    }

    private void showJsonDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        recyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sortBy = (String) parent.getItemAtPosition(position);
        makeQuery(sortBy);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class MovieDbQueryTask extends AsyncTask<URL, Void, String> {

        public MovieDbQueryTask(Context ctx) {
            this.ctx = ctx;
        }

        private Context ctx;
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
                searchResult = movieSearchResults;
                mAdapter = new MovieAdapter(ctx, searchResult);
                recyclerView.setAdapter(mAdapter);

            } else {
                showErrorMessage();
            }
        }
    }


}
