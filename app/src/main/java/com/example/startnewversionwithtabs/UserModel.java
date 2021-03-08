package com.example.startnewversionwithtabs;

import android.graphics.Bitmap;

public class UserModel {

    public String nome;
    public int livelloGlobale = globalLevelCalculator();
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

    public boolean isOurProfile = true;//opkpkpkpk
    public int userState = 2;

    public UserModel(String nome, String email, String password, boolean isOurProfile) {
        this.isOurProfile = isOurProfile;
        this.password = password;
        this.email = email;
        this.nome = nome;
    }

    public int globalLevelCalculator(){
        return (livelloCharDesign + livelloComics + livelloLandscapes + livelloLogos)/4;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getLivelloGlobale() {
        return livelloGlobale;
    }

    public void setLivelloGlobale(int livelloGlobale) {
        this.livelloGlobale = livelloGlobale;
    }

    public int getLivelloCharDesign() {
        return livelloCharDesign;
    }

    public void setLivelloCharDesign(int livelloCharDesign) {
        this.livelloCharDesign = livelloCharDesign;
    }

    public int getLivelloLandscapes() {
        return livelloLandscapes;
    }

    public void setLivelloLandscapes(int livelloLandscapes) {
        this.livelloLandscapes = livelloLandscapes;
    }

    public int getLivelloComics() {
        return livelloComics;
    }

    public void setLivelloComics(int livelloComics) {
        this.livelloComics = livelloComics;
    }

    public int getLivelloLogos() {
        return livelloLogos;
    }

    public void setLivelloLogos(int livelloLogos) {
        this.livelloLogos = livelloLogos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPath_CharDesign() {
        return path_CharDesign;
    }

    public void setPath_CharDesign(int path_CharDesign) {
        this.path_CharDesign = path_CharDesign;
    }

    public int getPath_Landscapes() {
        return path_Landscapes;
    }

    public void setPath_Landscapes(int path_Landscapes) {
        this.path_Landscapes = path_Landscapes;
    }

    public int getPath_Comics() {
        return path_Comics;
    }

    public void setPath_Comics(int path_Comics) {
        this.path_Comics = path_Comics;
    }

    public int getPath_Logos() {
        return path_Logos;
    }

    public void setPath_Logos(int path_Logos) {
        this.path_Logos = path_Logos;
    }

    public int getNumeroFotoCondivise() {
        return numeroFotoCondivise;
    }

    public void setNumeroFotoCondivise(int numeroFotoCondivise) {
        this.numeroFotoCondivise = numeroFotoCondivise;
    }

    public int getNumeroFotoValutate() {
        return numeroFotoValutate;
    }

    public void setNumeroFotoValutate(int numeroFotoValutate) {
        this.numeroFotoValutate = numeroFotoValutate;
    }

    public int getNumeroFotoCommentate() {
        return numeroFotoCommentate;
    }

    public void setNumeroFotoCommentate(int numeroFotoCommentate) {
        this.numeroFotoCommentate = numeroFotoCommentate;
    }

    public boolean isOurProfile() {
        return isOurProfile;
    }

    public void setOurProfile(boolean ourProfile) {
        isOurProfile = ourProfile;
    }

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "nome='" + nome + '\'' +
                ", livelloGlobale=" + livelloGlobale +
                ", livelloCharDesign=" + livelloCharDesign +
                ", livelloLandscapes=" + livelloLandscapes +
                ", livelloComics=" + livelloComics +
                ", livelloLogos=" + livelloLogos +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", path_CharDesign=" + path_CharDesign +
                ", path_Landscapes=" + path_Landscapes +
                ", path_Comics=" + path_Comics +
                ", path_Logos=" + path_Logos +
                ", numeroFotoCondivise=" + numeroFotoCondivise +
                ", numeroFotoValutate=" + numeroFotoValutate +
                ", numeroFotoCommentate=" + numeroFotoCommentate +
                ", isOurProfile=" + isOurProfile +
                ", userState=" + userState +
                '}';
    }
}
