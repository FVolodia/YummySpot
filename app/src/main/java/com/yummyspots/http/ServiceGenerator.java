package com.yummyspots.http;

import com.yummyspots.foursquare.FoursquareConstants;
import com.yummyspots.foursquare.converter.VenuesDetailDeserializer;
import com.yummyspots.foursquare.converter.VenuesExploreDeserializer;
import com.yummyspots.foursquare.converter.VenuesResponseDeserializer;
import com.yummyspots.foursquare.model.VenuesDetail;
import com.yummyspots.foursquare.model.VenuesResponse;
import com.yummyspots.foursquare.model.VenuesResponseExplore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Bogdan Melnychuk on 11/30/15.
 */
public class ServiceGenerator {

    private static OkHttpClient httpClient = new OkHttpClient();

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(VenuesResponse.class, new VenuesResponseDeserializer())
            .registerTypeAdapter(VenuesDetail.class, new VenuesDetailDeserializer())
            .registerTypeAdapter(VenuesResponseExplore.class, new VenuesExploreDeserializer())
            .create();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(FoursquareConstants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
