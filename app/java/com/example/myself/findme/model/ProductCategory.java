package com.example.myself.findme.model;

/**
 * Created by GB on 12/10/2015.
 */
 public class ProductCategory {

    private final String title;
    private final String director;
    private final int rating;
    private final int imageResourceId;

    public ProductCategory(String title, String director, int rating, int imageResourceId) {
        this.title = title;
        this.director = director;
        this.rating = rating;
        this.imageResourceId = imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getRating() {
        return rating;
    }

    public int getCoverResourceId() {
        return imageResourceId;
    }
}