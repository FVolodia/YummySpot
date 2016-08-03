package com.yummyspots.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.yummyspots.R;
import com.yummyspots.adapter.TabsPagerFragmentAdapter;
import com.yummyspots.fragment.BaseFragment;
import com.yummyspots.presenter.MainActivityPresenter;
import com.yummyspots.util.AnimationUtils;
import com.yummyspots.util.Logger;
import com.yummyspots.view.FABBehavior;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.Bind;
import butterknife.OnClick;
import io.nlopez.smartlocation.OnLocationUpdatedListener;

public class MainActivity extends BaseActivity {
    private static final Logger LOG = Logger.getLogger(MainActivity.class.getName());

    @Bind(R.id.viewPagerTabs)
    SmartTabLayout tabLayout;

    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    TabsPagerFragmentAdapter adapter;
    private MenuItem searchItem;
    private MainActivityPresenter presenter;

    public Fragment getActiveFragment() {
        String name = makeFragmentName(viewPager.getId(), viewPager.getCurrentItem());
        return getSupportFragmentManager().findFragmentByTag(name);
    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter(this);

        setSupportActionBar(toolbar);
        initTabs();
        showFab(600);
    }

    @OnClick(R.id.fab)
    protected void fabExpandView() {
        searchItem.expandActionView();
    }

    private void initTabs() {
        adapter = new TabsPagerFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateFabVisibility(position);
                ((BaseFragment)getActiveFragment()).reset();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setDefaultTabTextColor(Color.WHITE);
        tabLayout.setDividerColors(Color.TRANSPARENT);
        tabLayout.setViewPager(viewPager);
    }

    private void updateFabVisibility(int position) {
        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        if(position == 1) {
            p.setBehavior(null);
            fab.setLayoutParams(p);
            fab.setVisibility(View.GONE);
        } else {
            p.setBehavior(new FABBehavior());
            fab.setLayoutParams(p);
            fab.setVisibility(View.VISIBLE);
        }
    }

    private void showFab(int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationUtils.animateBounceIn(fab);
            }
        }, delay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        searchItem = menu.findItem(R.id.mi_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getResources().getString(R.string.background_text_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return presenter.onQueryTextChange(newText);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void updateLocation(final OnLocationUpdatedListener listener) {
        presenter.updateLocation(listener);
    }

    public String getCurrentQuery() {
        if(searchItem != null) {
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            return searchView.getQuery().toString();
        } else {
            return null;
        }
    }
}
