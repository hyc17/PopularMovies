package com.example.titidada.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    final static String API_KEY = "api_key";
    final static String API_KEY_CONTENT = "";

    final static String YOUTUBE_URL = "https://www.youtube.com/watch";
    final static String V = "v";



    public static URL buildUrl(String sortBy) {
        Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL+sortBy).buildUpon()
                .appendQueryParameter(API_KEY, API_KEY_CONTENT)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildUrl(String movieId, String option) {
        Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL+ movieId + "/" + option).buildUpon()
                .appendQueryParameter(API_KEY, API_KEY_CONTENT)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static Uri buildTrailerUri(String key){
        Uri uri = Uri.parse(YOUTUBE_URL).buildUpon()
                .appendQueryParameter(V, key)
                .build();
        return uri;
    }



}
