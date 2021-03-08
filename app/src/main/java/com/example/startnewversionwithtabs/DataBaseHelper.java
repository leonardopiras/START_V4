package com.example.startnewversionwithtabs;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static android.graphics.BitmapFactory.decodeResource;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";

    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_LOGOS_LEVEL = "LOGOS_LEVEL";
    public static final String COLUMN_GLOBAL_LEVEL = "GLOBAL_LEVEL";
    public static final String COLUMN_COMICS_LEVEL = "COMICS_LEVEL";
    public static final String COLUMN_LANDSCAPES_LEVEL = "LANDSCAPES_LEVEL";
    public static final String COLUMN_CHARACTER_DESIGN_LEVEL = "CHARACTER_DESIGN_LEVEL";
    public static final String COLUMN_CHARACTER_DESIGN_PATH_COMPLETION = "CHARACTER_DESIGN_PATH_COMPLETION";
    public static final String COLUMN_LANDSCAPES_PATH_COMPLETION = "LANDSCAPES_PATH_COMPLETION";
    public static final String COLUMN_COMICS_PATH_COMPLETION = "COMICS_PATH_COMPLETION";
    public static final String COLUMN_LOGOS_PATH_COMPLETION = "LOGOS_PATH_COMPLETION";
    public static final String COLUMN_IS_OUR_PROFILE = "IS_OUR_PROFILE";
    public static final String COLUMN_USER_STATE = "USER_STATE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creazione database
        String createTableStatement = "CREATE TABLE "+USER_TABLE+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_GLOBAL_LEVEL + " INTEGER, " + COLUMN_CHARACTER_DESIGN_LEVEL
                + " INTEGER, " + COLUMN_LANDSCAPES_LEVEL + " INTEGER, " + COLUMN_COMICS_LEVEL + " INTEGER, " + COLUMN_LOGOS_LEVEL + " INTEGER, "
                + COLUMN_CHARACTER_DESIGN_PATH_COMPLETION + " INTEGER, " + COLUMN_LANDSCAPES_PATH_COMPLETION + " INTEGER, " + COLUMN_COMICS_PATH_COMPLETION
                + " INTEGER, " + COLUMN_LOGOS_PATH_COMPLETION + " INTEGER, " + COLUMN_IS_OUR_PROFILE + " BOOLEAN, "
                + COLUMN_USER_STATE + " INTEGER)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //update del database
    }

    public boolean addUser(UserModel userModel, Resources res){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, userModel.getNome());
        cv.put(COLUMN_EMAIL, userModel.getEmail());
        cv.put(COLUMN_PASSWORD, userModel.getPassword());
        cv.put(COLUMN_LOGOS_LEVEL, 0);//userModel.getLivelloLogos());
        cv.put(COLUMN_GLOBAL_LEVEL, 0);//userModel.getLivelloGlobale());
        cv.put(COLUMN_COMICS_LEVEL, 0);//userModel.getLivelloComics());
        cv.put(COLUMN_LANDSCAPES_LEVEL, 0);//userModel.getLivelloLandscapes());
        cv.put(COLUMN_CHARACTER_DESIGN_LEVEL, 0);//userModel.getLivelloCharDesign());
        cv.put(COLUMN_CHARACTER_DESIGN_PATH_COMPLETION, 0);//userModel.getPath_CharDesign());
        cv.put(COLUMN_LANDSCAPES_PATH_COMPLETION, 0);//userModel.getPath_Landscapes());
        cv.put(COLUMN_COMICS_PATH_COMPLETION, 0);//userModel.getPath_Comics());
        cv.put(COLUMN_LOGOS_PATH_COMPLETION, 0);//userModel.getPath_Logos());
        cv.put(COLUMN_IS_OUR_PROFILE, userModel.isOurProfile());
        cv.put(COLUMN_USER_STATE, userModel.getUserState());

        long insert = db.insert(USER_TABLE, null, cv);


        return insert != -1;
    }

    public List<UserModel> getEveryUser(){
        List<UserModel> returnList = new ArrayList<>();

        String selectEveryoneQuery = "SELECT * FROM " + USER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectEveryoneQuery, null);

        if(cursor.moveToFirst()){
            do{
                String userName = cursor.getString(1);
                String userEmail = cursor.getString(2);
                String userPassword = cursor.getString(3);
                boolean userIsOurProfile = cursor.getInt(12) == 1;

                UserModel newUserModel = new UserModel(userName, userEmail, userPassword, userIsOurProfile);
                returnList.add(newUserModel);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return returnList;
    }
    public List<String> getEveryUserNames(){
        List<String> returnList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT USERNAME FROM " + USER_TABLE, null);

        if(cursor.moveToFirst()){
            do{
                String userName = cursor.getString(1);

                returnList.add(userName);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return returnList;
    }

    public int dbSize(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, USER_TABLE);
        db.close();
        return (int)count;
    }
}
