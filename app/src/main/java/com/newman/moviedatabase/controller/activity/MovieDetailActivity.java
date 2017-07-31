package com.newman.moviedatabase.controller.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.newman.moviedatabase.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class controls activity of application for viewing movie information.
 *
 * @author Tom Evans
 * @author Boston Burke
 */
public class MovieDetailActivity extends AppCompatActivity
{
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingLayout;
    private ImageView mMoviePoster;
    private JSONObject mMovieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        try {
            mMovieData = new JSONObject(intent.getStringExtra("MOVIE_DATA"));
            setupToolbar();
            setupCollapsingLayout(mMovieData.get("Title").toString());
            setupBackdropImage(mMovieData.get("Poster").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to set up the toolbar for the activity.
     */
    private void setupToolbar()
    {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Literally just set this up, it wont be null...
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Method to set up the collapsing toolbar layout for the activity.
     *
     * @param title - The title of the movie to be displayed on the toolbar.
     */
    private void setupCollapsingLayout(String title)
    {
        mCollapsingLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingLayout.setTitle(title);
    }

    /**
     * Method to set up the
     *
     * @param posterURL - The URL for the movie poster to be loaded into
     */
    private void setupBackdropImage(String posterURL)
    {
        mMoviePoster = (ImageView)findViewById(R.id.iv_poster_backdrop);
        Picasso.with(MovieDetailActivity.this).load(posterURL).into(mMoviePoster);
    }

    /**
     * Method to handle ActionBar options items being selected.
     *
     * @param item - The item that was selected.
     * @return - True if event was handled, false if nothing happened.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
