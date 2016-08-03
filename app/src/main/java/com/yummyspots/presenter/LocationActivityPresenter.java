package com.yummyspots.presenter;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.widget.Toast;

import com.yummyspots.R;
import com.yummyspots.activity.LocationActivity;
import com.yummyspots.activity.MainActivity;
import com.yummyspots.service.LocationService;
import com.yummyspots.util.DeviceUtils;
import com.yummyspots.util.PreferencesManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;


public class LocationActivityPresenter implements OnLocationUpdatedListener {

    private LocationActivity activity;

    private LocationService locationService;

    public LocationActivityPresenter(LocationActivity activity) {
        this.activity = activity;
        locationService = new LocationService(activity);
    }

    public void processLocation() {
        final Location lastLocation = SmartLocation.with(activity).location().getLastLocation();
        if (lastLocation == null) {
            if (DeviceUtils.v6(activity)) {
                Dexter.checkPermission(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        locationService.getLocation(new OnLocationUpdatedListener() {
                            @Override
                            public void onLocationUpdated(Location location) {
                                goToMainActivity(location);
                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(activity, R.string.permission_info, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }, Manifest.permission.ACCESS_FINE_LOCATION);
            } else {
                locationService.startLocation(this);
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToMainActivity(lastLocation);
                }
            }, 1000);
        }
    }

    public void stopLocationService() {
        this.locationService.stopLocation();
    }

    public void goToMainActivity(Location location) {
        float lat = (float) location.getLatitude();
        float lon = (float) location.getLongitude();

        PreferencesManager.putLon(activity, lon);
        PreferencesManager.putLat(activity, lat);
        Intent intent = new Intent(activity, MainActivity.class);
        //intent.putExtra("Latitude", location.getLatitude());
        //intent.putExtra("Longitude", location.getLongitude());
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void onLocationUpdated(Location location) {
        goToMainActivity(location);
    }

    public LocationGooglePlayServicesProvider getProvider() {
        return locationService.getProvider();
    }

    public void goToMainActivity() {
        float lat = 49.838602f;
        float lon = 24.030621f;


        PreferencesManager.putLon(activity, lon);
        PreferencesManager.putLat(activity, lat);
        Intent intent = new Intent(activity, MainActivity.class);
        //intent.putExtra("Latitude", location.getLatitude());
        //intent.putExtra("Longitude", location.getLongitude());
        activity.startActivity(intent);
        activity.finish();
    }
}
