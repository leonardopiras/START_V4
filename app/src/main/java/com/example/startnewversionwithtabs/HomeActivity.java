package com.example.startnewversionwithtabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.orangeHeader));
        }

        //writeFile("show","Show guide dialog?.txt");
        //writeFile("don't show never again","Show guide dialog?.txt");
        //writeFile("don't show in this session anymore","Show guide dialog?.txt");

        //Toast.makeText(this, readFile("Show guide dialog?.txt"), Toast.LENGTH_SHORT).show();

        if(readFile("Show guide dialog?.txt").equals("don't show in this session anymore\n") || readFile("Show guide dialog?.txt").isEmpty())
            writeFile("show","Show guide dialog?.txt");

        writeFile("Default","Where Help Guide Is Called.txt.txt");

        if (readFile("Logged in?.txt").equals("don't remember me\n"))
            writeFile("no", "Logged in?.txt");


        Thread  t = new Thread() {
            @Override
            public void run() {
                super.run();
                ImageView img = (ImageView)findViewById(R.id.loading);
                img.setBackgroundResource(R.drawable.loading);
                AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
                frameAnimation.start();
            }
        };
        t.start();

        AppSessione appSessione = createAppSessione();
        final int random = (new Random().nextInt(3) + 1) * 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(HomeActivity.this, MainActivity.class);
                homeIntent.putExtra("AppSessione", appSessione);
                t.interrupt();
                startActivity(homeIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


                finish();
            }
        }, random);



    }

    public AppSessione createAppSessione() {
        ArrayList<PathSingleElement> pathItems = genera_PathSingleElementArray_MyPath();
        ArrayList<GridItem> gridItems = generaArrayItemGallery();
        //ArrayList<GridItem> gridItemsCD = generaArrayItemCharacterDesign();
        ArrayList<Image> images = generaArrayImage();
        /*
        SharedPreferences mPrefs = getSharedPreferences("PROVA", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(appSessione);
        prefsEditor.putString("DioCane", "ddcdc");
        prefsEditor.putString("AppSessione", json);
        prefsEditor.apply();*/

        return new AppSessione(pathItems, gridItems, images, (images.get(0).owner));
    }


    public ArrayList<Image> generaArrayImage(){
        ArrayList<Image> arr = new ArrayList<>();

        User user1 = new User("Pippo Baudo");
        User user2 = new User ("Pippo Franco");
        User user3 = new User("Zio Michele");

        Comment comment1 = new Comment(user1, "Buono", 3f);
        Comment comment2 = new Comment(user2, "dcndu", 4.2f);
        Comment comment3 = new Comment(user3, "aer", 1f);

        comment1.innerComments.add(new Comment(user1, "1\"Buono\" na sega puteo", -2.5f));
        comment1.innerComments.add(new Comment(user1, "2ma to mare omo vecio", -5f));
        comment1.innerComments.add(new Comment(user1, "3Yosemite Sam", -3.5f));
        comment1.innerComments.add(new Comment(user1, "4de res naturae", -1f));
        comment1.innerComments.add(new Comment(user1, "5Il dugongo è un mammifero dell'ordine Sirenia; " +
                "è l'unica specie del genere Dugong Lacépède, 1799 e della famiglia Dugongidae Gray, 1821. È un parente relativamente prossimo del lamantino," +
                " da cui si differenzia soprattutto per la forma biforcuta della coda. Per secoli oggetto di caccia, è a rischio d'estinzione. ", -4.5f));


        ArrayList<Comment> commentsArray1 = new ArrayList<>();
        ArrayList<Comment> commentsArray2 = new ArrayList<>();
        ArrayList<Comment> commentsArray3 = new ArrayList<>();

        commentsArray1.add(comment1);
        commentsArray1.add(comment2);

        commentsArray2.add(comment1);
        commentsArray2.add(comment2);

        commentsArray3.add(comment1);
        commentsArray3.add(comment2);
        commentsArray3.add(comment3);

        /* arr.add(new Image("Img1", 5.0f, user1, commentsArray1, R.drawable.img1, true, "None", false));
        arr.add(new Image("Img1_2", 4.0f, user1, new ArrayList<Comment>(), R.drawable.img5, false, "Descr1", true));
        arr.add(new Image("Img1_3", 2.7f, user1, new ArrayList<Comment>(), R.drawable.img_comic, false, "Hak", false));
        arr.add(new Image("Img2", 4.0f, user2, commentsArray2, R.drawable.img2, true, "vee", false));
        arr.add(new Image("Img3", 3.5f, user3, commentsArray3, R.drawable.img3, true, "vdsfvndfui", true));
        arr.add(new Image("Add", 0.0f, user1, commentsArray1, R.drawable.camera_add_icon, false,"add", false));*/

        Image img1 = new Image("Img1", 5.0f, user1, commentsArray1,
                true, "", false, Category.LANDSCAPES);

        img1.ratingArray.add(new Comment(user2, 3.0f));
        img1.ratingArray.add(new Comment(user3, 4f));

        arr.add(img1);
        arr.add(new Image("Img1_2", 4.0f, user1, new ArrayList<Comment>(), false, "Descr1", true, Category.CHARACTER_DESIGN));
        arr.add(new Image("Img1_3", 2.7f, user1, new ArrayList<Comment>(), false, "Hak", false, Category.CHARACTER_DESIGN));
        arr.add(new Image("Img2", 4.0f, user2, commentsArray2, true, "vee", false, Category.LANDSCAPES));
        arr.add(new Image("Img3", 3.5f, user3, commentsArray3, true, "vdsfvndfui", true, Category.CHARACTER_DESIGN));
        //arr.add(new Image("Add", -1f, user1, commentsArray1, false,"add", false, ));
        return itemImagesAddPngPath(arr);

    }

    public ArrayList<Image> itemImagesAddPngPath(ArrayList<Image> images) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDiret", Context.MODE_PRIVATE);

        for (int i = 0; i < images.size(); i++) {
            int drawableId = R.drawable.ic_launcher_background;

            if (i == 0)
                drawableId = R.drawable.img1;
            if (i == 1)
                drawableId = R.drawable.img5;
            if (i == 2)
                drawableId = R.drawable.img_comic;
            if (i == 3)
                drawableId = R.drawable.img2;
            if (i == 4)
                drawableId = R.drawable.img3;
            if (i == 5)
                drawableId = R.drawable.camera_add_icon;


            Bitmap bitmap;
            Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), drawableId);
            File pngImageInMemory = new File(directory, images.get(i).name + ".png");

            if (!pngImageInMemory.exists()) {
                Log.d("path", pngImageInMemory.toString());
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(pngImageInMemory);
                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }

            images.get(i).imageSource = pngImageInMemory.toString();

        }

        return images;

    }

    public ArrayList<GridItem> generaArrayItemGallery() {
        ArrayList<GridItem> arrayList = new ArrayList<>();
        arrayList.add(new GridItem("Character design", true, false));
        arrayList.add(new GridItem("Landscapes",true, false));
        arrayList.add(new GridItem("Comics",  true, false));
        arrayList.add(new GridItem("Logos", true, false));
        arrayList.add(new GridItem("Add", true, false));
        return gridItemCategoriesImages(arrayList);
    }


    public ArrayList<GridItem> gridItemCategoriesImages(ArrayList<GridItem> gridItems) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("image_directory", Context.MODE_PRIVATE);

        for (int i = 0; i < gridItems.size(); i++) {
            int drawableId = R.drawable.ic_launcher_background;

            switch (gridItems.get(i).name) {
                case "Character design":
                    gridItems.get(i).category = Category.CHARACTER_DESIGN;
                    drawableId = R.drawable.img5;
                    break;
                case "Landscapes":
                    gridItems.get(i).category = Category.LANDSCAPES;
                    drawableId = R.drawable.img_landscape;
                    break;
                case "Comics":
                    gridItems.get(i).category = Category.COMICS;
                    drawableId = R.drawable.img_comic;
                    break;
                case"Logos":
                    gridItems.get(i).category = Category.LOGOS;
                    drawableId = R.drawable.logo;
                    break;
                case "Add":
                    gridItems.get(i).category = Category.ADD;
                    drawableId = R.drawable.camera_add_icon;
                    break;
            }

            Bitmap bitmap;
            Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), drawableId);
            File pngImageInMemory = new File(directory, gridItems.get(i).name + ".png");

            if (!pngImageInMemory.exists()) {
                Toast.makeText(getBaseContext(), pngImageInMemory.toString(), Toast.LENGTH_LONG).show();
                Log.d("path", pngImageInMemory.toString());
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(pngImageInMemory);
                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }

            gridItems.get(i).background = pngImageInMemory.toString();

        }

        return gridItems;

    }


    public ArrayList<PathSingleElement> genera_PathSingleElementArray_MyPath() {
        ArrayList<PathSingleElement> arrayOfElements = new ArrayList<>();
        arrayOfElements.add(new PathSingleElement("Landscapes", true,
                R.drawable.landscapes_background, false));
        arrayOfElements.add(new PathSingleElement("Charachter design", true,
                R.drawable.charachter_design_background, false));
        arrayOfElements.add(new PathSingleElement("Eyes", false,
                R.drawable.charachter_design_background, true, 1));
        arrayOfElements.add(new PathSingleElement("Hands", false,
                R.drawable.charachter_design_background, true, 1));
        arrayOfElements.add(new PathSingleElement("Comics", false,
                R.drawable.comics_background,false));

        for (int i = 0; i < arrayOfElements.size(); i++ ) {
            arrayOfElements.get(i).index = i;
        }
        return arrayOfElements;
    }



    public void writeFile(String textToSave, String fileName) {

        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(textToSave.getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String fileName) {
        String textToLoad = null;

        try {
            FileInputStream fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuffer = new StringBuilder();

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines).append("\n");
            }

            textToLoad = stringBuffer.toString();
        } catch (FileNotFoundException e) {
            textToLoad = "false";
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return textToLoad;
    }
}