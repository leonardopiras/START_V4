package com.example.startnewversionwithtabs;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.ColorUtils;

public class SketchActivity extends AppCompatActivity implements OnClickListener {

    private com.example.startnewversionwithtabs.DrawingView drawView;
    TextView brushSizeValue, eraserSizeValue;
    ImageButton drawEraseBtn, baru, save, backButton, logoButton, helpButton, full_screen, brush_options;
    LinearLayout paintLayout, brushOptionLayout, eraserOptionLayout;
    ConstraintLayout headerLayout;
    SeekBar sizeBar;
    boolean isBrush = true, isFullScreen = false, isBrushOptionInView = false;

    /*TextView hueValue, lightnessValue;
    SeekBar colorBar, lightBar;
    public int RGBcolor;
    public String RGBcolorString;
    public float  hue = 0, saturation = 1f, lightness = 0.5f;
    public float[] hsl;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sketch);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.orangeHeader));
        }

        /*hsl = new float[3];
        hsl[0] = hue;
        hsl[1] = saturation;
        hsl[2] = lightness;*/

        backButton = findViewById(R.id.back_button);
        logoButton = findViewById(R.id.logo_button);
        helpButton = findViewById(R.id.help_button);
        headerLayout = findViewById(R.id.headerConstraintLayout);
        drawView = (com.example.startnewversionwithtabs.DrawingView)findViewById(R.id.drawing);
        drawEraseBtn = (ImageButton) findViewById(R.id.draw_erase_btn);
        baru = (ImageButton) findViewById(R.id.new_btn);
        save = (ImageButton) findViewById(R.id.save_btn);
        paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        full_screen = findViewById(R.id.full_screen);

        brush_options = findViewById(R.id.brush_option);
        brushOptionLayout = findViewById(R.id.brush_option_layout);
        eraserOptionLayout = findViewById(R.id.eraser_size_layout);

        /*hueValue = findViewById(R.id.hue_value);
        lightnessValue = findViewById(R.id.lightness_value);
        colorBar = findViewById(R.id.color_bar);
        lightBar = findViewById(R.id.light_bar);*/

        brushSizeValue = findViewById(R.id.size_value);
        eraserSizeValue = findViewById(R.id.eraser_size_value);
        sizeBar = findViewById(R.id.size_bar);

        drawEraseBtn.setOnClickListener(this);
        baru.setOnClickListener(this);
        save.setOnClickListener(this);

        sizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brushSizeValue.setText(""+((int) sizeBar.getProgress()+5));
                brush_options.setScaleX((float) (sizeBar.getProgress()/31.25f)+0.2f);
                brush_options.setScaleY((float) (sizeBar.getProgress()/31.25f)+0.2f);
                drawView.setBrushSize(sizeBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        full_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFullScreen){
                    headerLayout.setVisibility(View.VISIBLE);
                    full_screen.setBackground(getResources().getDrawable(R.drawable.ic_baseline_open_in_full_24));
                    isFullScreen = false;
                } else {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                    headerLayout.setVisibility(View.GONE);
                    full_screen.setBackground(getResources().getDrawable(R.drawable.ic_baseline_close_fullscreen_24));
                    isFullScreen = true;
                }
            }
        });

        brush_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBrushOptionInView){
                    brushOptionLayout.setVisibility(View.GONE);
                    isBrushOptionInView = false;
                } else {
                    brushOptionLayout.setVisibility(View.VISIBLE);
                    isBrushOptionInView = true;
                }
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(SketchActivity.this, HelpGuideActivity.class);
                writeFile("SketchActivity","Where Help Guide Is Called.txt.txt");
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(SketchActivity.this, MainActivity.class);
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //showResult.putExtra("AppSessione", appSessione);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(SketchActivity.this, MainActivity.class);
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //showResult.putExtra("AppSessione", appSessione);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

    ImageButton prevImgView;

    public void paintClicked(View view){
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            if(prevImgView != null)
                prevImgView.setImageDrawable(getResources().getDrawable(R.drawable.empty));
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            prevImgView = (ImageButton)view;
            switch (color){
                case "#FFFF0000":
                    brush_options.getBackground().setColorFilter(Color.parseColor("#FFFF0000"), PorterDuff.Mode.SRC_ATOP);
                    break;
                case "#FFFF6600":
                    brush_options.getBackground().setColorFilter(Color.parseColor("#FFFF6600"), PorterDuff.Mode.SRC_ATOP);
                    break;
                case "#FFFFCC00":
                    brush_options.getBackground().setColorFilter(Color.parseColor("#FFFFCC00"), PorterDuff.Mode.SRC_ATOP);
                    break;
                case "#FF009900":
                    brush_options.getBackground().setColorFilter(Color.parseColor("#FF009900"), PorterDuff.Mode.SRC_ATOP);
                    break;
                case "#FF009999":
                    brush_options.getBackground().setColorFilter(Color.parseColor("#FF009999"), PorterDuff.Mode.SRC_ATOP);
                    break;
                case "#FF0000FF":
                    brush_options.getBackground().setColorFilter(Color.parseColor("#FF0000FF"), PorterDuff.Mode.SRC_ATOP);
                    break;
                case "#FF990099":
                    brush_options.getBackground().setColorFilter(Color.parseColor("#FF990099"), PorterDuff.Mode.SRC_ATOP);
                    break;
                case "#FFFF6666":
                    brush_options.getBackground().setColorFilter(Color.parseColor("#FFFF6666"), PorterDuff.Mode.SRC_ATOP);
                    break;
                case "#FFFFFFFF":
                    brush_options.getBackground().setColorFilter(Color.parseColor("#FFFFFFFF"), PorterDuff.Mode.SRC_ATOP);
                    break;
                case "#FF787878":
                    brush_options.getBackground().setColorFilter(Color.parseColor("#FF787878"), PorterDuff.Mode.SRC_ATOP);
                    break;
                case "#000000":
                    brush_options.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
                    break;
                default:
                    brush_options.getBackground().setColorFilter(Color.parseColor("#00000000"), PorterDuff.Mode.SRC_ATOP);
                    break;
            }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.draw_erase_btn){
            if(isBrush){
                drawEraseBtn.setBackground(getResources().getDrawable(R.drawable.ic_baseline_brush_24));
                drawView.setErase(true);
                //drawView.setBrushSize(drawView.getLastBrushSize());
                isBrush = false;
            } else {
                drawEraseBtn.setBackground(getResources().getDrawable(R.drawable.ic_baseline_cleaning_services_24));
                drawView.setupDrawing();
                isBrush = true;
            }
        }

        if(v.getId()==R.id.new_btn)
            openNewDrawDialog();

        if(v.getId()==R.id.save_btn)
            openSaveDrawDialog();

    }
    private void openNewDrawDialog(){
        androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(SketchActivity.this, R.style.AlertDialogTheme);

        View customView = getLayoutInflater().inflate(R.layout.new_draw_dialog, null);

        dialog.setView(customView);

        androidx.appcompat.app.AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button yes = customView.findViewById(R.id.yes_button);
        Button no = customView.findViewById(R.id.no_button);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.startNew();
                alertDialog.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void openSaveDrawDialog(){
        androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(SketchActivity.this, R.style.AlertDialogTheme);

        View customView = getLayoutInflater().inflate(R.layout.save_draw_dialog, null);

        dialog.setView(customView);

        androidx.appcompat.app.AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button yes = customView.findViewById(R.id.yes_button);
        Button no = customView.findViewById(R.id.no_button);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.setDrawingCacheEnabled(true);
                String imgSaved = MediaStore.Images.Media.insertImage(
                        getContentResolver(), drawView.getDrawingCache(),
                        UUID.randomUUID().toString()+".png", "drawing");
                if(imgSaved!=null){
                    Toast savedToast = Toast.makeText(getApplicationContext(),
                            "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                    savedToast.show();
                }
                else{
                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                            "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                    unsavedToast.show();
                }
                drawView.destroyDrawingCache();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    /*public void seekbars(SeekBar color, SeekBar light, SeekBar size, SeekBar eraser){
        color.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hsl[0] = color.getProgress();
                hueValue.setText(""+((int) color.getProgress()));
                RGBcolor = androidx.core.graphics.ColorUtils.HSLToColor(hsl);
                brush_options.setBackgroundTintList(ColorStateList.valueOf(RGBcolor));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                drawView.setColor(RGBcolor+"");
            }
        });

        light.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hsl[2] = (float) light.getProgress()/100;
                lightnessValue.setText(""+((int) light.getProgress()));
                RGBcolor = androidx.core.graphics.ColorUtils.HSLToColor(hsl);
                brush_options.setBackgroundTintList(ColorStateList.valueOf(RGBcolor));
                //drawView.setColor(HEXcolor+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brushSizeValue.setText(""+((int) size.getProgress()+5));
                brush_options.setScaleX((float) size.getProgress()/25);
                brush_options.setScaleY((float) size.getProgress()/25);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        eraser.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brushSizeValue.setText(""+((int) eraser.getProgress()+5));
                brush_options.setScaleX((float) eraser.getProgress()/25);
                brush_options.setScaleY((float) eraser.getProgress()/25);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }*/
}