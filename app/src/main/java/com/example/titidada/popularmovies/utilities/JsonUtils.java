package com.example.titidada.popularmovies.utilities;

import android.util.Log;

import com.example.titidada.popularmovies.model.Movie;
import com.example.titidada.popularmovies.model.UserReview;
import com.example.titidada.popularmovies.model.VideoInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    private final static String NAME = "name";
    private final static String KEY = "key";

    private final static String ID = "id";
    private final static String TITLE = "title";
    private final static String RELEASE_DATE = "release_date";
    private final static String POSTER_PATH = "poster_path";
    private final static String VOTE_AVERAGE = "vote_average";
    private final static String OVERVIEW = "overview";

    private final static String RESULTS = "results";

    private final static String AUTHOR = "author";
    private final static String CONTENT = "content";


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

                                String id = movieObject.getString(ID);
                                String title = movieObject.getString(TITLE);
                                String releaseDate = movieObject.getString(RELEASE_DATE);
                                String posterPath = movieObject.getString(POSTER_PATH);
                                Double voteAverage = movieObject.getDouble(VOTE_AVERAGE);
                                String overview = movieObject.getString(OVERVIEW);

                                Movie movie = new Movie(id, title,releaseDate,posterPath, voteAverage, overview);
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

    public static List<UserReview> parseReviewsJson(String queryResult){
        List<UserReview> reviews = new ArrayList<>();
        if(queryResult != null && !queryResult.isEmpty()){
            try {
                JSONObject queryResultJsonObject = new JSONObject(queryResult);
                JSONArray resultArray = queryResultJsonObject.getJSONArray(RESULTS);
                if(resultArray != null){
                    for(int i = 0; i < resultArray.length(); i++){
                        JSONObject reviewObject = resultArray.getJSONObject(i);
                        if(reviewObject != null){
                            try {
                                String author = reviewObject.getString(AUTHOR);
                                String content = reviewObject.getString(CONTENT);


                                UserReview review = new UserReview(author, content);
                                reviews.add(review);

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
        return reviews;

    }

    public static List<VideoInfo> parseVideosJson(String queryResult){

        List<VideoInfo> videos = new ArrayList<>();
        if(queryResult != null && !queryResult.isEmpty()){
            try {
                JSONObject queryResultJsonObject = new JSONObject(queryResult);
                JSONArray resultArray = queryResultJsonObject.getJSONArray(RESULTS);
                if(resultArray != null){
                    for(int i = 0; i < resultArray.length(); i++){
                        JSONObject videoObject = resultArray.getJSONObject(i);
                        if(videoObject != null){
                            try {
                                String name = videoObject.getString(NAME);
                                String key = videoObject.getString(KEY);

                                VideoInfo video = new VideoInfo(name, key);
                                videos.add(video);

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
        return videos;

    }
}
