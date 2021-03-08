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

public class SettingsActivity extends AppCompatActivity {


    ImageButton backButton, logoButton;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        backButton = findViewById(R.id.back_button);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        logoButton = findViewById(R.id.logo_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(showResult);
            }
        });

        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(showResult);
            }
        });
    }
}