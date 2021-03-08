package com.example.startnewversionwithtabs;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.core.content.res.ResourcesCompat;

public class PathSingleElement implements Parcelable {
    public String title;
    public boolean isPref;
    public int backGroundImageString;
    public int index;
    public boolean isFocused_mypath;
    public boolean isFocused_newpath;
    public boolean isSubCategory;
    public int parentIndex; // Solo se è subcategory

    public PathSingleElement(String title, boolean isPref, int backgroudImageString) {
        this.title = title;
        this.isPref = isPref;
        this.backGroundImageString = backgroudImageString;
        this.isFocused_mypath = false;
        this.isFocused_newpath = false;
    }

    public PathSingleElement(String title, boolean isPref, int backgroudImageSource, boolean isSubCategory) {
        this.title = title;
        this.isPref = isPref;
        this.backGroundImageString = backgroudImageSource;
        this.isSubCategory = isSubCategory;
        this.isFocused_mypath = false;
        this.isFocused_newpath = false;
    }


    public PathSingleElement(String title, boolean isPref, int backgroudImageSource, boolean isSubCategory, int parentIndex) {
        this.title = title;
        this.isPref = isPref;
        this.backGroundImageString = backgroudImageSource;
        this.isSubCategory = isSubCategory;
        this.isFocused_mypath = false;
        this.isFocused_newpath = false;
        this.parentIndex = parentIndex;
    }


    protected PathSingleElement(Parcel in) {
        title = in.readString();
        isPref = in.readByte() != 0;
        backGroundImageString = in.readInt();
        index = in.readInt();
        isFocused_mypath = in.readByte() != 0;
        isFocused_newpath = in.readByte() != 0;
        isSubCategory = in.readByte() != 0;
        parentIndex = in.readInt();
    }

    public static final Creator<PathSingleElement> CREATOR = new Creator<PathSingleElement>() {
        @Override
        public PathSingleElement createFromParcel(Parcel in) {
            return new PathSingleElement(in);
        }

        @Override
        public PathSingleElement[] newArray(int size) {
            return new PathSingleElement[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeByte((byte) (isPref ? 1 : 0));
        dest.writeInt(backGroundImageString);
        dest.writeInt(index);
        dest.writeByte((byte) (isFocused_mypath ? 1 : 0));
        dest.writeByte((byte) (isFocused_newpath ? 1 : 0));
        dest.writeByte((byte) (isSubCategory ? 1 : 0));
        dest.writeInt(parentIndex);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPref() {
        return isPref;
    }

    public void setPref(boolean pref) {
        isPref = pref;
    }

    public int getBackGroundImageString() {
        return backGroundImageString;
    }

    public void setBackGroundImageString(int backGroundImageString) {
        this.backGroundImageString = backGroundImageString;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isFocused_mypath() {
        return isFocused_mypath;
    }

    public void setFocused_mypath(boolean focused_mypath) {
        isFocused_mypath = focused_mypath;
    }

    public boolean isFocused_newpath() {
        return isFocused_newpath;
    }

    public void setFocused_newpath(boolean focused_newpath) {
        isFocused_newpath = focused_newpath;
    }

    public boolean isSubCategory() {
        return isSubCategory;
    }

    public void setSubCategory(boolean subCategory) {
        isSubCategory = subCategory;
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }
}
