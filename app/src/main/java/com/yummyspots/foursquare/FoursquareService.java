package com.yummyspots.foursquare;

import android.content.Context;

import com.yummyspots.http.ServiceGenerator;
import com.yummyspots.foursquare.model.VenuesDetail;
import com.yummyspots.foursquare.model.VenuesResponse;
import com.yummyspots.foursquare.model.VenuesResponseExplore;
import com.yummyspots.util.PreferencesManager;

import retrofit.Call;
import retrofit.Callback;


public class FoursquareService {

    private Context context;
    private FoursquareApi foursquareApi;

    public FoursquareService(Context context) {
        this.context = context;
        foursquareApi = ServiceGenerator.createService(FoursquareApi.class);
    }

    public void getNearbyVenues(Callback<VenuesResponse> callback) {
        float lat = PreferencesManager.getLat(context);
        float lon = PreferencesManager.getLon(context);
        String location = lat + "," + lon;

        Call<VenuesResponse> call = foursquareApi.getNearbyVenues(location);
        call.enqueue(callback);
    }

    public void searchForVanues(Callback<VenuesResponse> callback, String query) {
        float lat = PreferencesManager.getLat(context);
        float lon = PreferencesManager.getLon(context);
        String location = lat + "," + lon;

        Call<VenuesResponse> call = foursquareApi.searchForVenues(query, location);
        call.enqueue(callback);
    }

    public void getVenueDetails(Callback<VenuesDetail> callback, String venueId) {
        Call<VenuesDetail> call = foursquareApi.getVenueDetails(venueId);
        call.enqueue(callback);
    }

    public void getExploreVenuesNear(Callback<VenuesResponseExplore> callback) {
        float lat = PreferencesManager.getLat(context);
        float lon = PreferencesManager.getLon(context);
        String location = lat + "," + lon;

        Call<VenuesResponseExplore> call = foursquareApi.getExploreVenuesNear(location);
        call.enqueue(callback);
    }
}
