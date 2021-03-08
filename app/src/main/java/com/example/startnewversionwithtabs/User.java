package com.example.startnewversionwithtabs;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    public String nome;
    public int livelloGlobale = 0;
    public int livelloCharDesign = 0;
    public int livelloLandscapes = 0;
    public int livelloComics = 0;
    public int livelloLogos = 0;
    public String email;
    public String password;

    // percentuale completamento path
    public int path_CharDesign = 0;
    public int path_Landscapes = 0;
    public int path_Comics = 0;
    public int path_Logos = 0;

    public int numeroFotoCondivise = 0;
    public int numeroFotoValutate = 0;
    public int numeroFotoCommentate = 0;

    public boolean isOurProfile = false;
    public int userState;
    public String profilePhoto;


    public User(String nome) {
        this.nome = nome;
    }


    public User(String nome, int livelloCharDesign, int livelloLandscapes,
                int livelloComics, int livelloLogos, String email, String password, int path_CharDesign,
                int path_Landscapes, int path_Comics, int path_Logos, int numeroFotoCondivise, int numeroFotoValutate, int numeroFotoCommentate,
                boolean isOurProfile, int userState, String profilePhoto) {
        this.nome = nome;
        this.livelloCharDesign = livelloCharDesign;
        this.livelloLandscapes = livelloLandscapes;
        this.livelloComics = livelloComics;
        this.livelloLogos = livelloLogos;
        this.email = email;
        this.password = password;
        this.path_CharDesign = path_CharDesign;
        this.path_Landscapes = path_Landscapes;
        this.path_Comics = path_Comics;
        this.path_Logos = path_Logos;
        this.numeroFotoCondivise = numeroFotoCondivise;
        this.numeroFotoValutate = numeroFotoValutate;
        this.numeroFotoCommentate = numeroFotoCommentate;
        this.isOurProfile = isOurProfile;
        this.userState = userState;
        this.profilePhoto = profilePhoto;

        this.livelloGlobale = (livelloCharDesign + livelloComics + livelloLandscapes + livelloLogos)/4;
    }


    protected User(Parcel in) {
        nome = in.readString();
        livelloGlobale = in.readInt();
        livelloCharDesign = in.readInt();
        livelloLandscapes = in.readInt();
        livelloComics = in.readInt();
        livelloLogos = in.readInt();
        email = in.readString();
        password = in.readString();
        path_CharDesign = in.readInt();
        path_Landscapes = in.readInt();
        path_Comics = in.readInt();
        path_Logos = in.readInt();
        numeroFotoCondivise = in.readInt();
        numeroFotoValutate = in.readInt();
        numeroFotoCommentate = in.readInt();
        isOurProfile = in.readByte() != 0;
        userState = in.readInt();
        profilePhoto = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeInt(livelloGlobale);
        dest.writeInt(livelloCharDesign);
        dest.writeInt(livelloLandscapes);
        dest.writeInt(livelloComics);
        dest.writeInt(livelloLogos);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeInt(path_CharDesign);
        dest.writeInt(path_Landscapes);
        dest.writeInt(path_Comics);
        dest.writeInt(path_Logos);
        dest.writeInt(numeroFotoCondivise);
        dest.writeInt(numeroFotoValutate);
        dest.writeInt(numeroFotoCommentate);
        dest.writeByte((byte) (isOurProfile ? 1 : 0));
        dest.writeInt(userState);
        dest.writeString(profilePhoto);
    }


    public int getCategoryLevel(Category cat) {
        switch (cat) {
            case CHARACTER_DESIGN:
                return livelloCharDesign;
            case LANDSCAPES:
                return livelloLandscapes;
            case LOGOS:
                return livelloLogos;
            case COMICS:
                return livelloComics;
            default:
                return 0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}