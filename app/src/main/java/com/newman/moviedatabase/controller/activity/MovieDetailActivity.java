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
    private TextView mSimpleText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        JSONObject movieData = null;
        Intent intent = getIntent();
        try {
            movieData = new JSONObject(intent.getStringExtra("MOVIE_DATA"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSimpleText = (TextView)findViewById(R.id.simple_text);
        if (movieData != null)
        {
            mSimpleText.setText(movieData.toString());
        }
    }
}
