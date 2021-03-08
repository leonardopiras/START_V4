package com.example.startnewversionwithtabs;

import android.graphics.drawable.Drawable;

public class PathSingleElement {
    public String title;
    public boolean isPref;
    public Drawable backgroudImageSource;

    public PathSingleElement(String title, boolean isPref, Drawable backgroudImageSource) {
        this.title = title;
        this.isPref = isPref;
        this.backgroudImageSource = backgroudImageSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsPref() {
        return isPref;
    }

    public void setPref(boolean pref) {
        isPref = pref;
    }

    public Drawable getBackgroudImageSource() {
        return backgroudImageSource;
    }

    public void setBackgroudImageSource(Drawable backgroudImageSource) {
        this.backgroudImageSource = backgroudImageSource;
    }
}
