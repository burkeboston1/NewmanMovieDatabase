package com.bostonburke.newmanmoviedatabase;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.method.MovementMethod;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Simply a Vector of MovieRecord objects. MRParcel must implement Parcelable
 * so an instance of it can be putExtra() on an Intent.
 *
 * Created by Boston on 10/16/2016.
 */

public class MRParcel implements Parcelable{
    private int mData;
    private ArrayList<MovieRecord> mrv;

    public MRParcel(ArrayList<MovieRecord> records){
        mrv = records;
    }

    public ArrayList<MovieRecord> get(){
        return mrv;
    }

    // The following methods are required for implementation of Parcelable
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    public static final Parcelable.Creator<MRParcel> CREATOR
            = new Parcelable.Creator<MRParcel>() {
        public MRParcel createFromParcel(Parcel in) {
            return new MRParcel(in);
        }

        public MRParcel[] newArray(int size) {
            return new MRParcel[size];
        }
    };

    private MRParcel(Parcel in) {
        mData = in.readInt();
    }
}
