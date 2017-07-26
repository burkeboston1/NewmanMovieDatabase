package com.bostonburke.newmanmoviedatabase.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Most recent dump of Newman's database.
 *
 * Created by Boston on 7/9/2017.
 */
public class MovieTable {

    private ArrayList<MovieRecord> movieTable;

    /**
     * Creates a MovieTable object.
     */
    public MovieTable() {
        movieTable = new ArrayList<MovieRecord>();
        buildTable();
    }

    /**
     * Searches the MovieTable for records like 'query'.
     */
    public ArrayList<MovieRecord> lookup(String query) {
        ArrayList<MovieRecord> results = new ArrayList<MovieRecord>();
        for (MovieRecord mr : movieTable) {
            if (mr.contains(query)) {
                results.add(mr);
            }
        }
        return results;
    }

    /**
     * Gets Newman's latest database dump.
     */
    private File getCurrentDump() {
        return new File("C:\\Users\\Boston\\AndroidStudioProjects\\NewmanMovieDatabase\\app\\src\\main\\assets\\dvd_info.txt");
    }

    /**
     *  Parse the lines of the file into MovieRecord objects.
     */
    private void buildTable(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(getCurrentDump()));
            String line;
            reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                MovieRecord mr = MovieRecord.parseMovieRecord(line);
                if (mr != null) {
                    movieTable.add(mr);
                }
            }
        }
        catch (java.io.FileNotFoundException e) {
            System.out.println("ERROR: dump file not found");
        }
        catch (java.io.IOException e) {
            // end of file
        }
    }
}
