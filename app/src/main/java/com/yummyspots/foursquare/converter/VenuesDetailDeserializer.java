package com.yummyspots.foursquare.converter;

import com.yummyspots.foursquare.model.VenuesDetail;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by FVolodia on 01.12.15.
 */
public class VenuesDetailDeserializer implements JsonDeserializer<VenuesDetail> {

    @Override
    public VenuesDetail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        VenuesDetail venuesDetail = new VenuesDetail();
        int returnCode = json.getAsJsonObject().get("meta").getAsJsonObject().get("code").getAsInt();
        if (returnCode == 200) {
            Type type = new TypeToken<VenuesDetail>() {
            }.getType();

            VenuesDetail detail = new Gson().fromJson(json.getAsJsonObject().get("response").getAsJsonObject().get("venue"), type);
            venuesDetail = detail;
        }

        return venuesDetail;
    }
}
