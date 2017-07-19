package com.bostonburke.newmanmoviedatabase.model.cloud;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class containing functions to query the OMDb movie database.
 *
 * @author Tom Evans
 */
public class OMDatabase
{
    /** Holds the client entity that sends/receives requests */
    private static OkHttpClient mClient;

    /**
     * Method to get all data for a movie by its title.
     *
     * @param movieTitle - The title of the movie.
     * @return - The data associated with the given movie title.
     */
    public static JSONObject getMovieData(String movieTitle)
    {
        MovieDataHandler handler = new MovieDataHandler();
        try{
            JSONObject movieData = handler.execute(movieTitle).get();
            return movieData;
        }catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }

        return null;
    }

    private static class MovieDataHandler extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(String... strings)
        {
            String endpoint = "http://www.omdbapi.com/?t" + strings[0];
            Request.Builder builder = new Request.Builder();
            builder.url(endpoint);
            builder.get();
            Request request = builder.build();

            Response response = null;
            try {
                response = mClient.newCall(request).execute();
                JSONObject jsonResponse = new JSONObject(response.body().string());
                return jsonResponse;
            }catch (JSONException | IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
