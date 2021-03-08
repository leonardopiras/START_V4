package com.example.startnewversionwithtabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton startTraining, gallery, community, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        startTraining = findViewById(R.id.start_training_button);
        gallery = findViewById(R.id.gallery_button);
        community = findViewById(R.id.community_button);
        settings = findViewById(R.id.settings_button);

        startTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(MainActivity.this, StartTrainingActivity.class);
                startActivity(showResult);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(showResult);
            }
        });

        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(MainActivity.this, CommunityActivity.class);
                startActivity(showResult);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(showResult);
            }
        });
    }
}