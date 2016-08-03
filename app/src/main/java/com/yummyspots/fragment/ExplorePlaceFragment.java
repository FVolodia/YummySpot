package com.yummyspots.fragment;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yummyspots.R;
import com.yummyspots.activity.MainActivity;
import com.yummyspots.adapter.PlaceClickListener;
import com.yummyspots.adapter.PlacesAdapter;
import com.yummyspots.model.Place;
import com.yummyspots.presenter.ExplorePlaceFragmentPresenter;
import com.yummyspots.util.Alert;
import com.vlonjatg.progressactivity.ProgressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartlocation.OnLocationUpdatedListener;

/**
 * Created by FVolodia on 13.12.15.
 */
public class ExplorePlaceFragment extends BaseFragment {

    @Bind(R.id.recycleView)
    RecyclerView recyclerView;

    @Bind(R.id.swipeRefreshExplorePlaceFragment)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.progressActivityExplore)
    ProgressActivity progressActivity;

    private PlacesAdapter adapter;
    private ExplorePlaceFragmentPresenter presenter;


    public static ExplorePlaceFragment getInstance() {
        Bundle args = new Bundle();
        ExplorePlaceFragment explorePlaceFragment = new ExplorePlaceFragment();
        explorePlaceFragment.setArguments(args);
        return explorePlaceFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new ExplorePlaceFragmentPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_explore_place_fragment, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        adapter = new PlacesAdapter(getContext()).withPlaceClickListener(new PlaceClickListener() {
            @Override
            public void onPlaceClick(View view, int position, Place place) {
                presenter.goToPlaceDetails(place);
            }

            @Override
            public void onLikeClick(View view, Place place) {
                Alert.show(getContext(), "You Liked this place " + place.getTitle());
            }
        });
        recyclerView.setAdapter(adapter);

        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.primary));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity) getActivity()).updateLocation(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        presenter.queryPlaces();
                    }
                });
            }
        });

        presenter.queryPlaces();

        return view;
    }

    public void setRefreshing(boolean refreshing) {
        refreshLayout.setRefreshing(refreshing);
    }

    public void onError(Throwable e) {
        adapter.setItems(new ArrayList<Place>());
        if (e != null) {
            displayStatus(progressActivity, DisplayStatus.CONNECTION_ERROR);
            progressActivity.setVisibility(View.VISIBLE);
        } else progressActivity.setVisibility(View.GONE);
    }

    public void updateList(List<Place> places) {
        adapter.setItems(places);
        if (places.isEmpty()) {
            displayStatus(progressActivity, DisplayStatus.NO_DATA);
            progressActivity.setVisibility(View.VISIBLE);
        } else progressActivity.setVisibility(View.GONE);
        setRefreshing(false);
    }

    @Override
    public void reset() {
        presenter.queryPlaces();
    }
}
