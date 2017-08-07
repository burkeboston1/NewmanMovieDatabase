package com.newman.moviedatabase.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newman.moviedatabase.R;
import com.newman.moviedatabase.controller.activity.MainActivity;
import com.newman.moviedatabase.model.MovieRecord;
import com.newman.moviedatabase.network.ImageManager;

import java.util.ArrayList;

/**
 * Adapter for lazy-loaded bitmap images in a recycler view item.
 *
 * @author Tom Evans
 */
public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.ViewHolder>
{
    private ArrayList<MovieRecord> mMovies;
    private Activity mActivity;
    private ImageManager mImageManager;
    private int mBackgroundResourceID;

    public RecyclerItemAdapter(Activity a, int backgroundResID, ArrayList<MovieRecord> movies)
    {
        this.mActivity = a;
        this.mBackgroundResourceID = backgroundResID;
        this.mMovies = movies;
        this.mImageManager = new ImageManager(mActivity.getApplicationContext());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View view;
        public TextView title;
        public TextView year;
        public ImageView poster;

        public ViewHolder(View v)
        {
            super(v);

            view = v;
            title = (TextView)v.findViewById(R.id.movie_title_textview);
            year = (TextView)v.findViewById(R.id.year_textview);
            poster = (ImageView)v.findViewById(R.id.poster_imageview);
            v.setTag(this);
        }
    }


    /**
     * Method called when the recycler item is created.
     *
     * @param parent - The parent view for the item.
     * @param viewType - The type of view to create.
     * @return - The viewholder that was created.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent, false);
        v.setBackgroundResource(mBackgroundResourceID);
        return new ViewHolder(v);
    }


    /**
     * Method called as data is bound to the recycler item.
     *
     * @param vh - The viewholder to bind data to.
     * @param index - The index of the item to bind.
     */
    @Override
    public void onBindViewHolder(ViewHolder vh, final int index)
    {
        MovieRecord movie = mMovies.get(index);

        vh.title.setText(movie.getTitle());
        vh.year.setText(movie.getYear());
        vh.poster.setTag(movie.getPosterURL());
        mImageManager.displayImage(movie.getPosterURL(), vh.poster, R.drawable.ic_default_image);
        vh.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                MainActivity.MovieDataTask task = new MainActivity.MovieDataTask(v.getContext());
                task.execute(mMovies.get(index).getTitle());
            }
        });
    }


    /**
     * Method to get the number of items in the recyclerview.
     *
     * @return - The number of items in the recyclerview.
     */
    @Override
    public int getItemCount()
    {
        return mMovies.size();
    }
}
