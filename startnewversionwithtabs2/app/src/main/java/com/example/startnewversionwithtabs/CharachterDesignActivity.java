package com.example.startnewversionwithtabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CharachterDesignActivity extends AppCompatActivity {

    ImageButton backButton, logoButton, pref;
    public boolean star = false;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_charachter_design);

        Resources res = getResources();

        backButton = findViewById(R.id.back_button);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        logoButton = findViewById(R.id.logo_button);
        pref = findViewById(R.id.prefButton);

        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(CharachterDesignActivity.this, MainActivity.class);
                startActivity(showResult);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(CharachterDesignActivity.this, StartTrainingActivity.class);
                startActivity(showResult);
            }
        });

        pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!star){
                    pref.setBackground(ResourcesCompat.getDrawable(res, R.drawable.star_icon, null));
                    star = true;
                } else {
                    pref.setBackground(ResourcesCompat.getDrawable(res, R.drawable.empty_star_icon, null));
                    star = false;
                }
            }
        });
    }
}