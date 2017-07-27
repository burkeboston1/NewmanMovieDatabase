package com.newman.moviedatabase.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.newman.moviedatabase.R;
import com.newman.moviedatabase.controller.activity.MainActivity;
import com.newman.moviedatabase.controller.activity.MovieDetailActivity;
import com.newman.moviedatabase.model.cloud.OMDb;
import java.util.List;

/**
 * Adapter class to bind data to the layout of recyclerview items.
 *
 * @author Tom Evans
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackgroundResID;
    private List<String> mValues;
    private OMDb omdb = new OMDb();

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public String mBoundString;
        public final View mView;
        public final TextView mMovieTitle;

        public ViewHolder(View v)
        {
            super(v);
            mView = v;
            mMovieTitle = (TextView)v.findViewById(R.id.movie_title);
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + mMovieTitle.getText();
        }
    }

    public RecyclerViewAdapter(Context context, List<String> items)
    {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackgroundResID = mTypedValue.resourceId;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent, false);
        v.setBackgroundResource(mBackgroundResID);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int index)
    {
        vh.mBoundString = mValues.get(index);
        vh.mMovieTitle.setText(mValues.get(index));
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                MainActivity.MovieDataTask task = new MainActivity.MovieDataTask(v.getContext());
                Log.d("TAG", "The string passed into the asynctask: " + mValues.get(index));
                task.execute(mValues.get(index));
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mValues.size();
    }
}
