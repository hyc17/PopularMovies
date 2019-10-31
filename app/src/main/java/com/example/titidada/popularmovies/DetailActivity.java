package com.example.titidada.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.titidada.popularmovies.utilities.CommonUtils;

public class DetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView releaseDateTextView;
    private TextView voteAvgTextView;
    private TextView overviewTextView;
    private ImageView posterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleTextView = findViewById(R.id.title_text);
        releaseDateTextView = findViewById(R.id.release_date);
        voteAvgTextView = findViewById(R.id.vote_avg);
        overviewTextView = findViewById(R.id.overview);
        posterImageView = findViewById(R.id.poster_image);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        if(intent.hasExtra(getResources().getString(R.string.title))){
            titleTextView.setText(intent.getStringExtra(getResources().getString(R.string.title)));
        }
        if(intent.hasExtra(getResources().getString(R.string.overview))){
            overviewTextView.setText("Overview:\n" + intent.getStringExtra(getResources().getString(R.string.overview)));
        }
        if(intent.hasExtra(getResources().getString(R.string.release_date))){
            releaseDateTextView.setText("Release Date:" + intent.getStringExtra(getResources().getString(R.string.release_date)));
        }
        if(intent.hasExtra(getResources().getString(R.string.vote_average))){
            voteAvgTextView.setText("Vote Average:" + intent.getDoubleExtra(getResources().getString(R.string.vote_average), 0));
        }
        if(intent.hasExtra(getResources().getString(R.string.poster_path))){
            CommonUtils.loadImageByPicasso(posterImageView, intent.getStringExtra(getResources().getString(R.string.poster_path)));
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
