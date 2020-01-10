package com.example.titidada.popularmovies.model;

public class UserReview {
    private String author;
    private String content;

    public UserReview(String author, String content){
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }


}
