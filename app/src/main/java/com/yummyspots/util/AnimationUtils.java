package com.yummyspots.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;


public final class AnimationUtils {
    public static final void animateBounceIn(final View target) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1, 1, 1),
                ObjectAnimator.ofFloat(target, "scaleX", 0.3f, 1.05f, 0.9f, 1),
                ObjectAnimator.ofFloat(target, "scaleY", 0.3f, 1.05f, 0.9f, 1)
        );
        set.setDuration(500);
        target.setVisibility(View.VISIBLE);
        set.start();
    }
}
