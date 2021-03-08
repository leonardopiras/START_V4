package com.example.startnewversionwithtabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class CommunityActivity extends AppCompatActivity {
    
    ImageButton backButton, logoButton;
    TabLayout tabLayout;
    TabItem tabItem1, tabItem2;
    ViewPager viewPager;
    PageAdapterCommunity pageAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_community);

        tabLayout = findViewById(R.id.tabLayout);
        tabItem1 = findViewById(R.id.tabCommunity);
        tabItem2 = findViewById(R.id.tabMyPhotos);
        viewPager = findViewById(R.id.viewPager);
        backButton = findViewById(R.id.back_button);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        logoButton = findViewById(R.id.logo_button);

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

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(CommunityActivity.this, MainActivity.class);
                startActivity(showResult);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(CommunityActivity.this, MainActivity.class);
                startActivity(showResult);
            }
        });
    }
}
