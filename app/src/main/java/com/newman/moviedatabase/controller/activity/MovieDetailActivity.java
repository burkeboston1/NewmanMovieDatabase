package com.newman.moviedatabase.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.newman.moviedatabase.R;

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
    private JSONObject mMovieData;
    private TextView mTitle, mYear, mRating, mFullJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        try {
            mMovieData = new JSONObject(intent.getStringExtra("MOVIE_DATA"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mTitle = (TextView)findViewById(R.id.title_value);
        mYear = (TextView)findViewById(R.id.year_value);
        mRating = (TextView)findViewById(R.id.rating_value);
        mFullJSON = (TextView)findViewById(R.id.fulljson_value);

        if (mMovieData.has("Error"))
        {
            mTitle.setText("Error --> Movie doesn't exist!");
            mYear.setText("Error --> Movie doesn't exist!");
            mRating.setText("Error --> Movie doesn't exist!");
        }
        else
        {
            try {
                mTitle.setText(mMovieData.getString("Title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                mYear.setText(mMovieData.getString("Year"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                mRating.setText(mMovieData.getString("Rated"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            mFullJSON.setText("\n" + mMovieData.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}