package com.newman.moviedatabase.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.newman.moviedatabase.R;
import com.newman.moviedatabase.controller.adapter.RecyclerViewAdapter;

import android.support.v7.widget.DividerItemDecoration;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment
{
    final String[] strs = {
            "The Departed", "Movie Title #2", "Movie Title #3", "The Departed", "Movie Title #5", "Movie Title #6", "Movie Title #7", "Movie Title #8", "Movie Title #9", "Movie Title #10",
            "Movie Title #11", "Movie Title #12", "Movie Title #13", "Movie Title #14", "Movie Title #15", "Movie Title #16", "Movie Title #17", "Movie Title #18", "Movie Title #19", "Movie Title #20",
            "Movie Title #21", "Movie Title #22", "Movie Title #23", "Movie Title #24", "Movie Title #25", "Movie Title #26", "Movie Title #27", "Movie Title #28", "Movie Title #29", "Movie Title #30",
            "Movie Title #31", "Movie Title #32", "Movie Title #33", "Movie Title #34", "Movie Title #35", "Movie Title #36", "Movie Title #37", "Movie Title #38", "Movie Title #39", "Movie Title #40",
            "Movie Title #41", "Movie Title #42", "Movie Title #43", "Movie Title #44", "Movie Title #45", "Movie Title #46", "Movie Title #47", "Movie Title #48", "Movie Title #49", "Movie Title #50",
            "Movie Title #51", "Movie Title #52", "Movie Title #53", "Movie Title #54", "Movie Title #55", "Movie Title #56", "Movie Title #57", "Movie Title #58", "Movie Title #59", "Movie Title #60",
            "Movie Title #61", "Movie Title #62", "Movie Title #63", "Movie Title #64", "Movie Title #65", "Movie Title #66", "Movie Title #67", "Movie Title #68", "Movie Title #69", "Movie Title #70",
            "Movie Title #71", "Movie Title #72", "Movie Title #73", "Movie Title #74", "Movie Title #75", "Movie Title #76", "Movie Title #77", "Movie Title #78", "Movie Title #79", "Movie Title #80",
            "Movie Title #81", "Movie Title #82", "Movie Title #83", "Movie Title #84", "Movie Title #85", "Movie Title #86", "Movie Title #87", "Movie Title #88", "Movie Title #89", "Movie Title #90",
            "Movie Title #91", "Movie Title #92", "Movie Title #93", "Movie Title #94", "Movie Title #95", "Movie Title #96", "Movie Title #97", "Movie Title #98", "Movie Title #99", "Movie Title #100",
            "Movie Title #101", "Movie Title #102", "Movie Title #103", "Movie Title #104", "Movie Title #105", "Movie Title #106", "Movie Title #107", "Movie Title #108", "Movie Title #109", "Movie Title #110",
            "Movie Title #111", "Movie Title #112", "Movie Title #113", "Movie Title #114", "Movie Title #115", "Movie Title #116", "Movie Title #117", "Movie Title #118", "Movie Title #119", "Movie Title #120",
            "Movie Title #121", "Movie Title #122", "Movie Title #123", "Movie Title #124", "Movie Title #125", "Movie Title #126", "Movie Title #127", "Movie Title #128", "Movie Title #129", "Movie Title #130",
            "Movie Title #131", "Movie Title #132", "Movie Title #133", "Movie Title #134", "Movie Title #135", "Movie Title #136", "Movie Title #137", "Movie Title #138", "Movie Title #139", "Movie Title #140",
            "Movie Title #141", "Movie Title #142", "Movie Title #143", "Movie Title #144", "Movie Title #145", "Movie Title #146", "Movie Title #147", "Movie Title #148", "Movie Title #149", "Movie Title #150",
            "Movie Title #151", "Movie Title #152", "Movie Title #153", "Movie Title #154", "Movie Title #155", "Movie Title #156", "Movie Title #157", "Movie Title #158", "Movie Title #159", "Movie Title #160",
            "Movie Title #161", "Movie Title #162", "Movie Title #163", "Movie Title #164", "Movie Title #165", "Movie Title #166", "Movie Title #167", "Movie Title #168", "Movie Title #169", "Movie Title #170",
            "Movie Title #171", "Movie Title #172", "Movie Title #173", "Movie Title #174", "Movie Title #175", "Movie Title #176", "Movie Title #177", "Movie Title #178", "Movie Title #179", "Movie Title #180",
            "Movie Title #181", "Movie Title #182", "Movie Title #183", "Movie Title #184", "Movie Title #185", "Movie Title #186", "Movie Title #187", "Movie Title #188", "Movie Title #189", "Movie Title #190",
            "Movie Title #191", "Movie Title #192", "Movie Title #193", "Movie Title #194", "Movie Title #195", "Movie Title #196", "Movie Title #197", "Movie Title #198", "Movie Title #199", "Movie Title #200"};
    private LinearLayout mBaseLayout;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mBaseLayout = (LinearLayout)inflater.inflate(R.layout.fragment_browse, container, false);
        setupRecyclerView();
        return mBaseLayout;
    }

    /**
     * Method to set up the recyclerview contained in the fragment.
     */
    private void setupRecyclerView()
    {
        mRecyclerView = (RecyclerView)mBaseLayout.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getActivity(), new ArrayList<>(Arrays.asList(strs))));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }
}
