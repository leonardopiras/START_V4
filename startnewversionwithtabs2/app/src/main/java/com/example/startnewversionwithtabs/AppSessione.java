package com.example.startnewversionwithtabs;

import android.os.Parcel;
import android.os.Parcelable;



public class AppSessione implements Parcelable {

    boolean provaVaiINCharDesigngallery;

    public AppSessione() {

    }

    protected AppSessione(Parcel in) {
        provaVaiINCharDesigngallery = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (provaVaiINCharDesigngallery ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppSessione> CREATOR = new Creator<AppSessione>() {
        @Override
        public AppSessione createFromParcel(Parcel in) {
            return new AppSessione(in);
        }

        @Override
        public AppSessione[] newArray(int size) {
            return new AppSessione[size];
        }
    };
}
