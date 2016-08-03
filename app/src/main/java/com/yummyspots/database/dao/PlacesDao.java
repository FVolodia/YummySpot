package com.yummyspots.database.dao;

import android.text.TextUtils;

import com.yummyspots.model.Place;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlacesDao extends BaseDao<Place> {
    public PlacesDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Place.class);
    }

    public List<Place> filter(Set<Integer> badges, String keyword) throws SQLException {
        List<Place> all = queryForAll();
        all = filterByName(all, keyword);
        all = filterByBadges(all, badges);
        return all;
    }

    private List<Place> filterByName(List<Place> places, String name) {
        if (TextUtils.isEmpty(name)) {
            return places;
        }
        List<Place> result = new ArrayList<>();
        for (Place p : places) {
            if (p.getTitle().toUpperCase().contains(name.toUpperCase())) {
                result.add(p);
            }

        }
        return result;
    }

    private List<Place> filterByBadges(List<Place> places, Set<Integer> badges) {
        if (badges == null || badges.isEmpty()) {
            return places;
        }
        List<Place> result = new ArrayList<>();
        for (Place p : places) {
            for (Integer i : badges) {
                if (p.getBadges().contains(i)) {
                    result.add(p);
                    break;
                }
            }

        }
        return result;
    }

    public Place findByFoursqureID(String foursquareId) {
        try {
            return queryForEq("foursquareID", foursquareId).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Place> queryForAll() throws SQLException {
        return queryBuilder().orderBy("title", true).query();
    }
}
