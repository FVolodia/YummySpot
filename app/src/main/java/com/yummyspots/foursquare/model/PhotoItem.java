package com.yummyspots.foursquare.model;

/**
 * Created by FVolodia on 19.10.15.
 */
public class PhotoItem {

    private String id;

    private long createdAt;

    private int height;
    private int width;

    private String prefix;
    private String suffix;

    private String visibility;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getId() {
        return id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getVisibility() {
        return visibility;
    }
}
