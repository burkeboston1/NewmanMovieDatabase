package com.bostonburke.newmanmoviedatabase.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * A list of MovieRecord objects parsed from the most recent dump of Newman's movie database.
 *
 * NewmanTable makes API calls to OMDb to obtain extra information about the movie including plot
 * descriptions, pretty titles, and posters.
 *
 * Created by Boston on 7/9/2017.
 */
public class NewmanTable {

    private ArrayList<MovieRecord> movieTable;

    /**
     * Creates a NewmanTable object.
     */
    public NewmanTable() {
        movieTable = new ArrayList<>();
        buildTable();
    }

    /**
     * Searches the NewmanTable for records like 'query'.
     */
    public ArrayList<MovieRecord> lookup(String query) {
        ArrayList<MovieRecord> results = new ArrayList<>();
        for (MovieRecord mr : movieTable) {
            if (mr.contains(query)) {
                results.add(mr);
            }
        }
        return results;
    }

    /**
     * Pulls the txt file of movie data from Newman library.
     * @return File object with dvd_info table
     */
    private File pullMovieDb() {
        // TODO: implement network
        String tableLoc = "C:\\Users\\Boston\\AndroidStudioProjects\\NewmanMovieDatabase\\app\\src\\main\\assets\\dvd_info.txt";
        return new File(tableLoc);
    }

    /**
     *  Parses the raw data into an ArrayList of MovieRecord objects. Maps the records to IMDb IDs
     *  for lookup in the OMDb.
     */
    private void buildTable(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pullMovieDb()));
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
            System.out.println("ERR: File not found");
        }
        catch (java.io.IOException e) {
            System.out.println("ERR: End of file");
        }
    }
}
