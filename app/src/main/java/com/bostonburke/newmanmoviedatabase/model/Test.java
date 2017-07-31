package com.bostonburke.newmanmoviedatabase.model;

import com.newman.moviedatabase.model.NMDb;

import java.io.File;
import java.util.Set;

/**
 *
 * Created by Boston on 7/23/2017.
 */

public class Test {

    static long time() {
        return System.currentTimeMillis();
    }

    static void println(String s) {
        System.out.println(s);
    }

    public static void main(String args[]) {
        File dvdInfo = new File("C:\\Users\\Boston\\AndroidStudioProjects\\NewmanMovieDatabase" +
                "\\app\\src\\main\\assets\\dvd_info.txt");

        long start = time();
        NMDb nmdb = new NMDb(dvdInfo);
        long end = time();
        println("Build time: " + (end-start) + "ms");

        start = time();
        Set<Integer> results = nmdb.lookup("kubrick eyes star wars");
        end = time();
        println("Search time: " + (end-start) + "ms");
        for (int s : results) {
            println("" + s);
        }
    }

}
