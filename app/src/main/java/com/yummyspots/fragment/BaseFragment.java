package com.yummyspots.fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;
import com.vlonjatg.progressactivity.ProgressActivity;
import com.yummyspots.R;

public abstract class BaseFragment extends Fragment {
    public enum DisplayStatus {LOADING, NO_DATA,NO_DATA_AND_BADGES, CONNECTION_ERROR, UNKNOWN_ERROR}

    public void displayStatus(ProgressActivity activity, BaseFragment.DisplayStatus status) {
        switch (status) {
            case LOADING:
                Drawable cloudDownload = new IconDrawable(getContext(), Iconify.IconValue.zmdi_cloud_download)
                        .colorRes(android.R.color.white);
                activity.showEmpty(cloudDownload, getResources().getString(R.string.loading_data),
                        getResources().getString(R.string.wait_minute));
                break;

            case NO_DATA:
                Drawable basket = new IconDrawable(getContext(), Iconify.IconValue.zmdi_shopping_basket)
                        .colorRes(android.R.color.white);
                activity.showEmpty(basket, getResources().getString(R.string.no_get_data),
                        getResources().getString(R.string.check_data));
                break;

            case NO_DATA_AND_BADGES:
                Drawable basketempty = new IconDrawable(getContext(), Iconify.IconValue.zmdi_shopping_basket)
                        .colorRes(android.R.color.white);
                activity.showEmpty(basketempty, getResources().getString(R.string.no_get_data),
                        getResources().getString(R.string.check_data_or_badge));
                break;

            case CONNECTION_ERROR:
                Drawable wiFi = new IconDrawable(getContext(), Iconify.IconValue.zmdi_network_wifi_off)
                        .colorRes(android.R.color.white);
                activity.showEmpty(wiFi, getResources().getString(R.string.connection_error),
                        getResources().getString(R.string.check_your_internet));
                break;

            case UNKNOWN_ERROR:
                Drawable error = new IconDrawable(getContext(), Iconify.IconValue.zmdi_alert_polygon)
                        .colorRes(android.R.color.white);
                activity.showEmpty(error, getResources().getString(R.string.no_get_data),
                        getResources().getString(R.string.check_data));
                break;
        }
    }

    public abstract void reset();
}
