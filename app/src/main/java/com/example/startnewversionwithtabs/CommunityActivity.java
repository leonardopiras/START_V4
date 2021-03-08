package com.example.startnewversionwithtabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CommunityActivity extends AppCompatActivity {

    ImageButton backButton, logoButton, helpButton;
    TabLayout tabLayout;
    TabItem tabItem1, tabItem2;
    ViewPager viewPager;
    PageAdapterCommunity pageAdapter;
    BottomNavigationView bottomNavigationView;
    AppSessione appSessione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_community);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.orangeHeader));
        }

        appSessione = (AppSessione) getIntent().getParcelableExtra("AppSessione");

        tabLayout = findViewById(R.id.tabLayout);
        tabItem1 = findViewById(R.id.tabCommunity);
        tabItem2 = findViewById(R.id.tabMyPhotos);
        viewPager = findViewById(R.id.viewPager);
        backButton = findViewById(R.id.back_button);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        logoButton = findViewById(R.id.logo_button);
        helpButton = findViewById(R.id.help_button);


        pageAdapter = new PageAdapterCommunity(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0 || tab.getPosition() == 1) {
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


        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(CommunityActivity.this, HelpGuideActivity.class);
                writeFile("CommunityActivity","Where Help Guide Is Called.txt.txt");
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(CommunityActivity.this, MainActivity.class);
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                showResult.putExtra("AppSessione", appSessione);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                //Intent showResult = new Intent(CommunityActivity.this, MainActivity.class);
                //startActivity(showResult);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(CommunityActivity.this, MainActivity.class);
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                showResult.putExtra("AppSessione", appSessione);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                //Intent showResult = new Intent(CommunityActivity.this, MainActivity.class);
                //startActivity(showResult);
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.community);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent showResult =  null;

                switch (item.getItemId()) {
                    case R.id.settings:
                        showResult = new Intent(CommunityActivity.this, SettingsActivity.class);
                        break;
                    case R.id.community:
                        showResult = new Intent(CommunityActivity.this, CommunityActivity.class);
                        break;
                    case R.id.training:
                        showResult = new Intent(CommunityActivity.this, StartTrainingActivity.class);
                        break;
                    case R.id.gallery:
                        showResult = new Intent(CommunityActivity.this, GalleryActivity.class);
                        break;
                    case R.id.profile:
                        showResult = new Intent(CommunityActivity.this, UserProfileActivity.class);
                        break;
                    default:
                        showResult = new Intent(CommunityActivity.this, CommunityActivity.class);
                }
                if (item.getItemId() != R.id.community) {
                    showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    showResult.putExtra("AppSessione", appSessione);
                    startActivity(showResult);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }
                return true;
            }
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}