package com.example.titidada.popularmovies.utilities;

import android.util.Log;
import com.example.titidada.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {
    private final static String TITLE = "title";
    private final static String RELEASE_DATE = "release_date";
    private final static String POSTER_PATH = "poster_path";
    private final static String VOTE_AVERAGE = "vote_average";
    private final static String OVERVIEW = "overview";

    private final static String RESULTS = "results";

    public static List<Movie> parseMoviesJson(String queryResult){
        List<Movie> movies = new ArrayList<>();
        if(queryResult != null && !queryResult.isEmpty()){
            try {
                JSONObject queryResultJsonObject = new JSONObject(queryResult);
                JSONArray resultArray = queryResultJsonObject.getJSONArray(RESULTS);
                if(resultArray != null){
                    for(int i = 0; i < resultArray.length(); i++){
                        JSONObject movieObject = resultArray.getJSONObject(i);
                        if(movieObject != null){
                            try {

                                String title = movieObject.getString(TITLE);
                                String releaseDate = movieObject.getString(RELEASE_DATE);
                                String posterPath = movieObject.getString(POSTER_PATH);
                                Double voteAverage = movieObject.getDouble(VOTE_AVERAGE);
                                String overview = movieObject.getString(OVERVIEW);

                                Movie movie = new Movie(title,releaseDate,posterPath, voteAverage, overview);
                                movies.add(movie);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("JsonUtils", e.getMessage());
                            }
                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }
}
