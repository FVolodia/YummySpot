package com.yummyspots.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yummyspots.R;
import com.yummyspots.model.Badge;
import com.yummyspots.model.Place;
import com.yummyspots.presenter.PlaceViewActivityPresenter;
import com.yummyspots.util.Alert;
import com.yummyspots.util.ResourceUtils;
import com.yummyspots.view.BadgeView;
import com.yummyspots.view.PlaceDetailView;
import com.github.johnkil.print.PrintDrawable;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.Collection;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by FVolodia on 10.09.15.
 */
public class PlaceViewActivity extends BaseActivity {


    @Bind(R.id.pdvPhone)
    PlaceDetailView phoneNumber;

    @Bind(R.id.pdvSite)
    PlaceDetailView webSite;

    @Bind(R.id.pdvLocation)
    PlaceDetailView location;

    @Bind(R.id.pdvCategoryPlace)
    PlaceDetailView categoryPlace;

    @Bind(R.id.imvPlacePicture)
    ImageView imvPlaceActivity;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Bind(R.id.anim_toolbar)
    Toolbar toolbar;

    @Bind(R.id.fabButton)
    FloatingActionButton floatingActionButton;

    @Bind(R.id.badgeContainer)
    LinearLayout badgeContainer;

    @Bind(R.id.titleView)
    View titleInfo;

    @Bind(R.id.coordinatorLayout)
    ViewGroup coordinator;

    private LayoutInflater inflater;
    private PlaceViewActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeview);

        presenter = new PlaceViewActivityPresenter(this);
        inflater = LayoutInflater.from(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter.init(getIntent());


        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }


        Drawable icon = new PrintDrawable.Builder(PlaceViewActivity.this)
                .iconTextRes(R.string.ic_material_directions)
                .iconColorRes(android.R.color.white)
                .iconSizeRes(R.dimen.icon_size)
                .iconFont("fonts/material-icon-font.ttf")
                .build();
        floatingActionButton.setImageDrawable(icon);
    }

    private BadgeView createBadge(Badge b) {
        BadgeView result = (BadgeView) inflater.inflate(R.layout.view_badge, badgeContainer, false);
        result.setIconCodeRes(b.getCodeName());
        result.setHightLightColor(b.getColor());
        badgeContainer.addView(result);
        return result;
    }

    @OnClick(R.id.pdvPhone)
    public void clickCall() {
        presenter.call();
    }

    @OnClick(R.id.pdvSite)
    public void goToSite() {
        presenter.web();
    }

    @OnClick(R.id.fabButton)
    public void goToPlace() {
        presenter.clickNavigation();
    }

    @OnClick(R.id.pdvLocation)
    public void showSnackInfo() {
        Alert.snackBar(coordinator, getResources().getString(R.string.tap_fab));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void showPlace(final Place place) {
        if (!TextUtils.isEmpty(place.getImagePlaceUrl())) {
            Picasso.with(PlaceViewActivity.this).load(place.getImagePlaceUrl()).into(imvPlaceActivity);
        }
        collapsingToolbar.setTitle(place.getTitle());

        int color = ResourceUtils.randomColor(this, place.getFoursquareID().hashCode());
        titleInfo.setBackgroundColor(color);
        collapsingToolbar.setContentScrimColor(color);
        collapsingToolbar.setStatusBarScrimColor(color);


        setOrHide(phoneNumber, place.getPhoneNumb());
        setOrHide(location, place.getLocation());
        setOrHide(webSite, place.getSite());
        setOrHide(categoryPlace, place.getCategory());

        Collection<Badge> all = Badge.badgeList();
        for (final Badge b : all) {
            final BadgeView bView = createBadge(b);
            if (place.getBadges().contains(b.getId())) {
                bView.setSelected(true);
            }

            bView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        boolean addedAsNew = presenter.updateBadges(b, bView.isSelected());
                        if (addedAsNew) {
                            Alert.snackBar(coordinator, place.getTitle() + " " + getResources().getString(R.string.add_to_bd));
                        }
                    } catch (SQLException e) {
                        Alert.show(PlaceViewActivity.this, e.getMessage());
                    }
                }
            });
        }

    }

    private void setOrHide(PlaceDetailView view, String data) {
        if (!TextUtils.isEmpty(data)) {
            view.setMainText(data);
            view.setVisibility(View.VISIBLE);
        } else view.setVisibility(View.GONE);
    }

}
