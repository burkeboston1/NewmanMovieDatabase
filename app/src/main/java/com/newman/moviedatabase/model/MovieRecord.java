package com.newman.moviedatabase.model;

import com.newman.moviedatabase.model.cloud.OMDb;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Holds data about a movie in Newman library.
 *
 * Boston Burke, 2017
 */
public class MovieRecord {

    // Attributes
    private String CallNumber;
    private String Title;
    private String AddAuthor;
    private String Subject;

    private ArrayList<String> keywords;

    // ---------------------------------------------------------------------------------------------
    // Constructors

    /**
     * Creates a MovieRecord object with a call number, title, addAuthor, and subject.
     * @param callNumber the DVD's call number
     * @param title      title of the movie
     * @param addAuthor  extra information about actors, etc.
     * @param subject    keywords about the movie's content
     */
    public MovieRecord(String callNumber, String title, String addAuthor, String subject){
        this.CallNumber = callNumber;
        this.Title = title;
        this.AddAuthor = addAuthor;
        this.Subject = subject;

        String target = this.Title + " " + this.AddAuthor + " " + this.Subject;
        keywords = getKeywords(target);
    }

    //----------------------------------------------------------------------------------------------
    // Public Methods

    public static MovieRecord parseMovieRecord(String string) {
        String[] fields = string.split("\t");
        if (fields.length == 9) {
            return new MovieRecord(fields[0], trimTitle(fields[1]), fields[3], fields[8]);
        }
        return null;
    }


    public boolean contains(String s) {
        ArrayList<String> kws = getKeywords(s);
        for (String kw : kws) {
            if (!keywords.contains(kw)) {
                return false;
            }
        }
        return true;
//        s = s.toLowerCase();
//        return CallNumber.toLowerCase().contains(s) || Title.toLowerCase().contains(s)
//                || AddAuthor.toLowerCase().contains(s) || Subject.toLowerCase().contains(s);
    }

    public String getOMDb(OMDb omDb) {
        return omDb.getMovieDataByTitle(Title);
    }

    /* Getters */
    public String getCallNumber(){ return CallNumber; }
    public String getTitle(){
        return Title;
    }

    /* Equals */
    @Override
    public boolean equals(Object other){
        if ( other != null ){
            if ( other.getClass().equals(this.getClass()) ){
                MovieRecord omr = (MovieRecord)other;
                return CallNumber.equals(omr.getCallNumber());
            }
        }
        return false;
    }

    //----------------------------------------------------------------------------------------------
    // Private Methods
    /**
     * Trims the movie record's title of trailing, unnecessary information.
     */
    private static String trimTitle(String before) {
        return before.split("\\[v|\\(M|/")[0];
    }

    private ArrayList<String> getKeywords(String target) {
        ArrayList<String> kws = new ArrayList<>();
        String[] words = target.split("\\W"); // strip non-word characters
        for (String word : words) {
            if (!word.isEmpty()) {
                kws.add(word.toLowerCase());
            }
        }
        return kws;
    }
}
