package com.bostonburke.newmanmoviedatabase.controller.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bostonburke.newmanmoviedatabase.controller.adapter.ViewPagerAdapter;
import com.bostonburke.newmanmoviedatabase.controller.customview.NoSwipeViewPager;
import com.bostonburke.newmanmoviedatabase.controller.fragment.BrowseFragment;
import com.bostonburke.newmanmoviedatabase.model.MRParcel;
import com.bostonburke.newmanmoviedatabase.model.MovieTable;
import com.bostonburke.newmanmoviedatabase.R;

/**
 * View controller for activity_main.xml.
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener
{
    private Toolbar mToolbar;
    private NoSwipeViewPager mViewPager;
    private TabLayout mTabLayout;
    private SearchView.SearchAutoComplete mSearchView;
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

        mSearchView = (SearchView.SearchAutoComplete)findViewById(R.id.search_view);
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

    /**
     * Called when the titleSearch button is clicked
     *
     * @param view the view that was clicked
     */
    /**public void searchButtonClicked(View view) {
        String query = editText.getText().toString();

        // Check for empty string
        if( !query.isEmpty() ) {
            MRParcel results = new MRParcel(table.searchFor(query));

            // If the results set was empty, let the user know in
            // activity_main
            if (results.get().isEmpty()) {
                TextView nrFlag = (TextView) findViewById(R.id.noResultsFlag);
                nrFlag.setVisibility(View.VISIBLE);
            } else {
                Intent intent = new Intent(this, DisplayResults.class);
                intent.putExtra("results", results);
            }
        }
    }*/

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














