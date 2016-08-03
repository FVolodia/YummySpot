package com.yummyspots.util;

import android.content.Context;
import android.os.Build;


public class DeviceUtils {
    public static boolean v5(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean v6(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
