package com.yummyspots.service;

import android.content.Context;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;


public class LocationService {
    private Context context;
    private LocationGooglePlayServicesProvider provider;


    public LocationService(Context context) {
        this.context = context;
    }

    public void getLocation(OnLocationUpdatedListener listener) {
        SmartLocation.with(context).location()
                .oneFix()
                .start(listener);
    }

    public void startLocation(OnLocationUpdatedListener listener) {
        provider = new LocationGooglePlayServicesProvider();
        provider.setCheckLocationSettings(true);

        SmartLocation smartLocation = new SmartLocation.Builder(context).logging(true).build();
        smartLocation.location(provider).start(listener);
    }

    public void stopLocation() {
        SmartLocation.with(context).location().stop();
    }

    public LocationGooglePlayServicesProvider getProvider() {
        return provider;
    }
}
