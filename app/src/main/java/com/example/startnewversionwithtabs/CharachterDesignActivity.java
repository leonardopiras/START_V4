package com.example.startnewversionwithtabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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

        if(readFile().equals("true\n")){
            star = true;
            pref.setBackground(ResourcesCompat.getDrawable(res, R.drawable.star_icon, null));
        } else {
            star = false;
            pref.setBackground(ResourcesCompat.getDrawable(res, R.drawable.empty_star_icon, null));
        }

        logoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showResult = new Intent(CharachterDesignActivity.this, MainActivity.class);
                    showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(showResult);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    //Intent showResult = new Intent(CharachterDesignActivity.this, MainActivity.class);
                    //startActivity(showResult);
                }
            });

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showResult = new Intent(CharachterDesignActivity.this, StartTrainingActivity.class);
                    showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(showResult);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    //Intent showResult = new Intent(CharachterDesignActivity.this, StartTrainingActivity.class);
                    //startActivity(showResult);
                }
            });

            pref.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!star) {
                        pref.setBackground(ResourcesCompat.getDrawable(res, R.drawable.star_icon, null));
                        star = true;
                        Toast.makeText(getBaseContext(), "Add to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        pref.setBackground(ResourcesCompat.getDrawable(res, R.drawable.empty_star_icon, null));
                        star = false;
                        Toast.makeText(getBaseContext(), "Remove from favorites", Toast.LENGTH_SHORT).show();
                    }
                    writeFile(star);
                }
            });
        }

    public void writeFile(boolean text) {
        String textToSave;

        if(text)
            textToSave = "true";
        else
            textToSave = "false";

        try {
            FileOutputStream fileOutputStream = openFileOutput("Tutorial File.txt", MODE_PRIVATE);
            fileOutputStream.write(textToSave.getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile() {
        String textToLoad = null;
        try {
            FileInputStream fileInputStream = openFileInput("Tutorial File.txt");
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