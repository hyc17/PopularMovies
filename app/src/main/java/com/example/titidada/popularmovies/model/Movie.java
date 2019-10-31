package com.example.titidada.popularmovies.model;

public class Movie {

    private String title;
    private String releaseDate;
    private String posterPath;
    private Double voteAverage;
    private String overview;

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public Movie(String title, String releaseDate, String posterPath, Double voteAverage, String overview) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }




}
