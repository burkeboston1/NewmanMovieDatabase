package com.bostonburke.newmanmoviedatabase.model;

import com.bostonburke.newmanmoviedatabase.model.cloud.OMDb;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Tests the correctness and speed of the NewmanTable class.
 *
 * Created by Boston on 7/9/2017.
 */

public class TestMovieTable {

    public static void main(String[] args) {

        NewmanTable table = new NewmanTable();
        OMDb omDb = new OMDb();
        ArrayList<MovieRecord> results = table.lookup("star wars");
        System.out.println("Found " + results.size() + " results.");
        for (MovieRecord mr : results) {
            System.out.println(mr.getOMDb(omDb).toString());
        }


    }
}
