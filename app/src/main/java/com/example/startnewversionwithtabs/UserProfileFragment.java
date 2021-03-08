package com.example.startnewversionwithtabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    LinearLayout personalData;
    Button logoutBtn;
    ImageButton userProfilePic, changePassword, changeEmail;
    TextView username, badge, level, email, password, photoShared, photoRated, photoCommented;
    TextView landscapesCompletionRate, landscapesLevel, landscapesBadge;
    TextView charDesignCompletionRate, charDesignLevel, charDesignBadge;
    TextView comicsCompletionRate, comicsLevel, comicsBadge;
    TextView logosCompletionRate, logosLevel, logosBadge;
    User userTest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        findUserProfileFragmentId(view);

        //initializeUserProfile();
        setStats(view);
        medalsManager(view);

        logoutManager(view);
        emailManager(view);
        passwordManager(view);

        return view;
    }

    public void setStats(View view){
        DataBaseHelper dbh = new DataBaseHelper(view.getContext());
        List<UserModel> everyone = dbh.getEveryUser();

        username.setText(everyone.get(0).getNome());    // dbh.dbSize()
        email.setText(everyone.get(0).getEmail());
        password.setText(passwordCalculator(everyone.get(0).getPassword()));

        level.setText(everyone.get(0).getLivelloGlobale()+"");
        logosLevel.setText(everyone.get(0).getLivelloLogos()+"");
        comicsLevel.setText(everyone.get(0).getLivelloComics()+"");
        charDesignLevel.setText(everyone.get(0).getLivelloCharDesign()+"");
        landscapesLevel.setText(everyone.get(0).getLivelloLandscapes()+"");

        landscapesCompletionRate.setText(everyone.get(0).getPath_Landscapes()+"");
        logosCompletionRate.setText(everyone.get(0).getPath_Comics()+"");
        comicsCompletionRate.setText(everyone.get(0).getPath_Comics()+"");
        charDesignCompletionRate.setText(everyone.get(0).getPath_CharDesign()+"");

        photoCommented.setText(everyone.get(0).getNumeroFotoCommentate()+"");
        photoRated.setText(everyone.get(0).getNumeroFotoValutate()+"");
        photoShared.setText(everyone.get(0).getNumeroFotoCondivise()+"");

        logosBadge.setText(badgeCalculator(everyone.get(0).getLivelloLogos()));
        comicsBadge.setText(badgeCalculator(everyone.get(0).getLivelloComics()));
        landscapesBadge.setText(badgeCalculator(everyone.get(0).getLivelloLandscapes()));
        charDesignBadge.setText(badgeCalculator(everyone.get(0).getLivelloCharDesign()));
        badge.setText(badgeCalculator(everyone.get(0).getLivelloGlobale()));
    }

    public String badgeCalculator(int livello){
        if(livello <= 0)
            return "N/A";
        else if(livello < 10)
            return "Newbie";
        else if(livello < 20)
            return "Novice";
        else if(livello < 30)
            return "Practical";
        else if(livello < 40)
            return "Expert";
        else if(livello >= 40)
            return "Professionist";
        else
            return "N/A";
    }

    public String passwordCalculator(String password){
        StringBuilder result = new StringBuilder();

        for (int i=0; i<password.length(); i++){
            result.append("*");
        }
        return result.toString();
    }

    public void findUserProfileFragmentId(View view){
        userProfilePic = view.findViewById(R.id.userProfilePic);
        username = view.findViewById(R.id.username);
        badge = view.findViewById(R.id.badgeTextId);
        level = view.findViewById(R.id.levelTextId);
        email = view.findViewById(R.id.emailTextId);
        password = view.findViewById(R.id.passwordTextId);
        photoShared = view.findViewById(R.id.photos_shared_number);
        photoRated = view.findViewById(R.id.photos_rated_number);
        photoCommented = view.findViewById(R.id.photos_commented_number);
        personalData = view.findViewById(R.id.personal_data);
        changeEmail = view.findViewById(R.id.modifyEmailButton);
        changePassword = view.findViewById(R.id.modifyPasswordButton);

        landscapesCompletionRate = view.findViewById(R.id.landscapes_completion_rate);
        landscapesLevel = view.findViewById(R.id.landscapes_level);
        landscapesBadge = view.findViewById(R.id.landscapes_badge);
        charDesignCompletionRate = view.findViewById(R.id.character_design_completion_rate);
        charDesignLevel = view.findViewById(R.id.character_design_level);
        charDesignBadge = view.findViewById(R.id.character_design_badge);
        comicsCompletionRate = view.findViewById(R.id.comics_completion_rate);
        comicsLevel = view.findViewById(R.id.comics_level);
        comicsBadge = view.findViewById(R.id.comics_badge);
        logosCompletionRate = view.findViewById(R.id.logos_completion_rate);
        logosLevel = view.findViewById(R.id.logos_level);
        logosBadge = view.findViewById(R.id.logos_badge);
    }

    public void initializeUserProfile(){
        userTest = new User("utenteProva", 4, 22, 14,
                44, "test@gmail.com", "pass", 36, 100,
                0, 0, 12, 22, 6
                , true, 1, "");

        if(!userTest.isOurProfile)
            personalData.setVisibility(View.GONE);

        username.setText(userTest.nome);
        level.setText(String.valueOf(userTest.livelloGlobale));
        badge.setText(badgeCalculator(userTest.livelloGlobale));
        email.setText(userTest.email);
        password.setText(passwordCalculator(userTest.password));
        photoShared.setText(String.valueOf(userTest.numeroFotoCondivise));
        photoRated.setText(String.valueOf(userTest.numeroFotoValutate));
        photoCommented.setText(String.valueOf(userTest.numeroFotoCommentate));

        landscapesCompletionRate.setText(String.valueOf(userTest.path_Landscapes));
        landscapesLevel.setText(String.valueOf(userTest.livelloLandscapes));
        landscapesBadge.setText(badgeCalculator(userTest.livelloLandscapes));
        charDesignCompletionRate.setText(String.valueOf(userTest.path_CharDesign));
        charDesignLevel.setText(String.valueOf(userTest.livelloCharDesign));
        charDesignBadge.setText(badgeCalculator(userTest.livelloCharDesign));
        comicsCompletionRate.setText(String.valueOf(userTest.path_Comics));
        comicsLevel.setText(String.valueOf(userTest.livelloComics));
        comicsBadge.setText(badgeCalculator(userTest.livelloComics));
        logosCompletionRate.setText(String.valueOf(userTest.path_Logos));
        logosLevel.setText(String.valueOf(userTest.livelloLogos));
        logosBadge.setText(badgeCalculator(userTest.livelloLogos));
    }

    public void emailManager(View view){
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog cambio email
            }
        });
    }

    public void passwordManager(View view){
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog cambio password
            }
        });
    }

    public void changeProfilePic(View view){
        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog cambio immagine profilo
            }
        });
    }

    public void logoutManager(View view){
        logoutBtn = view.findViewById(R.id.logout_button);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogoutConfirmDialog();
            }
        });
    }

    private void openLogoutConfirmDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);

        View customView = getLayoutInflater().inflate(R.layout.logout_confirm_dialog, null);

        dialog.setView(customView);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button back = customView.findViewById(R.id.back_btn);
        Button exit = customView.findViewById(R.id.exit_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeFile("no", "Logged in?.txt");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    public String readFile(String fileName) {
        String textToLoad = null;

        try {
            FileInputStream fileInputStream = getActivity().openFileInput(fileName);
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

    public void writeFile(String textToSave, String fileName) {

        try {
            FileOutputStream fileOutputStream = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(textToSave.getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void medalsManager(View view){
        DataBaseHelper dbh = new DataBaseHelper(view.getContext());
        List<UserModel> everyone = dbh.getEveryUser();

        ImageButton empty_character_design, bronze_character_design, silver_character_design, gold_character_design,
                empty_landscapes, bronze_landscapes, silver_landscapes, gold_landscapes,
                empty_comics, bronze_comics, silver_comics, gold_comics,
                empty_logos, bronze_logos, silver_logos, gold_logos;

        empty_character_design = view.findViewById(R.id.character_design_not_awarded_medal);
        empty_landscapes = view.findViewById(R.id.landscapes_not_awarded_medal);
        empty_comics = view.findViewById(R.id.comics_not_awarded_medal);
        empty_logos = view.findViewById(R.id.logos_not_awarded_medal);
        bronze_character_design = view.findViewById(R.id.bronze_character_design_medal);
        bronze_landscapes = view.findViewById(R.id.bronze_landscapes_medal);
        bronze_comics = view.findViewById(R.id.bronze_comics_medal);
        bronze_logos = view.findViewById(R.id.bronze_logos_medal);
        silver_character_design = view.findViewById(R.id.silver_character_design_medal);
        silver_landscapes = view.findViewById(R.id.silver_landscapes_medal);
        silver_comics = view.findViewById(R.id.silver_comics_medal);
        silver_logos = view.findViewById(R.id.silver_logos_medal);
        gold_character_design = view.findViewById(R.id.gold_character_design_medal);
        gold_landscapes = view.findViewById(R.id.gold_landscapes_medal);
        gold_comics = view.findViewById(R.id.gold_comics_medal);
        gold_logos = view.findViewById(R.id.gold_logos_medal);

        bronze_character_design.setVisibility(View.GONE);
        bronze_landscapes.setVisibility(View.GONE);
        bronze_comics.setVisibility(View.GONE);
        bronze_logos.setVisibility(View.GONE);
        silver_character_design.setVisibility(View.GONE);
        silver_landscapes.setVisibility(View.GONE);
        silver_comics.setVisibility(View.GONE);
        silver_logos.setVisibility(View.GONE);
        gold_character_design.setVisibility(View.GONE);
        gold_landscapes.setVisibility(View.GONE);
        gold_comics.setVisibility(View.GONE);
        gold_logos.setVisibility(View.GONE);

        if(everyone.get(0).getLivelloLogos() >= 10 && everyone.get(0).getLivelloLogos() < 25){
            empty_logos.setVisibility(View.GONE);
            bronze_logos.setVisibility(View.VISIBLE);
            silver_logos.setVisibility(View.GONE);
            gold_logos.setVisibility(View.GONE);
        } else if (everyone.get(0).getLivelloLogos() >= 25 && everyone.get(0).getLivelloLogos() < 40){
            empty_logos.setVisibility(View.GONE);
            bronze_logos.setVisibility(View.GONE);
            silver_logos.setVisibility(View.VISIBLE);
            gold_logos.setVisibility(View.GONE);
        } else if (everyone.get(0).getLivelloLogos() >= 40){
            empty_logos.setVisibility(View.GONE);
            bronze_logos.setVisibility(View.GONE);
            silver_logos.setVisibility(View.GONE);
            gold_logos.setVisibility(View.VISIBLE);
        } else {
            empty_logos.setVisibility(View.VISIBLE);
            bronze_logos.setVisibility(View.GONE);
            silver_logos.setVisibility(View.GONE);
            gold_logos.setVisibility(View.GONE);
        }

        if(everyone.get(0).getLivelloCharDesign() >= 10 && everyone.get(0).getLivelloCharDesign() < 25){
            empty_character_design.setVisibility(View.GONE);
            bronze_character_design.setVisibility(View.VISIBLE);
            silver_character_design.setVisibility(View.GONE);
            gold_character_design.setVisibility(View.GONE);
        } else if (everyone.get(0).getLivelloCharDesign() >= 25 && everyone.get(0).getLivelloCharDesign() < 40){
            empty_character_design.setVisibility(View.GONE);
            bronze_character_design.setVisibility(View.GONE);
            silver_character_design.setVisibility(View.VISIBLE);
            gold_character_design.setVisibility(View.GONE);
        } else if (everyone.get(0).getLivelloCharDesign() >= 40){
            empty_character_design.setVisibility(View.GONE);
            bronze_character_design.setVisibility(View.GONE);
            silver_character_design.setVisibility(View.GONE);
            gold_character_design.setVisibility(View.VISIBLE);
        } else {
            empty_character_design.setVisibility(View.VISIBLE);
            bronze_character_design.setVisibility(View.GONE);
            silver_character_design.setVisibility(View.GONE);
            gold_character_design.setVisibility(View.GONE);
        }

        if(everyone.get(0).getLivelloLandscapes() >= 10 && everyone.get(0).getLivelloLandscapes() < 25){
            empty_landscapes.setVisibility(View.GONE);
            bronze_landscapes.setVisibility(View.VISIBLE);
            silver_landscapes.setVisibility(View.GONE);
            gold_landscapes.setVisibility(View.GONE);
        } else if (everyone.get(0).getLivelloLandscapes() >= 25 && everyone.get(0).getLivelloLandscapes() < 40){
            empty_landscapes.setVisibility(View.GONE);
            bronze_landscapes.setVisibility(View.GONE);
            silver_landscapes.setVisibility(View.VISIBLE);
            gold_landscapes.setVisibility(View.GONE);
        } else if (everyone.get(0).getLivelloLandscapes() >= 40){
            empty_landscapes.setVisibility(View.GONE);
            bronze_landscapes.setVisibility(View.GONE);
            silver_landscapes.setVisibility(View.GONE);
            gold_landscapes.setVisibility(View.VISIBLE);
        } else {
            empty_landscapes.setVisibility(View.VISIBLE);
            bronze_landscapes.setVisibility(View.GONE);
            silver_landscapes.setVisibility(View.GONE);
            gold_landscapes.setVisibility(View.GONE);
        }

        if(everyone.get(0).getLivelloComics() >= 10 && everyone.get(0).getLivelloComics() < 25){
            empty_comics.setVisibility(View.GONE);
            bronze_comics.setVisibility(View.VISIBLE);
            silver_comics.setVisibility(View.GONE);
            gold_comics.setVisibility(View.GONE);
        } else if (everyone.get(0).getLivelloComics() >= 25 && everyone.get(0).getLivelloComics() < 40){
            empty_comics.setVisibility(View.GONE);
            bronze_comics.setVisibility(View.GONE);
            silver_comics.setVisibility(View.VISIBLE);
            gold_comics.setVisibility(View.GONE);
        } else if (everyone.get(0).getLivelloComics() >= 40){
            empty_comics.setVisibility(View.GONE);
            bronze_comics.setVisibility(View.GONE);
            silver_comics.setVisibility(View.GONE);
            gold_comics.setVisibility(View.VISIBLE);
        } else {
            empty_comics.setVisibility(View.VISIBLE);
            bronze_comics.setVisibility(View.GONE);
            silver_comics.setVisibility(View.GONE);
            gold_comics.setVisibility(View.GONE);
        }


        empty_character_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reach level 10 in character design category", Toast.LENGTH_SHORT).show();
            }
        });
        empty_landscapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reach level 10 in landscapes category", Toast.LENGTH_SHORT).show();
            }
        });
        empty_comics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reach level 10 in comics category", Toast.LENGTH_SHORT).show();
            }
        });
        empty_logos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reach level 10 in logos category", Toast.LENGTH_SHORT).show();
            }
        });
        bronze_character_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reached level 10 in character design category", Toast.LENGTH_SHORT).show();
            }
        });
        bronze_landscapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reached level 10 in landscapes category", Toast.LENGTH_SHORT).show();
            }
        });
        bronze_comics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reached level 10 in comics category", Toast.LENGTH_SHORT).show();
            }
        });
        bronze_logos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reached level 10 in logos category", Toast.LENGTH_SHORT).show();
            }
        });
        silver_character_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reached level 25 in character design category", Toast.LENGTH_SHORT).show();
            }
        });
        silver_landscapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reached level 25 in landscapes category", Toast.LENGTH_SHORT).show();
            }
        });
        silver_comics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reached level 25 in comics category", Toast.LENGTH_SHORT).show();
            }
        });
        silver_logos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reached level 25 in logos category", Toast.LENGTH_SHORT).show();
            }
        });
        gold_character_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reached level 40 in character design category", Toast.LENGTH_SHORT).show();
            }
        });
        gold_landscapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reached level 40 in landscapes category", Toast.LENGTH_SHORT).show();
            }
        });
        gold_comics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reached level 40 in comics category", Toast.LENGTH_SHORT).show();
            }
        });
        gold_logos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Reached level 40 in logos category", Toast.LENGTH_SHORT).show();
            }
        });
    }
}