package com.bostonburke.newmanmoviedatabase.controller.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Custom viewpager to prevent swipe functionality between tabs.
 *
 * @author Tom Evans
 */
public class NoSwipeViewPager extends ViewPager
{
    /**
     * Default constructor.
     *
     * @param context - Context in which the view exists.
     */
    public NoSwipeViewPager(Context context)
    {
        super(context);
        setScroller();
    }

    /**
     * Defualt constructor.
     *
     * @param context - Context in which the view wxists.
     * @param attrs - The set of attributes for the custom view.
     */
    public NoSwipeViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setScroller();
    }

    /**
     * Listener method for intercepting a touch event on the viewpager.
     *
     * @param event - The touch event to intercept.
     * @return - False, prevents swipes from doing anything.
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        return false;
    }

    /**
     * Listener method for intercepting a touch event on the viewpager.
     *
     * @param event - The touch event to intercept.
     * @return - False, prevents swipes from doing anything.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return false;
    }

    /**
     * Method to set the scroller associated with the viewpager.
     * Allows for recyclerview to override touch disabling.
     */
    private void setScroller()
    {
        try {
            Class<?> viewPager = ViewPager.class;
            Field scroller = viewPager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new CustomScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
