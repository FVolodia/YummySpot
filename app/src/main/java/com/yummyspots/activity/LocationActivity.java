package com.yummyspots.activity;

import android.content.Intent;
import android.os.Bundle;

import com.yummyspots.R;
import com.yummyspots.presenter.LocationActivityPresenter;

/**
 * Created by FVolodia on 02.12.15.
 */
public class LocationActivity extends BaseActivity {

    private LocationActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        presenter = new LocationActivityPresenter(this);
//        presenter.goToMainActivity();
        presenter.processLocation();
    }

    @Override
    protected void onDestroy() {
        presenter.stopLocationService();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (presenter.getProvider() != null) {
            presenter.getProvider().onActivityResult(requestCode, resultCode, data);
        }
    }
}
