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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowReviewsFragment extends Fragment {
    Image image;
    AppSessione appSessione;
    boolean pictureFromGalleryActivity;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameter
    private String mParam1;
    private String mParam2;

    public ShowReviewsFragment() {
        // Required empty public constructor
    }

    public ShowReviewsFragment(Image image, AppSessione appSessione, boolean pictureFromGalleryActivity) {
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
        View view = inflater.inflate(R.layout.fragment_show_reviews, container, false);
        LinearLayout linLayShowImageReviews = view.findViewById(R.id.linLay_showImageReviews);
        TextView textViewRatingImageValue = view.findViewById(R.id.textViewRatingImageValue);
        TextView textViewRatingImageNumberOfReviews = view.findViewById(R.id.textViewRatingImageNumberOfReviews);
        ExpandableListView listViewComments = view.findViewById(R.id.listViewComments);
        ImageButton buttonClose = view.findViewById(R.id.imageButtonClose);

        if (image.comments.size() != 1)
            textViewRatingImageValue.setText(image.comments.size() + " comments");
        else
            textViewRatingImageValue.setText(image.comments.size() + " comment");


        textViewRatingImageNumberOfReviews.setText(String.valueOf(image.getCalculatedRating()) + " ★");

        makeBlurBackground(view);
        ArrayList<Comment> im  = image.comments;
        LinkedHashMap<Comment, List<Comment>> hashMap = getHashMap();
        AdpterComments adapter = new AdpterComments(getContext(), hashMap);
        listViewComments.setAdapter(adapter);
        listViewComments.setSelector(android.R.color.transparent);

        listViewComments.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                long idView = v.getId();
                Comment commentObj = (Comment) parent.getItemAtPosition(groupPosition);

                if (idView == R.id.send_button && commentObj.commentOwner == null ) {
                    Comment newComm = new Comment(appSessione.user, commentObj.comment);
                    image.comments.add(newComm);

                    AppSessione appSessione = getAppSessione();
                    for (int i = 0; i < appSessione.images.size(); i++) {
                        if (appSessione.images.get(i).imageSource.equals(image.imageSource)) {
                            appSessione.images.set(i, image);
                            setAppSessione(appSessione);
                        }
                    }
                    ( (AdpterComments) listViewComments.getExpandableListAdapter()).notifica(getHashMap());


                    return true;
                }

                return false;
            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });








        listViewComments.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getContext(),
                        image.comments.get(groupPosition).comment + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        listViewComments.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getContext(),
                        image.comments.get(groupPosition).comment + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        listViewComments.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getContext(),
                        image.comments.get(groupPosition).comment
                                + " -> ", Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });


        // Inflate the layout for this fragment
        return view;
    }



    public LinkedHashMap<Comment, List<Comment>> getHashMap() {
        LinkedHashMap<Comment, List<Comment>> hashMap = new LinkedHashMap<>();
        AppSessione appSessione = getAppSessione();
        for (Comment com : image.comments) {
            if (com.innerComments.isEmpty() || !com.innerComments.get(com.innerComments.size()-1).comment.equals("Reply"))
                    com.innerComments.add(new Comment("Reply"));
            hashMap.put(com, com.innerComments);
        }
        hashMap.put(new Comment("Add a comment"), null);
        return hashMap;
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
            if (frag instanceof ShowReviewsFragment) {
                getParentFragmentManager().beginTransaction().detach(frag).commit();
                getParentFragmentManager().beginTransaction().attach(frag).commit();
            }

        }
    }

}