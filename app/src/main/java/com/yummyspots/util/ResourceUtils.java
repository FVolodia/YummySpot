package com.yummyspots.util;

import android.content.Context;

import com.yummyspots.R;

import java.util.Random;

/**
 * Created by FVolodia on 22.12.15.
 */
public class ResourceUtils {

    public static int randomColor(Context context, int seed) {
        int[] colors = context.getResources().getIntArray(R.array.categoryColors);
        Random r = new Random();
        r.setSeed(seed);
        int colorIndex = r.nextInt(colors.length);
        return colors[colorIndex];
    }
}
