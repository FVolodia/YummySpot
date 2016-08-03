package com.yummyspots.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yummyspots.R;
import com.yummyspots.fragment.ExplorePlaceFragment;
import com.yummyspots.fragment.MyPlaceFragment;
import com.yummyspots.fragment.NearPlaceFragment;

/**
 * Created by FVolodia on 12.10.15.
 */
public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] tabs;
    private Context context;

    public TabsPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        tabs = new String[]{"Near Places", "Explore Places", "My Places"};
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NearPlaceFragment.getInstance();
            case 1:
                return ExplorePlaceFragment.getInstance();
            case 2:
                return MyPlaceFragment.getInstance();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.near_place);
            case 1:
                return context.getResources().getString(R.string.explore_place);
            case 2:
                return context.getResources().getString(R.string.my_place);
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

}
