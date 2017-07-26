package com.newman.moviedatabase.controller.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.newman.moviedatabase.controller.adapter.ViewPagerAdapter;
import com.newman.moviedatabase.controller.customview.NoSwipeViewPager;
import com.newman.moviedatabase.controller.fragment.BrowseFragment;
import com.newman.moviedatabase.model.MovieTable;
import com.newman.moviedatabase.R;

/**
 * View controller for activity_main.xml.
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener
{
    private Toolbar mToolbar;
    private NoSwipeViewPager mViewPager;
    private TabLayout mTabLayout;
    private AutoCompleteTextView mSearchView;
    private ImageButton mSearchButton;


    // Attributes
    MovieTable table;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolBar();
        setupNoSwipeViewPager();
        setupTabLayout();

        //table = new MovieTable();

        // Get the search field
        //editText = (EditText) findViewById(R.id.search_field);
    }

    /**
     * Method to set up the toolbar view.
     */
    private void setupToolBar()
    {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Prevent the name of the app from being shown on the toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSearchView = (AutoCompleteTextView) findViewById(R.id.search_view);
        mSearchButton = (ImageButton)findViewById(R.id.search_button);
    }

    /**
     * Method to set up the custom viewpager view.
     */
    private void setupNoSwipeViewPager()
    {
        mViewPager = (NoSwipeViewPager)findViewById(R.id.viewpager);
        if (mViewPager != null)
        {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

            BrowseFragment aToZ = new BrowseFragment();
            adapter.addFragment(aToZ, "A-Z");

            BrowseFragment rand = new BrowseFragment();
            adapter.addFragment(rand, "Random");

            BrowseFragment thirdExample = new BrowseFragment();
            adapter.addFragment(thirdExample, "Example Tab");

            mViewPager.setAdapter(adapter);
            mViewPager.addOnPageChangeListener(this);
        }
    }

    /**
     * Method to set up the tab layout view.
     */
    private void setupTabLayout()
    {
        mTabLayout = (TabLayout)findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onPageSelected(int position)
    {
        //TODO: Sort items by tab type they are under...
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
        //Empty
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
        //Empty
    }
}














