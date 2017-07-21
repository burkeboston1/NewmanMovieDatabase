package com.bostonburke.newmanmoviedatabase.model;

import com.bostonburke.newmanmoviedatabase.model.cloud.OMDb;

import org.json.JSONObject;

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

    // ---------------------------------------------------------------------------------------------
    // Constructors

    /**
     * Creates a MovieRecord object with a call number, title, addAuthor, and subject.
     * @param callNumber the DVD's call number
     * @param title      title of the movie
     * @param addAuthor  extra information about actors, etc.
     * @param subject    keywords about the movie's content
     */
    public MovieRecord(String callNumber, String title, String addAuthor, String subject ){
        this.CallNumber = callNumber;
        this.Title = title;
        this.AddAuthor = addAuthor;
        this.Subject = subject;
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
        s = s.toLowerCase();
        return CallNumber.toLowerCase().contains(s) || Title.toLowerCase().contains(s)
                || AddAuthor.toLowerCase().contains(s) || Subject.toLowerCase().contains(s);
    }

    public JSONObject getOMDb(OMDb omDb) {
        return omDb.getMovieDataByTitle(Title);
    }

    /* Getters */
    public String getCallNumber(){ return CallNumber; }
    public String getTitle(){
        return Title;
    }
    public String getAddAuthor(){ return AddAuthor; }
    public String getSubject(){ return Subject; }


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
        return before.split("\\[v|\\(M")[0];
    }

}
