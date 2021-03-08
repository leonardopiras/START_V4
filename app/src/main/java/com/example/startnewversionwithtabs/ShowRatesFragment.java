package com.example.startnewversionwithtabs;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowRatesFragment extends Fragment {
    Image image;
    AppSessione appSessione;
    boolean pictureFromGalleryActivity;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShowRatesFragment() {
        // Required empty public constructor
    }

    public ShowRatesFragment(Image image, AppSessione appSessione, boolean pictureFromGalleryActivity) {
        this.image = image;
        this.appSessione = appSessione;
        this.pictureFromGalleryActivity = pictureFromGalleryActivity;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowReviewsFragment newInstance(String param1, String param2) {
        ShowReviewsFragment fragment = new ShowReviewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        View view = inflater.inflate(R.layout.fragment_show_rates, container, false);
        LinearLayout linLayShowImageReviews = view.findViewById(R.id.linLay_showImageReviews);
        ListView listViewRates = view.findViewById(R.id.listViewRates);
        ImageButton buttonClose = view.findViewById(R.id.imageButtonClose);
        TextView view_nummberOfReviews = view.findViewById(R.id.textViewRatingImageNumberOfReviews);
        RatingBar view_totalRatingBar = view.findViewById(R.id.ratingBarTotal);

        switch(image.ratingArray.size()) {
            case 0:
                view_nummberOfReviews.setText("Not rated yet       ");
                view_nummberOfReviews.setTextColor(getResources().getColor(R.color.white));
                view_nummberOfReviews.setTextSize(35);
                view_totalRatingBar.setVisibility(View.GONE);
                break;
            case 1:
                view_nummberOfReviews.setText(String.valueOf(image.getCalculatedRating()) + " ★             " +
                        image.ratingArray.size() + " rate     ");
                view_totalRatingBar.setRating(image.getCalculatedRating());
                break;
            default:
                view_nummberOfReviews.setText(String.valueOf(image.getCalculatedRating()) + " ★             " +
                        image.ratingArray.size() + " rates     ");
                view_totalRatingBar.setRating(image.getCalculatedRating());

        }

        makeBlurBackground(view);
        AdapterRates adapterRates = new AdapterRates(requireContext(), R.layout.inside_comment_line, getCorrectRatingArray());
        listViewRates.setAdapter(adapterRates);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }


    ArrayList<Comment> getCorrectRatingArray() {
        ArrayList<Comment> arr = new ArrayList<>(image.ratingArray);
        boolean youAlreadyVoted = false;
        for(int i = 0; i < arr.size(); i++) {
            if (arr.get(i).commentOwner.equals(getAppSessione().user)) {
                Collections.swap(arr, 0, i);
                youAlreadyVoted = true;
            }
        }
        if (!youAlreadyVoted && !(image.owner.equals(getAppSessione().user))) {
            arr.add(new Comment(-1f));
            Collections.swap(arr, 0, arr.size()-1);
        }
        return arr;

    }



    protected void makeBlurBackground(View view) {
        float radius = 9f;
        BlurView blurView = view.findViewById(R.id.blurViewPictureFragment);
        View decorView = requireActivity().getWindow().getDecorView();
        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        //Set drawable to draw in the beginning of each blurred frame (Optional).
        //Can be used in case your layout has a lot of transparent space and your content
        //gets kinda lost after after blur is applied.
        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(getContext()))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true);
    }


    public AppSessione getAppSessione(){
        if (pictureFromGalleryActivity)
            return ((GalleryActivity)requireActivity()).appSessione;
        else
            return ((CommunityActivity)requireActivity()).appSessione;
    }

    public void setAppSessione(AppSessione appSessione){
        if (pictureFromGalleryActivity)
            ((GalleryActivity)requireActivity()).appSessione = appSessione;
        else
            ((CommunityActivity)requireActivity()).appSessione = appSessione;
    }


    public void reloadfrag() {
        List<Fragment> fragments = getParentFragmentManager().getFragments();
        for (Fragment frag : fragments) {
            // Tutti e due, cosi se cambi preferito in uno si aggiorna anche l'altro
            if (frag instanceof ShowRatesFragment) {
                getParentFragmentManager().beginTransaction().detach(frag).commit();
                getParentFragmentManager().beginTransaction().attach(frag).commit();
            }

        }
    }

}