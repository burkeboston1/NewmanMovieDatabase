package com.bostonburke.newmanmoviedatabase.controller.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bostonburke.newmanmoviedatabase.R;
import com.bostonburke.newmanmoviedatabase.controller.activity.MovieDetailActivity;
import com.bostonburke.newmanmoviedatabase.model.cloud.OMDatabase;

import org.json.JSONObject;

import java.io.Serializable;
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
    public void onBindViewHolder(final ViewHolder vh, int index)
    {
        vh.mBoundString = mValues.get(index);
        vh.mMovieTitle.setText(mValues.get(index));
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Context ctxt = v.getContext();

                //Pass movie data JSON as string to new activity, re-create as JSONObject in receiving activity
                Intent intent = new Intent(ctxt, MovieDetailActivity.class);
                intent.putExtra("MOVIE_DATA", OMDatabase.getMovieData(vh.mMovieTitle.getText().toString()).toString());
                ctxt.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mValues.size();
    }
}
