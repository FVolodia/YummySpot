package com.yummyspots.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yummyspots.R;
import com.yummyspots.adapter.PlaceClickListener;
import com.yummyspots.adapter.PlacesAdapter;
import com.yummyspots.model.Badge;
import com.yummyspots.model.Place;
import com.yummyspots.presenter.MyPlaceFragmentPresenter;
import com.yummyspots.util.Alert;
import com.yummyspots.view.BadgeView;
import com.vlonjatg.progressactivity.ProgressActivity;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by FVolodia on 12.10.15.
 */
public class MyPlaceFragment extends BaseFragment implements Filterable {
    private View view;

    @Bind(R.id.recycleView)
    RecyclerView recyclerView;

    PlacesAdapter adapter;
    private LayoutInflater inflater;

    @Bind(R.id.badgeContainerMainView)
    LinearLayout badgeContainerMainView;

    @Bind(R.id.progressActivityMyPlaceFragment)
    ProgressActivity progressActivity;

    private MyPlaceFragmentPresenter presenter;

    public static MyPlaceFragment getInstance() {
        Bundle args = new Bundle();
        MyPlaceFragment myPlaceFragment = new MyPlaceFragment();
        myPlaceFragment.setArguments(args);
        return myPlaceFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new MyPlaceFragmentPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_my_place_fragment, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        this.inflater = LayoutInflater.from(getContext());

        adapter = new PlacesAdapter(getContext()).withPlaceClickListener(new PlaceClickListener() {
            @Override
            public void onPlaceClick(View view, int position, Place place) {
                presenter.goToPlaceDetail(place);
            }

            @Override
            public void onLikeClick(View view, Place place) {
                Alert.show(getContext(), "You Liked this place " + place.getTitle());
            }
        });
        recyclerView.setAdapter(adapter);

        Collection<Badge> all = Badge.badgeList();
        for (final Badge b : all) {
            final BadgeView bView = createBadgeMain(b);
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.queryPlace();
    }

    @Override
    public void reset() {
        presenter.reset();
        presenter.queryPlace();
        for(int i = 0; i < badgeContainerMainView.getChildCount(); i++) {
            BadgeView badgeView = (BadgeView) badgeContainerMainView.getChildAt(i);
            badgeView.setSelected(false);
        }

    }

    public void updateList(List<Place> data) {
        adapter.setItems(data);
        emptyData(data);

    }

    private BadgeView createBadgeMain(final Badge b) {
        final BadgeView result = (BadgeView) inflater.inflate(R.layout.view_badge, badgeContainerMainView, false);
        result.setIconCodeRes(b.getCodeName());
        result.setHightLightColor(b.getColor());
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.selectBadge(result, b);
                presenter.queryPlace();
            }
        });
        badgeContainerMainView.addView(result);
        return result;
    }

    @Override
    public void filterFromKeyWord(String keyWord) {
        presenter.queryPlace(keyWord);
    }

    public void emptyData(List<Place> places) {
        if (places.isEmpty()) {
            displayStatus(progressActivity, DisplayStatus.NO_DATA_AND_BADGES);
            progressActivity.setVisibility(View.VISIBLE);
        } else progressActivity.setVisibility(View.GONE);

    }

}
