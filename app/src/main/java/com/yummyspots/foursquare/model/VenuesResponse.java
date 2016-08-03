package com.yummyspots.foursquare.model;

import java.util.List;

/**
 * Created by Bogdan Melnychuk on 11/30/15.
 */
public class VenuesResponse {
    List<Venue> venues;

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }
}
