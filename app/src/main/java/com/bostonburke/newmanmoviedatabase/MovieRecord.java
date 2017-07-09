package com.bostonburke.newmanmoviedatabase;

/**
 * Holds information about a movie.
 *
 * Created by Boston on 9/25/2016.
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
    public MovieRecord( String callNumber, String title, String addAuthor, String subject ){
        this.CallNumber = callNumber;
        this.Title = title;
        this.AddAuthor = addAuthor;
        this.Subject = subject;
    }

    //----------------------------------------------------------------------------------------------
    // Public Methods

    /**
     * Gets the location specifier of this MovieRecord object
     * @return call number
     */
    public String getCallNumber(){
        return CallNumber;
    }

    public static MovieRecord parseMovieRecord(String string) {
        String[] fields = string.split("\t");
        if (fields.length == 9) {
            return new MovieRecord(fields[0], fields[1], fields[3], fields[8]);
        }
        return null;
    }

    /**
     * Gets the title of this MovieRecord object
     * @return title
     */
    public String getTitle(){
        return Title;
    }

    /**
     * Gets the AddAuthor information about this movie, i.e. actors, firms, etc.
     * @return addAuthor
     */
    public String getAddAuthor(){ return AddAuthor; }

    /**
     * Gets the Subject information about this movie, i.e. keywords about the
     * movie's content.
     * @return subject
     */
    public String getSubject(){ return Subject; }

    /**
     * Assesses the equality of two MovieRecords. Two are equal if their CallNumbers
     * are the same
     * @param other MovieRecord object other
     * @return true if equal, false otherwise
     */
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
}
