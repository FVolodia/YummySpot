package com.yummyspots.foursquare;

public class FoursquareConstants {
    public static final String API_BASE_URL = "https://api.foursquare.com/v2/venues/";
    public static final String VENUES = "search?v=20140806&radius=900&categoryId=4d4b7105d754a06374d81259&limit=50&client_id=" + FoursquareConstants.CLIENT_ID + "&client_secret=" + FoursquareConstants.CLIENT_SECRET;
    public static final String VENUES_FOR_SEARCH_NO_RADIUS = "search?v=20140806&categoryId=4d4b7105d754a06374d81259&limit=50&client_id=" + FoursquareConstants.CLIENT_ID + "&client_secret=" + FoursquareConstants.CLIENT_SECRET;
    public static final String VENUES_EXPLORE = "explore?v=20140806&venuePhotos=1&section=food&limit=50&client_id=" + FoursquareConstants.CLIENT_ID + "&client_secret=" + FoursquareConstants.CLIENT_SECRET;
    public static final String VENUES_DETAIL = "?v=20140806&client_id=" + FoursquareConstants.CLIENT_ID + "&client_secret=" + FoursquareConstants.CLIENT_SECRET;
    public static final String CLIENT_ID = "SPBHJBF4HZ0YRSWGJE0AGLLIVMCRDLZK33HTMA4J21HIWFXG";
    public static final String CLIENT_SECRET = "KD2RWRGUGB5CQ0UI5HMANQEFMHXAIMNPQF0HMLHXXSAPKPWK";
}
