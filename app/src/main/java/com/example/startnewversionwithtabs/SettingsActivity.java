package com.example.startnewversionwithtabs;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {


    ImageButton backButton, logoButton;
    SwitchCompat nightModeSwitch, preventSleepSWitch;
    BottomNavigationView bottomNavigationView;
    AppSessione appSessione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.orangeHeader));
        }

        appSessione = (AppSessione) getIntent().getParcelableExtra("AppSessione");

        setContentView(R.layout.activity_settings);

        backButton = findViewById(R.id.back_button);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        logoButton = findViewById(R.id.logo_button);
        nightModeSwitch = findViewById(R.id.nightmode);
        preventSleepSWitch = findViewById(R.id.preventsleep);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(SettingsActivity.this, MainActivity.class);
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                showResult.putExtra("AppSessione", appSessione);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                //Intent showResult = new Intent(SettingsActivity.this, MainActivity.class);
                //startActivity(showResult);
            }
        });

        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(SettingsActivity.this, MainActivity.class);
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


                //Intent showResult = new Intent(SettingsActivity.this, MainActivity.class);
                //startActivity(showResult);
            }
        });


        preventSleepSWitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    Toast.makeText(getApplicationContext(), "Preventing phone from locking", Toast.LENGTH_LONG).show();
                } else {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            }
        });

        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    findViewById(R.id.constraintLayout).setBackgroundResource(R.drawable.header_style_night_mode);
                    nightModeSwitch.setBackgroundResource(R.drawable.header_style_night_mode);
                    nightModeSwitch.setTextColor(Color.WHITE);
                    preventSleepSWitch.setBackgroundResource(R.drawable.header_style_night_mode);
                    preventSleepSWitch.setTextColor(Color.WHITE);
                    bottomNavigationView.setBackgroundResource(R.drawable.header_style_night_mode);
                    getWindow().setNavigationBarColor(Color.BLACK);
                } else {
                    findViewById(R.id.constraintLayout).setBackgroundResource(R.drawable.header_style_doc);
                    nightModeSwitch.setBackgroundResource(R.color.translucentWhite);
                    nightModeSwitch.setTextColor(Color.BLACK);
                    preventSleepSWitch.setBackgroundResource(R.color.translucentWhite);
                    preventSleepSWitch.setTextColor(Color.BLACK);
                    bottomNavigationView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.header_style_doc, null));
                    getWindow().setNavigationBarColor(Color.WHITE);
                    getWindow().setNavigationBarContrastEnforced(true);
                }
            }
        });

        ImageButton helpButton = findViewById(R.id.help_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(SettingsActivity.this, HelpGuideActivity.class);
                writeFile("SettingsActivity","Where Help Guide Is Called.txt.txt");
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent showResult =  null;

                switch (item.getItemId()) {
                    case R.id.settings:
                        showResult = new Intent(SettingsActivity.this, SettingsActivity.class);
                        break;
                    case R.id.community:
                        showResult = new Intent(SettingsActivity.this, CommunityActivity.class);
                        break;
                    case R.id.training:
                        showResult = new Intent(SettingsActivity.this, StartTrainingActivity.class);
                        break;
                    case R.id.gallery:
                        showResult = new Intent(SettingsActivity.this, GalleryActivity.class);
                        break;
                    case R.id.profile:
                        showResult = new Intent(SettingsActivity.this, UserProfileActivity.class);
                        break;
                    default:
                        showResult = new Intent(SettingsActivity.this, SettingsActivity.class);
                }
                if (item.getItemId() != R.id.settings) {
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