package com.yummyspots.presenter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.yummyspots.activity.PlaceViewActivity;
import com.yummyspots.database.DatabaseHelper;
import com.yummyspots.database.dao.PlacesDao;
import com.yummyspots.foursquare.FoursquareService;
import com.yummyspots.foursquare.model.Venue;
import com.yummyspots.foursquare.model.VenuesResponse;
import com.yummyspots.fragment.NearPlaceFragment;
import com.yummyspots.model.Place;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class NearPlaceFragmentPresenter {
    private NearPlaceFragment fragment;
    private Activity activity;

    private PlacesDao pDao;
    private FoursquareService fsqService;
    private String lastQuery;

    Callback<VenuesResponse> callback = new Callback<VenuesResponse>() {
        @Override
        public void onResponse(Response<VenuesResponse> response, Retrofit retrofit) {
            List<Place> data = new ArrayList<>();
            for (Venue v : response.body().getVenues()) {
                final Place p = new Place();
                p.setTitle(v.getName());
                p.setLocation(v.getLocation().getFormattedAddress());
                p.setSite(v.getUrl());
                p.setFoursquareID(v.getId());
                p.setPhoneNumb(v.getContact().getFormatedPhone());
                p.setLatitude(v.getLocation().getLat());
                p.setLongitude(v.getLocation().getLng());
                data.add(p);
            }
            fragment.updateList(data);
        }

        @Override
        public void onFailure(Throwable t) {
            fragment.onError(t);
        }
    };

    public NearPlaceFragmentPresenter(NearPlaceFragment fragment) {
        this.fragment = fragment;
        activity = fragment.getActivity();
        pDao = (PlacesDao) DatabaseHelper.getHelper().getDaoInstance(PlacesDao.class);
        fsqService = new FoursquareService(activity);

    }

    public void goToPlaceDetails(Place place) {
        Place myPlace = pDao.findByFoursqureID(place.getFoursquareID());
        Intent intent = new Intent(activity, PlaceViewActivity.class);
        intent.putExtra(PlaceViewActivityPresenter.ARG_PLACE, myPlace == null ? place : myPlace);
        activity.startActivity(intent);
    }

    public void queryPlaces() {
        this.queryPlaces(lastQuery);
    }

    public void queryPlaces(String lastQuery) {
        if (TextUtils.isEmpty(lastQuery)) {
            fsqService.getNearbyVenues(callback);
        } else {
            fsqService.searchForVanues(callback, lastQuery);
        }
        this.lastQuery = lastQuery;
    }
}
