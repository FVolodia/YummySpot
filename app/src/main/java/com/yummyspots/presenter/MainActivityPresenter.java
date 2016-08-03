package com.yummyspots.presenter;


import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.yummyspots.activity.MainActivity;
import com.yummyspots.fragment.Filterable;
import com.yummyspots.service.LocationService;
import com.yummyspots.util.PreferencesManager;

import java.lang.ref.WeakReference;

import io.nlopez.smartlocation.OnLocationUpdatedListener;

/**
 * Created by FVolodia on 21.12.15.
 */
public class MainActivityPresenter {
    private static final int SEARCH_MESSAGE = 0;

    private MainActivity activity;
    private LocationService locationService;
    private OnlineSearchHandler onlineSearchHandler;

    public MainActivityPresenter(MainActivity activity) {
        this.activity = activity;
        onlineSearchHandler = new OnlineSearchHandler();
        locationService = new LocationService(activity);

    }

    public void updateLocation(final OnLocationUpdatedListener listener) {
        locationService.startLocation(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
                float lat = (float) location.getLatitude();
                float lon = (float) location.getLongitude();
                PreferencesManager.putLon(activity, lon);
                PreferencesManager.putLat(activity, lat);
                locationService.stopLocation();
                if (listener != null) {
                    listener.onLocationUpdated(location);
                }
            }
        });
    }

    private static final class OnlineSearchHandler extends Handler {
        private WeakReference<Filterable> reference;

        public void setFilterable(Filterable filterable) {
            reference = new WeakReference<>(filterable);
        }

        @Override
        public void handleMessage(Message msg) {
            Filterable fragment = reference.get();
            fragment.filterFromKeyWord(msg.getData().getString("keyWord"));
        }
    }

    public boolean onQueryTextChange(String newText) {
        if (activity.getActiveFragment() instanceof Filterable) {
            onlineSearchHandler.setFilterable((Filterable) activity.getActiveFragment());
            onlineSearchHandler.removeMessages(SEARCH_MESSAGE);
            Message message = onlineSearchHandler.obtainMessage(SEARCH_MESSAGE);
            Bundle b = new Bundle();
            b.putString("keyWord", newText);
            message.setData(b);
            onlineSearchHandler.sendMessageDelayed(message, 800);
        } else {
            onlineSearchHandler.removeMessages(SEARCH_MESSAGE);
        }
        return true;
    }

}
