package com.yummyspots.foursquare.converter;

import com.yummyspots.foursquare.model.VenuesResponse;
import com.yummyspots.foursquare.model.Venue;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class VenuesResponseDeserializer implements JsonDeserializer<VenuesResponse> {

    @Override
    public VenuesResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        VenuesResponse response = new VenuesResponse();
        int returnCode = json.getAsJsonObject().get("meta").getAsJsonObject().get("code").getAsInt();
        if (returnCode == 200) {
            Type listType = new TypeToken<List<Venue>>() {
            }.getType();
            List<Venue> venuesList = new Gson().fromJson(json.getAsJsonObject().get("response").getAsJsonObject().get("venues").getAsJsonArray(), listType);
            response.setVenues(venuesList);
        }
        return response;
    }
}
