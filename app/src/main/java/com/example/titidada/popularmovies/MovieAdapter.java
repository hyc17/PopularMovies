package com.example.titidada.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.titidada.popularmovies.model.Movie;
import com.example.titidada.popularmovies.utilities.CommonUtils;
import com.example.titidada.popularmovies.utilities.JsonUtils;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.NumberViewHolder> {

    private Context mContext;
    private int numMovies = 0;
    private List<Movie> movies;
    private Class destinationActivity = DetailActivity.class;



    public MovieAdapter(Context context, String queryResult) {
        mContext = context;
        movies = JsonUtils.parseMoviesJson(queryResult);
        numMovies = (movies != null) ? movies.size() : 0;
    }


    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, final int position) {

        final Movie movie = movies.get(position);

        CommonUtils.loadImageByPicasso(holder.imageView, movie.getPosterPath());


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startDetailActivityIntent = new Intent(mContext, destinationActivity);
                startDetailActivityIntent.putExtra(mContext.getResources().getString(R.string.overview), movie.getOverview());
                startDetailActivityIntent.putExtra(mContext.getResources().getString(R.string.title), movie.getTitle());
                startDetailActivityIntent.putExtra(mContext.getResources().getString(R.string.release_date), movie.getReleaseDate());
                startDetailActivityIntent.putExtra(mContext.getResources().getString(R.string.vote_average), movie.getVoteAverage());
                startDetailActivityIntent.putExtra(mContext.getResources().getString(R.string.poster_path), movie.getPosterPath());
                mContext.startActivity(startDetailActivityIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return numMovies;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public NumberViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.movie_image_view);
        }

    }
    
}
