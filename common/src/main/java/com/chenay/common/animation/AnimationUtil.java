package com.chenay.common.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public class AnimationUtil {

    /**
     * 左右反转
     * @param oldView
     * @param newView
     * @param time
     */
    public static void flipHorizontal(final View oldView, final View newView, final long time) {

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(oldView, "rotationY", 0,-90);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(newView, "rotationY", 90,0);
        flipAnimatorXViewShow(oldView,newView,time,animator1,animator2);
    }

    /**
     * 上下反转
     * @param oldView
     * @param newView
     * @param time
     */
    public static void flipVerticel(final View oldView, final View newView, final long time) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(oldView, "rotationX", 0, 90);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(newView, "rotationX", -90, 0);
        flipAnimatorXViewShow(oldView,newView,time,animator1,animator2);
    }

    public static void flipAnimatorXViewShow(final View oldView, final View newView, final long time, ObjectAnimator animator1, final ObjectAnimator animator2) {
        animator2.setInterpolator(new OvershootInterpolator(2.0f));

        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                oldView.setVisibility(View.GONE);
                animator2.setDuration(time).start();
                newView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator1.setDuration(time).start();
    }
}
