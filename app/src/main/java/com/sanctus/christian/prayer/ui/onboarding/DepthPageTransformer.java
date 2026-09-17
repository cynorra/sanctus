package com.sanctus.christian.prayer.ui.onboarding;

import android.view.View;
import androidx.viewpager2.widget.ViewPager2;

/** Subtle parallax fade+scale as pages swipe past, instead of a flat instant cut. */
public class DepthPageTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) {
            page.setAlpha(0f);
        } else if (position <= 0) {
            page.setAlpha(1f);
            page.setTranslationX(0f);
            page.setScaleX(1f);
            page.setScaleY(1f);
        } else if (position <= 1) {
            page.setAlpha(1 - position);
            page.setTranslationX(page.getWidth() * -position * 0.25f);
            float scale = 0.88f + (1 - Math.abs(position)) * 0.12f;
            page.setScaleX(scale);
            page.setScaleY(scale);
        }
    }
}
