package com.traveler.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;

/**
 * @author vgrec, created on 2/24/15.
 */
public class AnimationUtils {

    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(2);

    public static void bounceAnimation(final ImageButton imageButton, final int onAnimationStartImageResource) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(imageButton, "scaleX", 0.95f, 1f);
        bounceAnimX.setDuration(400);
        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(imageButton, "scaleY", 0.95f, 1f);
        bounceAnimY.setDuration(400);
        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
        bounceAnimY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                imageButton.setImageResource(onAnimationStartImageResource);
            }
        });
        animatorSet.play(bounceAnimX).with(bounceAnimY);
        animatorSet.start();
    }
}
