package com.newman.moviedatabase.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A searchable database of movies located in Newman Library at Virginia Tech.
 *
 * Created by Boston Burke on 7/27/2017.
 */
public class NMDb {

    /* ATTRIBUTES */
    private Map<String, Set<Integer>> keywordMap; // maps keywords to call numbers
    private Set<String> stopList;                    // list of stop words

    private int numRecords;


    // region CONSTRUCTORS
    /* CONSTRUCTORS */

    /**
     * Creates a new NMDb.
     *
     * @param dvdInfo - the text file containing Newman movie records
     */
    public NMDb (File dvdInfo) {
        stopList = new HashSet<>();
        stopList.addAll(Arrays.asList(
                "a", "an", "and", "are", "as", "at", "be", "by", "for", "from", "has", "he", "in",
                "is", "it", "its", "of", "on", "that", "the", "to", "was", "were", "will", "with"
        ));
        keywordMap = new HashMap<>(8192);
        buildTable(dvdInfo);
        numRecords = 0;
    }
    //endregion

    /* PUBLIC */

    /**
     * Search the database for words in 'query'.
     *
     * @param query - a string with words to search the db for
     * @return      - a set of unique call numbers referring to matching movie records
     */
    public Set<Integer> lookup(String query) {
        return findCallNos(getKeywords(query));
    }


    // region PRIVATE
    /* PRIVATE */

    /**
     * Load the data from dvdInfo into keywordMap.
     *
     * @param dvdInfo - table from Newman
     */
    private void buildTable(File dvdInfo) {
        try {
            /* Parsing dvdInfo */
            BufferedReader reader = new BufferedReader(new FileReader(dvdInfo));
            String line;
            reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                int callNo = parseCallNo(line.split("\t")[0]); // call number
                if (callNo < 0) {continue;}
                numRecords++;
                // Map callNo to each keyword
                for (String kw : getKeywords(line.toLowerCase())) {
                    putBin(kw, callNo);
                }
            }
        }
        catch (java.io.IOException e) {
            // TODO: Throw sexy error up the stack boi
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the set of keywords in s. A valid keyword:
     * a) matches the Java regex "\W" (word) AND
     * b) does not occur in stopList.
     *
     * @param s - the string to chop up
     * @return  - a HashSet of all keywords found in s
     */
    private Set<String> getKeywords(String s) {
        Set<String> keywords = new HashSet<>();
        // Parse out words with regex
        String words[] = s.split("\\W");
        for (String word : words) {
            if (!word.isEmpty() && !stopList.contains(word)) {
                keywords.add(word);
            }
        }
        return keywords;
    }

    /**
     * Maps callNo to keyword. If keyword exists in keywordMap, adds callNo to mapped
     * set of call numbers.
     * @param keyword - map key
     * @param callNo  - value to map to key
     */
    private void putBin(String keyword, int callNo) {
        Set<Integer> existing = keywordMap.get(keyword);
        if (existing != null) {
            // keyword already occurs in map
            existing.add(callNo);
        } else {
            keywordMap.put(keyword, new HashSet<>(Arrays.asList(callNo)));
        }
    }

    /**
     * Finds the set of call numbers that map to every keyword in keywords.
     *
     * @param keywords - the set of keywords to look for
     * @return         - a list of call numbers
     */
    private Set<Integer> findCallNos(Set<String> keywords) {
        Set<Integer> matches = new HashSet<>();
        for (String s : keywords) {
            if (matches.size() == 0) {
                matches = keywordMap.get(s);
            } else {
                matches.retainAll(keywordMap.get(s));
            }
        }
        return matches;
    }
    //endregion

    /**
     * Parses a string for a integer call number. Returns -1 if a call number could not be found.
     * @param s - the call number string
     * @return  - an integer
     */
    private int parseCallNo(String s) {
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    //region Debug

    private void debugTable() {
        System.out.println("Number of records: " + numRecords);
        Set<String> keySet = keywordMap.keySet();
        System.out.println("Number of keywords: " + keySet.size());
        int max = 0;
        for (String keyword : keySet) {
            int cns = keywordMap.get(keyword).size();
            double df = (double)cns / numRecords;
            max = Math.max(max, cns);
            if (df > 0.25) {
                System.out.println(keyword);
            }
        }
    }








    //endregion

}
