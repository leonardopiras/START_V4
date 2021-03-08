package com.example.startnewversionwithtabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelpGuideActivity extends AppCompatActivity {

    ImageButton logoButton, backButton;
    BottomNavigationView bottomNavigationView;
    ImageButton next_guide_page_prologue, next_guide_page_paths, prev_guide_page_paths, next_guide_page_gallery, prev_guide_page_gallery, next_guide_page_community, prev_guide_page_community, next_guide_page_sketch, prev_guide_page_sketch, next_guide_page_rewards, prev_guide_page_rewards;
    LinearLayout prologue, paths, gallery, community, sketch, rewards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_help_guide);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.orangeHeader));
        }

        backButton = findViewById(R.id.back_button);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        logoButton = findViewById(R.id.logo_button);

        next_guide_page_prologue = findViewById(R.id.next_guide_page);
        prev_guide_page_paths = findViewById(R.id.prev_guide_page_paths);
        next_guide_page_paths = findViewById(R.id.next_guide_page_paths);
        prev_guide_page_gallery = findViewById(R.id.prev_guide_page_gallery);
        next_guide_page_gallery = findViewById(R.id.next_guide_page_gallery);
        prev_guide_page_community = findViewById(R.id.prev_guide_page_community);
        next_guide_page_community = findViewById(R.id.next_guide_page_community);
        prev_guide_page_sketch = findViewById(R.id.prev_guide_page_sketch);
        next_guide_page_sketch = findViewById(R.id.next_guide_page_sketch);
        prev_guide_page_rewards = findViewById(R.id.prev_guide_page_rewards);
        next_guide_page_rewards = findViewById(R.id.return_to_main_menu);
        prologue = findViewById(R.id.guide_prologue);
        paths = findViewById(R.id.guide_paths);
        gallery = findViewById(R.id.guide_gallery);
        community = findViewById(R.id.guide_community);
        sketch = findViewById(R.id.guide_sketch);
        rewards = findViewById(R.id.guide_rewards);

        prologue.setVisibility(View.VISIBLE);
        paths.setVisibility(View.GONE);
        gallery.setVisibility(View.GONE);
        community.setVisibility(View.GONE);
        sketch.setVisibility(View.GONE);
        rewards.setVisibility(View.GONE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult;

                switch (readFile("Where Help Guide Is Called.txt.txt")){
                    case "UserProfileActivity\n":
                        showResult = new Intent(HelpGuideActivity.this, UserProfileActivity.class);
                        break;
                    case "GalleryActivity\n":
                        showResult = new Intent(HelpGuideActivity.this, GalleryActivity.class);
                        break;
                    case "CommunityActivity\n":
                        showResult = new Intent(HelpGuideActivity.this, CommunityActivity.class);
                        break;
                    case "StartTrainingActivity\n":
                        showResult = new Intent(HelpGuideActivity.this, StartTrainingActivity.class);
                        break;
                    case "SettingsActivity\n":
                        showResult = new Intent(HelpGuideActivity.this, SettingsActivity.class);
                        break;
                    case "SketchActivity\n":
                        showResult = new Intent(HelpGuideActivity.this, SketchActivity.class);
                        break;
                    default:
                        showResult = new Intent(HelpGuideActivity.this, MainActivity.class);
                }
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(HelpGuideActivity.this, MainActivity.class);
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(showResult);
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent showResult =  null;

                switch (item.getItemId()) {
                    case R.id.settings:
                        showResult = new Intent(HelpGuideActivity.this, SettingsActivity.class);
                        break;
                    case R.id.community:
                        showResult = new Intent(HelpGuideActivity.this, CommunityActivity.class);
                        break;
                    case R.id.training:
                        showResult = new Intent(HelpGuideActivity.this, StartTrainingActivity.class);
                        break;
                    case R.id.gallery:
                        showResult = new Intent(HelpGuideActivity.this, GalleryActivity.class);
                        break;
                    case R.id.profile:
                        showResult = new Intent(HelpGuideActivity.this, UserProfileActivity.class);
                        break;
                    default:
                        showResult = new Intent(HelpGuideActivity.this, MainActivity.class);
                }
                if (item.getItemId() != R.id.settings) {
                    showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(showResult);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                return true;
            }
        });

        switch (readFile("Where Help Guide Is Called.txt.txt")){
            case "StartTrainingActivity\n":
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.VISIBLE);
                gallery.setVisibility(View.GONE);
                community.setVisibility(View.GONE);
                sketch.setVisibility(View.GONE);
                rewards.setVisibility(View.GONE);
                break;
            case "GalleryActivity\n":
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.GONE);
                gallery.setVisibility(View.VISIBLE);
                community.setVisibility(View.GONE);
                sketch.setVisibility(View.GONE);
                rewards.setVisibility(View.GONE);
                break;
            case "CommunityActivity\n":
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.GONE);
                gallery.setVisibility(View.GONE);
                community.setVisibility(View.VISIBLE);
                sketch.setVisibility(View.GONE);
                rewards.setVisibility(View.GONE);
                break;
            case "UserProfileActivity\n":
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.GONE);
                gallery.setVisibility(View.GONE);
                community.setVisibility(View.GONE);
                sketch.setVisibility(View.GONE);
                rewards.setVisibility(View.VISIBLE);
                break;
            case "SketchActivity\n":
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.GONE);
                gallery.setVisibility(View.GONE);
                community.setVisibility(View.GONE);
                sketch.setVisibility(View.VISIBLE);
                rewards.setVisibility(View.GONE);
                break;
            default:
                prologue.setVisibility(View.VISIBLE);
                paths.setVisibility(View.GONE);
                gallery.setVisibility(View.GONE);
                community.setVisibility(View.GONE);
                sketch.setVisibility(View.GONE);
                rewards.setVisibility(View.GONE);
        }

        next_guide_page_prologue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.VISIBLE);
                gallery.setVisibility(View.GONE);
                community.setVisibility(View.GONE);
                sketch.setVisibility(View.GONE);
                rewards.setVisibility(View.GONE);
            }
        });

        prev_guide_page_paths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prologue.setVisibility(View.VISIBLE);
                paths.setVisibility(View.GONE);
                gallery.setVisibility(View.GONE);
                community.setVisibility(View.GONE);
                sketch.setVisibility(View.GONE);
                rewards.setVisibility(View.GONE);
            }
        });

        next_guide_page_paths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.GONE);
                gallery.setVisibility(View.VISIBLE);
                community.setVisibility(View.GONE);
                sketch.setVisibility(View.GONE);
                rewards.setVisibility(View.GONE);
            }
        });

        prev_guide_page_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.VISIBLE);
                gallery.setVisibility(View.GONE);
                community.setVisibility(View.GONE);
                sketch.setVisibility(View.GONE);
                rewards.setVisibility(View.GONE);
            }
        });

        next_guide_page_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.GONE);
                gallery.setVisibility(View.GONE);
                community.setVisibility(View.VISIBLE);
                sketch.setVisibility(View.GONE);
                rewards.setVisibility(View.GONE);
            }
        });

        prev_guide_page_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.GONE);
                gallery.setVisibility(View.VISIBLE);
                community.setVisibility(View.GONE);
                sketch.setVisibility(View.GONE);
                rewards.setVisibility(View.GONE);
            }
        });

        next_guide_page_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.GONE);
                gallery.setVisibility(View.GONE);
                community.setVisibility(View.GONE);
                sketch.setVisibility(View.VISIBLE);
                rewards.setVisibility(View.GONE);
            }
        });

        prev_guide_page_sketch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.GONE);
                gallery.setVisibility(View.GONE);
                community.setVisibility(View.VISIBLE);
                sketch.setVisibility(View.GONE);
                rewards.setVisibility(View.GONE);
            }
        });

        next_guide_page_sketch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.GONE);
                gallery.setVisibility(View.GONE);
                community.setVisibility(View.GONE);
                sketch.setVisibility(View.GONE);
                rewards.setVisibility(View.VISIBLE);
            }
        });

        prev_guide_page_rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prologue.setVisibility(View.GONE);
                paths.setVisibility(View.GONE);
                gallery.setVisibility(View.GONE);
                community.setVisibility(View.GONE);
                sketch.setVisibility(View.VISIBLE);
                rewards.setVisibility(View.GONE);
            }
        });

        next_guide_page_rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(HelpGuideActivity.this, MainActivity.class);
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
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