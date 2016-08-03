package com.yummyspots.presenter;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;

import com.yummyspots.activity.PlaceViewActivity;
import com.yummyspots.database.DatabaseHelper;
import com.yummyspots.database.dao.PlacesDao;
import com.yummyspots.model.Badge;
import com.yummyspots.model.Place;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.EmptyPermissionListener;

import java.sql.SQLException;

/**
 * Created by FVolodia on 22.12.15.
 */
public class PlaceViewActivityPresenter {
    public static final String ARG_PLACE = "_argPlace";

    private PlaceViewActivity activity;
    private PlacesDao pDao;
    private Place place;

    public PlaceViewActivityPresenter(PlaceViewActivity activity) {
        this.activity = activity;
        pDao = (PlacesDao) DatabaseHelper.getHelper().getDaoInstance(PlacesDao.class);
    }

    public void init(Intent intent) {
        place = (Place) intent.getSerializableExtra(ARG_PLACE);
        activity.showPlace(place);
    }

    public boolean updateBadges(Badge b, boolean isSelected) throws SQLException {
        if (isSelected) {
            place.getBadges().add(b.getId());
        } else {
            place.getBadges().remove(b.getId());
        }
        if (place.getBadges().isEmpty()) {
            pDao.delete(place);
        } else {
            boolean addedAsNew = place.getId() == 0;
            pDao.createOrUpdate(place);
            return addedAsNew;
        }
        return false;
    }

    public void call() {
        Dexter.checkPermission(new EmptyPermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                Uri phoneIntentUri = Uri.parse("tel:" + place.getPhoneNumb());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, phoneIntentUri);
                activity.startActivity(callIntent);
            }

        }, Manifest.permission.CALL_PHONE);
    }

    public void web() {
        Uri urlIntentUri = Uri.parse("http://" + place.getSite());
        Intent webIntent = new Intent(Intent.ACTION_VIEW, urlIntentUri);
        activity.startActivity(webIntent);
    }

    public void clickNavigation() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + place.getLatitude() + "," + place.getLongitude() + "&mode=w");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(mapIntent);
        }
    }
}
