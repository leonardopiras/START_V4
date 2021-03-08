package com.example.startnewversionwithtabs;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;


public class AppSessione implements Parcelable {


   ArrayList<PathSingleElement> pathItem;
   ArrayList<GridItem> gridItemsGallery;
   ArrayList<Image> images;
   User user;

    public AppSessione(ArrayList<PathSingleElement> pathItem, ArrayList<GridItem> gridItemsGallery,
                       ArrayList<Image> images, User user) {
        this.pathItem = pathItem;
        this.gridItemsGallery = gridItemsGallery;
        this.images = images;
        this.user = user;
    }


    protected AppSessione(Parcel in) {
        pathItem = in.createTypedArrayList(PathSingleElement.CREATOR);
        gridItemsGallery = in.createTypedArrayList(GridItem.CREATOR);
        images = in.createTypedArrayList(Image.CREATOR);
        user = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(pathItem);
        dest.writeTypedList(gridItemsGallery);
        dest.writeTypedList(images);
        dest.writeParcelable(user, flags);
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
