package com.example.startnewversionwithtabs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.FileNotFoundException;
import java.io.IOException;

public class GalleryActivity extends AppCompatActivity {

    ImageButton backButton, logoButton;
    TabLayout tabLayout;
    TabItem tabItem1, tabItem2;
    ViewPager viewPager;
    PageAdapterGallery pageAdapter;
    BottomNavigationView bottomNavigationView;

    private ImageView loadedImage;
    ImageButton addPhotoButton;
    private static final int PICK_IMAGE = 1;
    private static final int SHOOT_PHOTO = 100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery);

        tabLayout = findViewById(R.id.tabLayout);
        tabItem1 = findViewById(R.id.tabGallery);
        tabItem2 = findViewById(R.id.tabFavorites);
        viewPager = findViewById(R.id.viewPager);
        backButton = findViewById(R.id.back_button);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        logoButton = findViewById(R.id.logo_button);


        loadedImage = findViewById(R.id.logo_button);/////////////////////////////////////////////////////////////////////////////////////////////
        addPhotoButton = findViewById(R.id.add_photo_button);

        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        pageAdapter = new PageAdapterGallery(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition()==0 || tab.getPosition()==1){
                    pageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(GalleryActivity.this, MainActivity.class);
                startActivity(showResult);
            }
        });

        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(GalleryActivity.this, MainActivity.class);
                startActivity(showResult);
            }
        });
    }

    public void selectImage() {
        final CharSequence[] items = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(GalleryActivity.this);
        builder.setTitle("Add image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Gallery")){
                    Intent gallery = new Intent();
                    gallery.setType("image/*");
                    gallery.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(gallery, "Select picture"), PICK_IMAGE);

                } else if (items[i].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, SHOOT_PHOTO);
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                loadedImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(requestCode == SHOOT_PHOTO){
            assert data != null;
            Bitmap captureImage = (Bitmap)data.getExtras().get("data");
            loadedImage.setImageBitmap(captureImage);
        }
    }
}