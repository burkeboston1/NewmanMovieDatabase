package com.newman.moviedatabase.controller.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.newman.moviedatabase.controller.adapter.ViewPagerAdapter;
import com.newman.moviedatabase.controller.customview.NoSwipeViewPager;
import com.newman.moviedatabase.controller.fragment.BrowseFragment;
import com.newman.moviedatabase.model.MovieTable;
import com.newman.moviedatabase.R;
import com.newman.moviedatabase.model.cloud.Encoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class controls main activity of application for browsing the movie database listings.
 *
 * @author Tom Evans
 * @author Boston Burke
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener
{
    private Toolbar mToolbar;
    private NoSwipeViewPager mViewPager;
    private TabLayout mTabLayout;
    private AutoCompleteTextView mSearchView;
    private ImageButton mSearchButton;

    /** Holds the client entity that sends/receives requests */
    private static OkHttpClient mHTTPClient;


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

        mHTTPClient = new OkHttpClient();

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
        mSearchView.setOnEditorActionListener(hideOnDone);
        mSearchView.setOnItemClickListener(hideOnSelection);

        mSearchButton = (ImageButton)findViewById(R.id.search_button);
    }

//region SearchView Listeners
    /**
     * Listener hides keyboard when selection is made in the suggestions dropdown.
     */
    private AdapterView.OnItemClickListener hideOnSelection = new AdapterView.OnItemClickListener()
    {
        /**
         * Method hides the keyboard when selection is made in the suggestions dropdown.
         *
         * @param parent - The parent view containing the searchview.
         * @param view - The textview mSearchView.
         * @param position - The index of the suggestion in the menu that was selected.
         * @param id
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            View v = getCurrentFocus();
            if (v != null)
            {
                InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                Log.i("TAG", "Selection made: " + parent.getItemAtPosition(position));
            }


            /*
             * TODO: Start activity for the item that was selected by getting the text (movie title) at menu index 'position'
             *       and passing it in as an argument for the new activity transition. Transition starts new MovieDetainActivity
             *       on main UI thread while background thread queries OMDb by the title for the data that will be loaded into the
             *       newly transitioned-to activity.
             */
        }
    };

    /**
     * Inner-class holds the handler for sending an HTTP request for movie data.
     */
    public static class MovieDataTask extends AsyncTask<String, Void, JSONObject>
    {
        private Context mContext;
        private String mMovieTitle;

        public MovieDataTask(Context context)
        {
            mContext = context;
        }

        /**
         * Thread routine for OMBb request to query movie data in background
         * when dropdown suggestion is pressed.
         *
         * @param strings - The movie title to be used in the request.
         * @return - The JSON response from the OMDb query.
         */
        @Override
        protected JSONObject doInBackground(String... strings)
        {
            mMovieTitle = strings[0];
            return getMovieDataByTitle(strings[0]);
        }

        @Override
        protected void onPostExecute(JSONObject movieData)
        {
            super.onPostExecute(movieData);
            if (movieData.has("Error"))
            {
                AlertDialog.Builder errDialogBuilder = new AlertDialog.Builder(mContext, R.style.ErrorDialogTheme);
                errDialogBuilder.setTitle("Error!");
                errDialogBuilder.setMessage("Could not find \"" + mMovieTitle + "\"");
                errDialogBuilder.show();
            }
            else
            {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("MOVIE_DATA", movieData.toString());
                mContext.startActivity(intent);
            }
        }

        /**
         * Method to query the OMDb for the data associated with a movie title.
         * @param movieTitle
         */
        private JSONObject getMovieDataByTitle(String movieTitle)
        {
            String endpoint = "http://www.omdbapi.com/?apiKey=96ad6a43&t=" + Encoder.URIEncode(movieTitle);
            Request.Builder builder = new Request.Builder();
            builder.url(endpoint);
            builder.get();
            Request request = builder.build();

            Response response = null;
            try {
                response = mHTTPClient.newCall(request).execute();
                JSONObject jsonResponse = new JSONObject(response.body().string());
                return jsonResponse;
            }catch (JSONException | IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    /**
     * Listener hides keyboard when enter/done is pressed on keyboard.
     */
    private TextView.OnEditorActionListener hideOnDone = new TextView.OnEditorActionListener()
    {
        /**
         * Method hides keyboard when enter/done is pressed on keyboard.
         *
         * @param v - The textview mSearchView.
         * @param actionId - The ID for the action event.
         * @param event - The editor action event, pressing the enter key.
         * @return - True if action was executed, false if not.
         */
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
        {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE))
            {
                Log.i("TAG", "Done Pressed");
            }
            return false;
        }
    };
//endregion

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














