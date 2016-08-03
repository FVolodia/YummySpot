package com.yummyspots.foursquare.model;

import java.util.ArrayList;

/**
 * Created by FVolodia on 19.10.15.
 */
public class Photos {

    private int count;

    private String summary;

    private ArrayList<PhotoItem> items;

    private PhotosGroup groups;

    public PhotosGroup getGroups() {
        return groups;
    }

    public String getSummary() {
        return summary;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<PhotoItem> getItems() {
        return items;
    }
}
