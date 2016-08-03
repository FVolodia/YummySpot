package com.yummyspots;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.yummyspots.database.DatabaseHelper;
import com.karumi.dexter.Dexter;
import io.fabric.sdk.android.Fabric;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if(!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
        DatabaseHelper.initHelper(this);
        Dexter.initialize(this);
    }

    @Override
    public void onTerminate() {
        DatabaseHelper.releaseHelper();
        super.onTerminate();
    }
}
