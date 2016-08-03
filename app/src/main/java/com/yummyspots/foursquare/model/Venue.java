package com.yummyspots.foursquare.model;

import java.util.ArrayList;

public class Venue {

    private String id;

    private String name;

    private Location location;

    private String rating;

    private String ratingSignals;

    private Contact contact;

    private ArrayList<Category> categories;

    private boolean verified;

    private Statistics stats;

    private HereNow beenHere;

    private HereNow hereNow;

    private long createdAt;

    private String timeZone;

    private String canonicalUrl;

    private String shortUrl;

    private boolean dislike;

    private String url;

    private boolean like;

    private String pictureUrl;

    public String getRating() {
        return rating;
    }

    public String getRatingSignals() {
        return ratingSignals;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public boolean isDislike() {
        return dislike;
    }

    public HereNow getHereNow() {
        return hereNow;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        String newName;
        if (this.name.contains("/")) {
            int a = this.name.indexOf("/");
            newName = this.name.substring(0, a);
            return newName;
        }
        return name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Location getLocation() {
        return location;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public boolean isVerified() {
        return verified;
    }

    public Statistics getStats() {
        return stats;
    }

    public HereNow getBeenHere() {
        return beenHere;
    }

    public String getUrl() {
        if (url != null && url.contains("//")){
            int a = url.indexOf("//") + 2;
            String newUrl = url.substring(a, url.length());
            return newUrl;
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public Contact getContact() {
        return contact;
    }
}
