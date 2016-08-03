package com.yummyspots.model;


import com.yummyspots.R;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by FVolodia on 28.09.15.
 */
public class Badge implements Comparable {

    public static final int COFFE_ID = 1;
    public static final int BEER_ID = 2;
    public static final int CAKES_ID = 3;
    public static final int BURGER_ID = 4;
    public static final int PIZZA_ID = 5;
    public static final int WINE_ID = 6;
    public static final int STAKE_ID = 7;
    public static final int SUSHI_ID = 8;

    private int codeName;
    private int color;
    private int id;

    public Badge(int codeName, int color, int id) {
        this.codeName = codeName;
        this.color = color;
        this.id = id;
    }

    public int getCodeName() {
        return codeName;
    }

    public void setCodeName(int codeName) {
        this.codeName = codeName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Collection<Badge> badgeList() {
        Set<Badge> all = new TreeSet<>();
        all.add(new Badge(R.integer.icon_tea24, R.color.coffe_selected, Badge.COFFE_ID));
        all.add(new Badge(R.integer.icon_beer16, R.color.beer_selected, Badge.BEER_ID));
        all.add(new Badge(R.integer.icon_sweet9, R.color.cakes_selected, Badge.CAKES_ID));
        all.add(new Badge(R.integer.icon_burger4, R.color.burger_selected, Badge.BURGER_ID));
        all.add(new Badge(R.integer.icon_italian1, R.color.pizza_selected, Badge.PIZZA_ID));
        all.add(new Badge(R.integer.icon_wine41, R.color.wine_selected, Badge.WINE_ID));
        all.add(new Badge(R.integer.icon_steak, R.color.steak_selected, Badge.STAKE_ID));
        all.add(new Badge(R.integer.icon_fish60, R.color.sushi_selected, Badge.SUSHI_ID));
        return all;
    }

    public static Map<Integer, Badge> badgeMap() {
        Map<Integer, Badge> badgeMap = new LinkedHashMap<>();
        for (Badge b : badgeList()) {
            badgeMap.put(b.getId(), b);
        }
        return badgeMap;
    }


    @Override
    public int compareTo(Object obj) {
        Badge badge = (Badge) obj;
        if (this.getId() < badge.getId()) {
            return -1;
        } else if (this.getId() > badge.getId()) {
            return 1;
        }
        return 0;
    }
}
