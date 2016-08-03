package com.yummyspots.foursquare;

import com.yummyspots.foursquare.model.VenuesDetail;
import com.yummyspots.foursquare.model.VenuesResponse;
import com.yummyspots.foursquare.model.VenuesResponseExplore;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


public interface FoursquareApi {
    @GET(FoursquareConstants.VENUES)
    Call<VenuesResponse> getNearbyVenues(@Query("ll") String location);

    @GET(FoursquareConstants.VENUES_FOR_SEARCH_NO_RADIUS)
    Call<VenuesResponse> searchForVenues(@Query("query") String query, @Query("ll") String location);

    @GET("{venueId}" + FoursquareConstants.VENUES_DETAIL)
    Call<VenuesDetail> getVenueDetails(@Path("venueId") String venueId);

    @GET(FoursquareConstants.VENUES_EXPLORE)
    Call<VenuesResponseExplore> getExploreVenuesNear(@Query("ll") String location);
}
