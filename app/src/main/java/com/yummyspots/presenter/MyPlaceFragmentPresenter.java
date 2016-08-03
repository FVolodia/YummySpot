package com.yummyspots.presenter;

import android.app.Activity;
import android.content.Intent;

import com.yummyspots.activity.PlaceViewActivity;
import com.yummyspots.database.DatabaseHelper;
import com.yummyspots.database.dao.PlacesDao;
import com.yummyspots.fragment.MyPlaceFragment;
import com.yummyspots.model.Badge;
import com.yummyspots.model.Place;
import com.yummyspots.view.BadgeView;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by FVolodia on 23.12.15.
 */
public class MyPlaceFragmentPresenter {

    private MyPlaceFragment fragment;
    private Activity activity;
    private PlacesDao pDao;
    private String keyword;
    private Set<Integer> selectedBadges = new HashSet<>();


    public MyPlaceFragmentPresenter(MyPlaceFragment fragment) {
        this.fragment = fragment;
        activity = fragment.getActivity();
        pDao = (PlacesDao) DatabaseHelper.getHelper().getDaoInstance(PlacesDao.class);
    }

    public void goToPlaceDetail(Place place) {
        Intent intent = new Intent(activity, PlaceViewActivity.class);
        intent.putExtra(PlaceViewActivityPresenter.ARG_PLACE, place);
        activity.startActivity(intent);
    }

    public void queryPlace() {
        this.queryPlace(keyword);
    }

    public void queryPlace(String keyword) {
        try {
            List<Place> data = pDao.filter(selectedBadges, keyword);
            fragment.updateList(data);
        } catch (SQLException e) {
            // LOG.error(e.getMessage(), e);
        }
        this.keyword = keyword;
    }

    public void selectBadge(BadgeView badgeView, Badge badge) {
        if (badgeView.isSelected()) {
            selectedBadges.add(badge.getId());
        } else {
            selectedBadges.remove(badge.getId());
        }
    }

    public void reset() {
        this.keyword = null;
        this.selectedBadges = new HashSet<>();
    }
}
