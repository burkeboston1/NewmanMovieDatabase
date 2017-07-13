package com.bostonburke.newmanmoviedatabase.model;

import java.util.ArrayList;

/**
 * Tests the correctness and speed of the MovieTable class.
 *
 * Created by Boston on 7/9/2017.
 */

public class TestMovieTable {

    public static void main(String[] args) {
        MovieTable table = new MovieTable();
        ArrayList<MovieRecord> results = table.searchFor("star wars");
        System.out.println("MovieTable returned " + results.size() + " results");
        for (MovieRecord mr : results) {
            System.out.println(mr.getCallNumber() + ": " + mr.getTitle());
        }
    }
}
