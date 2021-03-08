package com.example.startnewversionwithtabs;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class GridItem implements Parcelable {
    String name;
    String background;
    boolean isCategory;
    boolean isPref;
    Category category;



    public GridItem(String name, String background, boolean isCategory, boolean isPref) {
        this.name = name;
        this.background = background;
        this.isCategory = isCategory;
        this.isPref = isPref;
    }


    public GridItem(String name, boolean isCategory, boolean isPref) {
        this.name = name;
        this.isCategory = isCategory;
        this.isPref = isPref;
    }


    protected GridItem(Parcel in) {
        name = in.readString();
        background = in.readString();
        isCategory = in.readByte() != 0;
        isPref = in.readByte() != 0;
        category = Category.valueOf(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(background);
        dest.writeByte((byte) (isCategory ? 1 : 0));
        dest.writeByte((byte) (isPref ? 1 : 0));
        dest.writeString(category == null ? null : category.name());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GridItem> CREATOR = new Creator<GridItem>() {
        @Override
        public GridItem createFromParcel(Parcel in) {
            return new GridItem(in);
        }

        @Override
        public GridItem[] newArray(int size) {
            return new GridItem[size];
        }
    };
}


enum Category {
    CHARACTER_DESIGN,
    LANDSCAPES,
    COMICS,
    LOGOS,
    ADD;

    public static Category getCategoryAtPosition(int position) {
        switch (position) {
            case 0:
                return CHARACTER_DESIGN;
            case 1:
                return LANDSCAPES;
            case 2:
                return COMICS;
            case 3:
                return LOGOS;
            case 4:
                return ADD;
        }
        return null;
    }
}
