package com.example.titidada.popularmovies.model;

public class VideoInfo {

    private String name;
    private String key;

    public VideoInfo(String name, String key){
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }


}
