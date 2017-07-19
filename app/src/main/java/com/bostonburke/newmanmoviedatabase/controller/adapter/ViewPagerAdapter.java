package com.bostonburke.newmanmoviedatabase.controller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class to bind individual fragments to the viewpager of the activity.
 *
 * @author Tom Evans
 */
public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public void addFragment(Fragment frag, String title)
    {
        mFragments.add(frag);
        mFragmentTitles.add(title);
    }

    /**
     * Method to get a fragment in the tabbed viewpager.
     *
     * @param position - Index of fragment on viewpager.
     * @return - Fragment at index position.
     */
    @Override
    public Fragment getItem(int position)
    {
        return mFragments.get(position);
    }

    /**
     * Method to get the title of a page in the tabbed viewpager.
     *
     * @param position - Index of title in list.
     * @return - Title of tab on viewpager.
     */
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mFragmentTitles.get(position);
    }

    /**
     * Method to get the number of items in the list.
     * (Useful for loops and conditionals involving this object)
     *
     * @return - The number of items.
     */
    @Override
    public int getCount()
    {
        if (mFragments.size() != mFragmentTitles.size())
        {
            Log.e("APP", "Error in ViewPagerAdapter.java -> getCount()");
        }
        return mFragments.size();
    }
}
