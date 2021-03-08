package com.example.startnewversionwithtabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ImageButton startTraining, gallery, community, sketch, settings, userProfile, helpGuide, logoBtn;
    String myEmail = (""), myPassword = ("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.orangeHeader));
        }

        if(readFile("Show guide dialog?.txt").equals("show\n")){
            openGuideDialog();
            writeFile("don't show in this session anymore","Show guide dialog?.txt");
        }

        AppSessione appSessione = (AppSessione) getIntent().getParcelableExtra("AppSessione");


        startTraining = findViewById(R.id.start_training_button);
        gallery = findViewById(R.id.gallery_button);
        community = findViewById(R.id.community_button);
        settings = findViewById(R.id.settings_button);
        userProfile = findViewById(R.id.user_icon_button);
        helpGuide = findViewById(R.id.help_button);
        sketch = findViewById(R.id.sketch_button);
        logoBtn = findViewById(R.id.logo_btn);

        logoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dbh = new DataBaseHelper(MainActivity.this);
                List<UserModel> everyone = dbh.getEveryUser();
                Toast.makeText(getBaseContext(), everyone.toString(), Toast.LENGTH_LONG).show();
                //logoBtn.setBackground(new BitmapDrawable(getResources(), everyone.get(3).immagineProfilo));
            }
        });

        startTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(MainActivity.this, StartTrainingActivity.class);
                showResult.putExtra("AppSessione", appSessione);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(MainActivity.this, GalleryActivity.class);
                showResult.putExtra("AppSessione", appSessione);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readFile("Logged in?.txt").equals("yes\n") || readFile("Logged in?.txt").equals("don't remember me\n")){
                    Intent showResult = new Intent(MainActivity.this, CommunityActivity.class);
                    showResult.putExtra("AppSessione", appSessione);
                    startActivity(showResult);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    openLoginDialog(1);
                }
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(MainActivity.this, SettingsActivity.class);
                showResult.putExtra("AppSessione", appSessione);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readFile("Logged in?.txt").equals("yes\n")){
                    Intent showResult = new Intent(MainActivity.this, UserProfileActivity.class);
                    showResult.putExtra("AppSessione", appSessione);
                    startActivity(showResult);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    openLoginDialog(0);
                }
            }
        });

        helpGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(MainActivity.this, HelpGuideActivity.class);
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        sketch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(MainActivity.this, SketchActivity.class);
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void openLoginDialog(int activity){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);

        View customView = getLayoutInflater().inflate(R.layout.login_dialog, null);

        dialog.setView(customView);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView loginWarningText = customView.findViewById(R.id.login_warning);

        if(activity == 1){
            loginWarningText.setVisibility(View.VISIBLE);
            loginWarningText.setError("");
        } else
            loginWarningText.setVisibility(View.GONE);


        Button insert = customView.findViewById(R.id.insert_button);
        Button register = customView.findViewById(R.id.register_button);
        Button forgotPassword = customView.findViewById(R.id.forgot_password_button);
        EditText email = customView.findViewById(R.id.email_textview);
        EditText password = customView.findViewById(R.id.password_textview);
        CheckBox rememberCredentials = customView.findViewById(R.id.remember_me_check_box);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals(myEmail) && password.getText().toString().equals(myPassword)){
                    if(rememberCredentials.isChecked())
                        writeFile("yes", "Logged in?.txt");
                    else
                        writeFile("don't remember me", "Logged in?.txt");

                    alertDialog.dismiss();
                    Intent showResult = new Intent(MainActivity.this, UserProfileActivity.class);
                    //showResult.putExtra("AppSessione", appSessione);
                    startActivity(showResult);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    //errore credenziali errate
                    password.setError("Password not correct");
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                openSignUpDialog();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"I know you can remember it", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openSignUpDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);

        View customView = getLayoutInflater().inflate(R.layout.register_login, null);

        dialog.setView(customView);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button cancel = customView.findViewById(R.id.cancel_sign_up_button);
        Button signUp = customView.findViewById(R.id.register_button);
        CheckBox checkBox = customView.findViewById(R.id.accept_check_box);
        EditText email = customView.findViewById(R.id.register_email_textview);
        EditText password = customView.findViewById(R.id.register_password_textview);
        EditText username = customView.findViewById(R.id.register_username_textview);
        EditText repeat_password = customView.findViewById(R.id.register_confirm_password_textview);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean it_is_all_good = true;
                String password_text = password.getText().toString();
                //String pattern;

                if(username.getText().toString().equals("")) {
                    username.setError("You must choose a valid username");
                    it_is_all_good = false;
                }

                if(!checkBox.isChecked()) {
                    checkBox.setError("You must accept the terms and conditions");
                    it_is_all_good = false;
                }

                if (!Pattern.matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$", email.getText().toString())) {
                    email.setError("E-mail address not valid");
                    it_is_all_good = false;
                }

                if(email.getText().toString().equals("")) {
                    email.setError("You must choose an e-mail address");
                    it_is_all_good = false;
                }

                if (!password.getText().toString().equals(repeat_password.getText().toString())) {
                    repeat_password.setError("Password not correct");
                    it_is_all_good = false;
                }

                if (!password_text.matches("^.{8,16}$")){
                    password.setError("Password must be between 8 and 16 digits long");
                    it_is_all_good = false;
                }

                if (password_text.matches("^[a-zA-Z]+$")){
                    password.setError("Password must include at least one numeric digit");
                    it_is_all_good = false;
                }

                if (password_text.matches("^[0-9a-z]+$")){
                    password.setError("Password must include at least one upper case digit");
                    it_is_all_good = false;
                }

                if (password_text.matches("^[0-9A-Z]+$")){
                    password.setError("Password must include at least one lower case digit");
                    it_is_all_good = false;
                }

                if (!password_text.matches("^\\S+$")){
                    password.setError("Password must not contain spaces");
                    it_is_all_good = false;
                }

                if(password.getText().toString().equals("")) {
                    password.setError("You must choose a password");
                    it_is_all_good = false;
                }

                if (it_is_all_good){
                    myEmail = email.getText().toString();
                    myPassword = password.getText().toString();

                    UserModel userModel;
                    try{
                        userModel = new UserModel(username.getText().toString(), email.getText().toString(), password.getText().toString(), true);
                    } catch (Exception e){
                        Toast.makeText(getBaseContext(), "Error in the user class creation", Toast.LENGTH_SHORT).show();
                        userModel = new UserModel("error", "error", "error", true);
                    }

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());

                    dataBaseHelper.addUser(userModel, getResources());

                    alertDialog.dismiss();
                    writeFile("yes", "Logged in?.txt");
                    Intent showResult = new Intent(MainActivity.this, UserProfileActivity.class);
                    //showResult.putExtra("AppSessione", appSessione);
                    startActivity(showResult);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });
    }

    private void openGuideDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);

        View customView = getLayoutInflater().inflate(R.layout.first_time_help_dialog_pop_up, null);

        Button ignore = customView.findViewById(R.id.ignore_button);
        Button guide = customView.findViewById(R.id.guide_button);
        CheckBox checkBox = customView.findViewById(R.id.check_box);

        dialog.setView(customView);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                    writeFile("don't show never again","Show guide dialog?.txt");

                alertDialog.dismiss();
            }
        });

        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                    writeFile("don't show never again","Show guide dialog?.txt");

                Intent showResult = new Intent(MainActivity.this, HelpGuideActivity.class);
                writeFile("GalleryActivity","Where Help Guide Is Called.txt.txt");
                showResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(showResult);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                alertDialog.dismiss();
            }
        });




        /*dialog.setContentView(R.layout.first_time_help_dialog_pop_up);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialog.getWindow().getAttributes().windowAnimations = R.style.AlertDialogTheme;

        //ImageView close = dialog.findViewById(R.id.close);
        dialog.show();*/
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