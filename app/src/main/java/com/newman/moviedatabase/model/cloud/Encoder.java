package com.newman.moviedatabase.model.cloud;

import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by Tom on 7/27/2017.
 */

public class Encoder
{
    /**
     * Method to URI-encode a parameter for use in HTTP requests.
     *
     * @param rawParam - The unprocessed parameter.
     * @return - The URI-encoded parameter.
     */
    public static String URIEncode(String rawParam)
    {
        try {
            Log.d("TAG", "The encoded thing is: " + java.net.URLEncoder.encode(rawParam, "UTF-8"));
            return java.net.URLEncoder.encode(rawParam, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("TAG", "Error when URI-encoding parameters");
            e.printStackTrace();
        }
        return null;
    }
}
