package com.example.startnewversionwithtabs;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Image implements Parcelable {
    public String name;
    public float rating;
    public User owner;
    public ArrayList<Comment> comments;
    public String imageSource;
    public boolean isAlsoOnline;
    public String description;
    public boolean  isPref;
    ArrayList<Category> category;
    ArrayList<Comment> ratingArray = new ArrayList<>();


    public Image(String name, float rating, User owner, ArrayList<Comment> comments, String imageSource,
                 boolean isAlsoOnline, String description, boolean isPref, ArrayList<Category> category) {
        this.name = name;
        this.rating = rating;
        this.owner = owner;
        this.comments = comments;
        this.imageSource = imageSource;
        this.isAlsoOnline = isAlsoOnline;
        this.description = description;
        this.isPref = isPref;
        this.category = new ArrayList<>();
        this.category = category;
    }


    public Image(String name, float rating, User owner, ArrayList<Comment> comments, String imageSource,
                 boolean isAlsoOnline, String description, boolean isPref, Category category) {
        this.name = name;
        this.rating = rating;
        this.owner = owner;
        this.comments = comments;
        this.imageSource = imageSource;
        this.isAlsoOnline = isAlsoOnline;
        this.description = description;
        this.isPref = isPref;
        this.category = new ArrayList<>();
        this.category.add(category);
    }

    public Image(String name, float rating, User owner, ArrayList<Comment> comments, boolean isAlsoOnline, String description, boolean isPref, Category category) {
        this.name = name;
        this.rating = rating;
        this.owner = owner;
        this.comments = comments;
        this.isAlsoOnline = isAlsoOnline;
        this.description = description;
        this.isPref = isPref;
        this.category = new ArrayList<>();
        this.category.add(category);    }


    protected Image(Parcel in) {
        name = in.readString();
        rating = in.readFloat();
        owner = in.readParcelable(User.class.getClassLoader());
        comments = in.createTypedArrayList(Comment.CREATOR);
        imageSource = in.readString();
        isAlsoOnline = in.readByte() != 0;
        description = in.readString();
        isPref = in.readByte() != 0;
        createCategoryArrayEnum(in.createStringArrayList());
        ratingArray = in.createTypedArrayList(Comment.CREATOR);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(rating);
        dest.writeParcelable(owner, flags);
        dest.writeTypedList(comments);
        dest.writeString(imageSource);
        dest.writeByte((byte) (isAlsoOnline ? 1 : 0));
        dest.writeString(description);
        dest.writeByte((byte) (isPref ? 1 : 0));
        dest.writeStringList(createCategoryArrayString());
        dest.writeTypedList(ratingArray);
    }

    void createCategoryArrayEnum(ArrayList<String> stringArrayList) {
        if (category == null)
            category = new ArrayList<Category>();
        for(String str : stringArrayList) {
            category.add(Category.valueOf(str));
        }
    }

    ArrayList<String> createCategoryArrayString() {
        ArrayList<String> catArrayString = new ArrayList<>();
        for(Category cat : category) {
            catArrayString.add(cat.name());
        }
        return catArrayString;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    static boolean checkIfImageBelongsToThisCategory(Image im, Category cat) {
        for (Category imgCat : im.category) {
            if (imgCat == cat)
                return true;
        }
        return false;
    }

    //trasforma immagini in gallery item per offline gallery, senza preferiti
    public static ArrayList<GridItem> imageToGalleryItemOffline(ArrayList<Image> images,
                                                                User user, Category category1) {
        ArrayList<GridItem> arrayList = new ArrayList<>();
        for (Image image : images) {
            if (image.owner.nome.equals(user.nome) && checkIfImageBelongsToThisCategory(image, category1)) {
                arrayList.add(image.imageToGridItem());
            }
        }

        return arrayList;
    }



    public static ArrayList<GridItem> imageToGalleryItemGalleryFavorites(ArrayList<Image> images, User user) {
        ArrayList<GridItem> arrayList = new ArrayList<>();
        for (Image image : images) {
            if (image.isPref) {
                arrayList.add(image.imageToGridItem());
            }
        }
        return arrayList;
    }


    GridItem imageToGridItem( ) {

        return  new GridItem(name, imageSource, false, isPref);


    }

    public float getCalculatedRating() {
        float finalRating = 0;

        for (Comment rat : ratingArray) {
            finalRating += rat.rating;
        }
        if (finalRating > 0)
            finalRating = finalRating / (ratingArray.size());
        return finalRating;
    }


}


class Comment implements Parcelable {
    User commentOwner;
    String comment;
    float rating;
    ArrayList<Comment> innerComments = new ArrayList<Comment>();



    public Comment(User commentOwner, String comment) {
        this.commentOwner = commentOwner;
        this.comment = comment;
    }

    public Comment(User commentOwner, float rating) {
        this.commentOwner = commentOwner;
        this.rating = rating;
    }

    public Comment(float rating) {
        this.rating = rating;
    }



    public Comment(String comment) {
        this.comment = comment;
    }

    public Comment(User commentOwner, String comment, float rating) {
        this.commentOwner = commentOwner;
        this.comment = comment;
        this.rating = rating;
    }


    protected Comment(Parcel in) {
        commentOwner = in.readParcelable(User.class.getClassLoader());
        comment = in.readString();
        rating = in.readFloat();
        innerComments = in.createTypedArrayList(Comment.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(commentOwner, flags);
        dest.writeString(comment);
        dest.writeFloat(rating);
        dest.writeTypedList(innerComments);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}