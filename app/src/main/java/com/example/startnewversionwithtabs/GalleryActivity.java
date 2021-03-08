package com.example.startnewversionwithtabs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    ImageButton backButton, logoButton;
    TabLayout tabLayout;
    TabItem tabItem1, tabItem2;
    ViewPager viewPager;
    PageAdapterGallery pageAdapter;
    BottomNavigationView bottomNavigationView;
    AppSessione appSessione;
    int selectedFrag = 0;

    private ImageView loadedImage;
    ImageButton addPhotoButton;
    private static final int PICK_IMAGE = 1;
    private static final int SHOOT_PHOTO = 100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.orangeHeader));
        }

        appSessione = (AppSessione) getIntent().getParcelableExtra("AppSessione");

        setContentView(R.layout.activity_gallery);

        tabLayout = findViewById(R.id.tabLayout);
        tabItem1 = findViewById(R.id.tabGallery);
        tabItem2 = findViewById(R.id.tabFavorites);
        viewPager = findViewById(R.id.viewPager);
        backButton = findViewById(R.id.back_button);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        logoButton = findViewById(R.id.logo_button);
        addPhotoButton = findViewById(R.id.add_photo_button);

        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                selectedFrag = tab.getPosition();
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

                backButtonHandler();

                //Intent showResult = new Intent(GalleryActivity.this, MainActivity.class);
                //startActivity(showResult);
            }
        });
        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(GalleryActivity.this, MainActivity.class);
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                showResult.putExtra("AppSessione", appSessione);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


                //Intent showResult = new Intent(GalleryActivity.this, MainActivity.class);
                //startActivity(showResult);
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.gallery);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent showResult =  null;

                switch (item.getItemId()) {
                    case R.id.settings:
                        showResult = new Intent(GalleryActivity.this, SettingsActivity.class);
                        break;
                    case R.id.community:
                        showResult = new Intent(GalleryActivity.this, CommunityActivity.class);
                        break;
                    case R.id.training:
                        showResult = new Intent(GalleryActivity.this, StartTrainingActivity.class);
                        break;
                    case R.id.gallery:
                        showResult = new Intent(GalleryActivity.this, GalleryActivity.class);
                        break;
                    case R.id.profile:
                        showResult = new Intent(GalleryActivity.this, UserProfileActivity.class);
                        break;
                    default:
                        showResult = new Intent(GalleryActivity.this, GalleryActivity.class);
                }
                if (item.getItemId() != R.id.gallery) {
                    showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    showResult.putExtra("AppSessione", appSessione);
                    startActivity(showResult);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }
                return true;
            }
        });
    }

    public void selectImage() {
        final CharSequence[] items = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(GalleryActivity.this, R.style.AlertDialogTheme);
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
        AlertDialog alertDialogObject = builder.create();

        ListView listView=alertDialogObject.getListView();
        listView.setDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.header_style_doc, null)); // set color
        listView.setDividerHeight(2); // set height
        alertDialogObject.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (resultCode == RESULT_OK && requestCode != SHOOT_PHOTO) {
                appSessione = (AppSessione) getIntent().getParcelableExtra("AppSessione");
            }
        }

        if(requestCode == SHOOT_PHOTO){
            assert data != null;
            bitmap = (Bitmap)data.getExtras().get("data");
        }

        if (bitmap != null) { // Se abbiamo aggiunto una foto
            proceduraAggiuntaImmagine(bitmap);

        } else {
            int i = 5;
            // Se si decide di non scattare nessuna foto
        }

    }


    public void proceduraAggiuntaImmagine(Bitmap bitmapImage) {

        AlertDialog.Builder dialogNome = new AlertDialog.Builder(GalleryActivity.this, R.style.AlertDialogTheme);

        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add_image, null);
        ImageView imageView = (ImageView) customLayout.findViewById(R.id.image_viewDialog_add_image);
        imageView.setImageBitmap(bitmapImage);
        List<String> stringArrayCategories = Arrays.asList(getResources().getStringArray(R.array.categorySpinner));
        ArrayAdapter<String> adapterCat = new ArrayAdapter<>(GalleryActivity.this, R.layout.simple_list_item_multiple_choice_custom, stringArrayCategories);
        ExpandableHeightGridView listViewCategories = customLayout.findViewById(R.id.list_viewChooseCategoryImg);
        listViewCategories.setAdapter(adapterCat);
        dialogNome.setView(customLayout);
        dialogNome.setIcon(new BitmapDrawable(getResources(), bitmapImage));
        dialogNome.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int i = 5+4;
            }
        });
        dialogNome.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialogAle = dialogNome.create();

        dialogAle.setOnShowListener(new DialogInterface.OnShowListener() {
                                        @Override
                                        public void onShow(DialogInterface dialog) {
                                            ArrayList<Category> cat = new ArrayList<>();
                                            Button b = dialogAle.getButton(AlertDialog.BUTTON_POSITIVE);
                                            //Spinner spinnerCategory  = customLayout.findViewById(R.id.spinner);
                                            listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    cat.add(Category.getCategoryAtPosition(position));
                                                }
                                            });

                                            EditText editTextName = customLayout.findViewById(R.id.edit_textDialog_add_image);
                                            editTextName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
                                            b.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String chosenName = editTextName.getText().toString();

                                                    if (!chosenName.isEmpty() && !cat.isEmpty()) {
                                                        String pngImgSourcePath = creaImmaginePngInMemory(chosenName, bitmapImage);

                                                        appSessione.images.add(new Image(chosenName, 0f, appSessione.user, new ArrayList<Comment>(),
                                                                pngImgSourcePath, false, "", false, cat));
                                                        dialogAle.dismiss();
                                                    } else {
                                                        TextView warning;
                                                        TextView imageTitleTextView = customLayout.findViewById(R.id.text_viewDialog_add_image);
                                                        TextView imageCategoryTextView = customLayout.findViewById(R.id.text2_viewDialog_add_image);

                                                        if (chosenName.isEmpty()) {
                                                            imageTitleTextView.setTextColor(getResources().getColor(R.color.red));
                                                            warning = customLayout.findViewById(R.id.warnigTextTitle);
                                                            warning.setVisibility(View.VISIBLE);
                                                        } else {
                                                            imageTitleTextView.setTextColor(getResources().getColor(R.color.black));
                                                            warning = customLayout.findViewById(R.id.warnigTextTitle);
                                                            warning.setVisibility(View.INVISIBLE);
                                                        }

                                                        if (cat.isEmpty()) {
                                                            imageCategoryTextView.setTextColor(getResources().getColor(R.color.red));
                                                            warning = customLayout.findViewById(R.id.warningChooseCategoryText);
                                                            warning.setVisibility(View.VISIBLE);
                                                        } else {
                                                            imageCategoryTextView.setTextColor(getResources().getColor(R.color.black));
                                                            warning = customLayout.findViewById(R.id.warningChooseCategoryText);
                                                            warning.setVisibility(View.INVISIBLE);
                                                        }
                                                    }
                                                }
                                            });

                                        }
                                    }
        );
        dialogAle.show();



    }

    public String creaImmaginePngInMemory(String imageName, Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("image_directory", Context.MODE_PRIVATE);
        File pngImageInMemory = new File(directory, imageName + ".png");

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

        return pngImageInMemory.toString();
    }




    public void backButtonHandler() {

        List<Fragment> fragments = (List<Fragment>) getSupportFragmentManager().getFragments();
        for (Fragment frag : fragments) {
            if (frag instanceof GalleryFragment && selectedFrag == 0) {
                if (frag.getArguments() == null) {
                    Intent showResult = new Intent(GalleryActivity.this, MainActivity.class);
                    showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    showResult.putExtra("AppSessione", appSessione);
                    startActivity(showResult);
                } else {
                    if (frag.getArguments().getBoolean("Categories", true)) {
                        Intent showResult = new Intent(GalleryActivity.this, MainActivity.class);
                        showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        showResult.putExtra("AppSessione", appSessione);
                        startActivity(showResult);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    } else {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("Categories", true);
                        frag.setArguments(bundle);
                        frag.getParentFragmentManager().beginTransaction().detach(frag).commit();
                        frag.getParentFragmentManager().beginTransaction().attach(frag).commit();

                    }
                }
            }
            if (frag instanceof FavoritesFragment && selectedFrag == 1) {
                Intent showResult = new Intent(GalleryActivity.this, MainActivity.class);
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                showResult.putExtra("AppSessione", appSessione);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
       int num = fragmentManager.getBackStackEntryCount();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            backButtonHandler();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            getSupportFragmentManager().popBackStackImmediate();

        }
    }
}