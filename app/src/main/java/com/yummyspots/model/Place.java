package com.yummyspots.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by FVolodia on 10.09.15.
 */
@DatabaseTable
public class Place implements Serializable {

    public enum Type {RESTAURANT, PUB, COFFESHOP, CLUB, FASTFOOD}

    @DatabaseField(canBeNull = false, columnName = "_id", generatedId = true)
    private int id;
    @DatabaseField
    private String foursquareID;
    @DatabaseField
    private String title;
    @DatabaseField
    private String category;
    @DatabaseField
    private String phoneNumb;
    @DatabaseField
    private String site;
    @DatabaseField(dataType = DataType.ENUM_STRING)
    private Type type;
    @DatabaseField
    private String location;
    @DatabaseField
    private double latitude;
    @DatabaseField
    private double longitude;
    @DatabaseField
    private String rating;
    @DatabaseField
    private String ratingSignals;
    @DatabaseField
    private String imagePlaceUrl;
    @DatabaseField(useGetSet = true)
    private String badgesJson;
    private Set<Integer> badges = new HashSet<>();

    public Place() {

    }

    public Place(String title, String phoneNumb, String site, Type type, String location, double latitude, double longitude, String imagePlaceUrl) {
        this.title = title;
        this.phoneNumb = phoneNumb;
        this.site = site;
        this.type = type;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imagePlaceUrl = imagePlaceUrl;
    }

    public Set<Integer> getBadges() {
        return badges;
    }

    public void setBadges(Set<Integer> badges) {
        this.badges = badges;
    }

    public String getTitle() {
        return title;
    }

    public Type getType() {
        return type;
    }

    public String getPhoneNumb() {
        return phoneNumb;
    }

    public void setPhoneNumb(String phoneNumb) {
        this.phoneNumb = phoneNumb;
    }

    public String getSite(){
        return site;
    }

    public void setSite(String site){
        this.site = site;
    }

    public String getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getRating(){
        return rating;
    }

    public String getRatingSignals() {
        return ratingSignals;
    }

    public String getCategory() {
        return category;
    }

    public String getImagePlaceUrl() {
        return imagePlaceUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setRating (String rating){
        this.rating = rating;
    }

    public void setRatingSignals(String ratingSignals) {
        this.ratingSignals = ratingSignals;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImagePlaceUrl(String imagePlaceUrl) {
        this.imagePlaceUrl = imagePlaceUrl;
    }

    public String getFoursquareID() {
        return foursquareID;
    }

    public void setFoursquareID(String foursquareID) {
        this.foursquareID = foursquareID;
    }

    public String getBadgesJson() {
        try {
            JSONObject result = new JSONObject();
            JSONArray badgesJsonArray = new JSONArray();

            for (Integer i : this.badges) {
                badgesJsonArray.put(i);
            }

            result.put("badges", badgesJsonArray);
            return result.toString();
        } catch (JSONException e) {
            //TODO
        }
        return null;
    }

    public void setBadgesJson(String badgesJson) {
        this.badgesJson = badgesJson;
        try {
            JSONObject root = new JSONObject(badgesJson);
            JSONArray badgesArray = root.getJSONArray("badges");
            for (int i = 0; i < badgesArray.length(); i++) {
                this.badges.add(badgesArray.getInt(i));
            }
        } catch (JSONException e) {
        }
    }

    public int getId() {
        return id;
    }

    public Place withBages(int bageId) {
        this.badges.add(bageId);
        return this;
    }


}
