package com.bostonburke.newmanmoviedatabase.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Current database of movies available to rent from Newman Library.
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
    public ArrayList<MovieRecord> searchFor(String query) {
        ArrayList<MovieRecord> results = new ArrayList<MovieRecord>();
        for (MovieRecord mr : movieTable) {
            if (mr.getTitle().toLowerCase().contains(query.toLowerCase())) {
                results.add(mr);
            }
            else if (mr.getSubject().contains(query)) {
                results.add(mr);
            }
            else if (mr.getAddAuthor().contains(query)) {
                results.add(mr);
            }
        }
        return results;
    }

    /**
     * Get the most recent .csv file of DVD information.
     */
    private File getCurrentCSVFile() {
        // TODO: implement network
        return new File("C:\\Users\\Boston\\AndroidStudioProjects\\NewmanMovieDatabase\\app\\src\\main\\assets\\dvd_info.txt");
    }

    /**
     *
     */
    private void buildTable(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(getCurrentCSVFile()));
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
            System.out.println("ERROR: File not found");
        }
        catch (java.io.IOException e) {
            System.out.println("MSG: End of file");
        }
    }
}
