package com.yummyspots.foursquare.model;

import java.util.ArrayList;

/**
 * Created by FVolodia on 25.11.15.
 */
public class VenuesDetail {
    private String rating;
    private String ratingSignals;
    private ArrayList<Category> categories;
    private BestPhoto bestPhoto;

    public String getRating() {
        return rating;
    }

    public String getRatingSignals() {
        return ratingSignals;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public BestPhoto getBestPhoto() {
        return bestPhoto;
    }
}
