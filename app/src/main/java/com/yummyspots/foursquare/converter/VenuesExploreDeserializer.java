package com.yummyspots.foursquare.converter;

import com.yummyspots.foursquare.model.Venue;
import com.yummyspots.foursquare.model.VenuesResponseExplore;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FVolodia on 14.12.15.
 */
public class VenuesExploreDeserializer implements JsonDeserializer<VenuesResponseExplore> {
    @Override
    public VenuesResponseExplore deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        VenuesResponseExplore responseExplore = new VenuesResponseExplore();
        List<Venue> venueList = new ArrayList<>();

        int returnCode = json.getAsJsonObject().get("meta").getAsJsonObject().get("code").getAsInt();
        if (returnCode == 200) {

            Type venue = new TypeToken<Venue>() {
            }.getType();

            JsonArray items = json.getAsJsonObject().get("response").getAsJsonObject().get("groups").getAsJsonArray().get(0).getAsJsonObject().get("items").getAsJsonArray();
            for (JsonElement element : items) {
                Venue v = new Gson().fromJson(element.getAsJsonObject().get("venue").getAsJsonObject(), venue);
                venueList.add(v);
            }

            responseExplore.setVenues(venueList);
        }

        return responseExplore;
    }
}
