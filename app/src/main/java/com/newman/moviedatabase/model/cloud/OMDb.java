package com.newman.moviedatabase.model.cloud;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class
 */
public class OMDb
{
    /** Holds the client entity that sends/receives requests */
    private OkHttpClient mClient;

    /* API Key for OMDb */
    private String apiKey = "96ad6a43";

    /**
     * Method to get all data for a movie by its title.
     *
     * @param movieTitle - The title of the movie.
     * @return - The data associated with the given movie title.
     */
    public JSONObject getMovieDataByTitle(String movieTitle)
    {
        MovieDataHandler handler = new MovieDataHandler();
        String titleParam = "t=\"" + movieTitle + "\"";
        try{
            JSONObject movieData = handler.execute(titleParam).get();
            return movieData;
        }catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method to get all data for a movie by its id
     *
     * @param movieId - The id of the movie.
     * @return - The data associated with the given movie title.
     */
    public JSONObject getMovieDataById(String movieId)
    {
        MovieDataHandler handler = new MovieDataHandler();
        String titleParam = "i=\"" + movieId + "\"";
        try{
            JSONObject movieData = handler.execute(titleParam).get();
            return movieData;
        }catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Searches OMDb for all records matching "title".
     *
     * @param movieTitle - the title of the movie
     * @return - JSON object of results
     */
    public JSONObject getMoviesWithTitle(String movieTitle) {
        MovieDataHandler handler = new MovieDataHandler();
        String titleParam = "s=\"" + movieTitle + "\"";
        try{
            JSONObject movieData = handler.execute(titleParam).get();
            return movieData;
        }catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Searches OMDb for all records matching year "year".
     *
     * @param movieYear - the year the movie was published
     * @return - JSON object of results
     */
    public JSONObject getMoviesFromYear(String movieYear) {
        MovieDataHandler handler = new MovieDataHandler();
        String titleParam = "y=\"" + movieYear + "\"";
        try{
            JSONObject movieData = handler.execute(titleParam).get();
            return movieData;
        }catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }

        return null;
    }

    private class MovieDataHandler extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(String... strings)
        {
            String endpoint = "http://www.omdbapi.com/?apiKey=" + apiKey + strings[0];
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
