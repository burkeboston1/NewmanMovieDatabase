package com.bostonburke.newmanmoviedatabase.controller.customview;

import android.content.Context;
import android.widget.Scroller;
import android.view.animation.DecelerateInterpolator;

/**
 * Custom scroller class for use in NoSwipeViewPager.
 *
 * @author Tom Evans
 */
public class CustomScroller extends Scroller
{
    /**
     * Default constructor.
     *
     * @param context - The context in which the view is used.
     */
    public CustomScroller(Context context)
    {
        super(context, new DecelerateInterpolator());
    }

    /**
     * Method to handle animation associated with scroll.
     *
     * @param startX - The starting position on the horizontal axis.
     * @param startY - The starting position on the vertical axis.
     * @param dx - The change in the x value on scroll.
     * @param dy - The change in the y value on scroll.
     * @param duration - The duration to animate the scroll.
     */
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration)
    {
        super.startScroll(startX, startY, dx, dy, 350); //350 is equal to 1 second
    }
}
