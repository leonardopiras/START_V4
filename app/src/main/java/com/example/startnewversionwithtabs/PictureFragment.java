package com.example.startnewversionwithtabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.text.Editable;
import android.text.InputType;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.Objects;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PictureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PictureFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Image image;
    public boolean pictureFromGalleryActivity;



    ConstraintLayout consLay_usernameClickable;
    ImageView view_ownerPhoto;
    CustomEditText view_imageName;
    EditText view_imageDescription;
    TextView view_ownerName, view_ratingImgValue;
    ImageButton closeImageButton, modifyImageNameButton, modifyImageDescriptionButton;
    PhotoView view_pictureToShow;
    RatingBar ratingBar;
    android.widget.Button favoriteButton, shareCommunityButton, commentsButton, shareOutsideButton, rateButton;
    LinearLayout linLayShowImageRates;

    public PictureFragment(Image image, boolean pictureFromGalleryActivity) {
        this.image = image;
        this.pictureFromGalleryActivity = pictureFromGalleryActivity;
    }

    public PictureFragment(int contentLayoutId, Image image, boolean pictureFromGalleryActivity) {
        super(contentLayoutId);
        this.image = image;
        this.pictureFromGalleryActivity = pictureFromGalleryActivity;
    }

    public PictureFragment(int contentLayoutId, Image image) {
        super(contentLayoutId);
        this.image = image;
    }

    public PictureFragment() {
        super(R.layout.fragment_picture);
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PictureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PictureFragment newInstance(String param1, String param2) {
        PictureFragment fragment = new PictureFragment();
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
        View view = inflater.inflate(R.layout.fragment_picture, container, false);


        consLay_usernameClickable = view.findViewById(R.id.cons_lay_username_clickable);
        view_ownerPhoto = view.findViewById(R.id.user_pic_in_picture_fragment);
        view_ownerName = view.findViewById(R.id.owner_name_in_picture_fragment);
        closeImageButton = view.findViewById(R.id.imageButtonClose);
        view_imageName = view.findViewById(R.id.image_name_id);
        modifyImageNameButton = view.findViewById(R.id.modifyImageName);
        modifyImageDescriptionButton = view.findViewById(R.id.modifyImageDescription);
        view_imageDescription = view.findViewById(R.id.image_description);
        view_pictureToShow = view.findViewById(R.id.picture_to_show);
        view_ratingImgValue = view.findViewById(R.id.textViewRatingImageValue);
        linLayShowImageRates = view.findViewById(R.id.linLay_showImageRates);

        shareCommunityButton = view.findViewById(R.id.share_button);
        shareOutsideButton = view.findViewById(R.id.shareOutside);
        favoriteButton = view.findViewById(R.id.favorite_button);
        commentsButton = view.findViewById(R.id.commentsButton);



        if (image != null) {
            if (image.owner.nome.equals(getAppSessione().user.nome)) {
                // Se è un immagine dell'utente
                consLay_usernameClickable.setVisibility(View.GONE);

                imageNameHandler();

                imageDescriptionHandler();


                shareCommunityButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Condividi in community", Toast.LENGTH_SHORT).show();
                    }
                });



            } else {
                // Se è un immagine della community

                modifyImageNameButton.setVisibility(View.GONE);
                modifyImageDescriptionButton.setVisibility(View.GONE);


                removeShareCommunityButton(view);

                //view_ownerPhoto.setImageBitmap(BitmapFactory.decodeFile(image.owner.));
                consLay_usernameClickable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Esci a vedere il profilo di un utente", Toast.LENGTH_SHORT).show();
                    }
                });
                view_ownerName.setText(image.owner.nome);






            }

            /*Qui dove non c'è nessuna differenza tra immagine tuo o di altri */

            showCommentsHandler();
            showRatesHandler(view);


            boolean youCanVote = false;
                /*
                for (Category imageSingleCat : image.category) {
                    if (getAppSessione().user.getCategoryLevel(imageSingleCat) < 2) {
                        youCanVote = false;
                    }
                }*/

            if (youCanVote) {

            } else {

               /* BlurView blurView = view.findViewById(R.id.blurViewRatingBar);
                float radius = 1f;

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
                        .setHasFixedTransformationMatrix(true);*/
            }


            if (image.description.isEmpty())
                view_imageDescription.setText("No description");
            else
                view_imageDescription.setText(image.description);

            view_imageName.setEnabled(false);
            view_imageName.setText(image.name);
            view_pictureToShow.setImageBitmap(BitmapFactory.decodeFile(image.imageSource));
            shareOutsideButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareOutside(image);
                }
            });

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "gestisci preferiti", Toast.LENGTH_SHORT).show();
                }
            });

           /* commentsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "gestisci Commenti", Toast.LENGTH_SHORT).show();
                }
            });*/

        }

        /*Qui a prescindere che immagine venga caricata correttamente o meno*/

        makeBlurBackground(view);

        closeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().remove(PictureFragment.this).commit();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    protected void showRatesHandler(View view) {
        TextView viewAvgRates = view.findViewById(R.id.textViewAvgImgRates);
        TextView viewNumberOfRates = view.findViewById(R.id.textViewRatingImageNumberOfRates);


        switch (image.ratingArray.size()) {
            case 0:
                viewAvgRates.setPadding(0,30,0,0);
                viewAvgRates.setText("UNRATED");
                viewAvgRates.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);

                viewNumberOfRates.setVisibility(View.INVISIBLE);
                break;
            case 1:
                viewAvgRates.setText(String.valueOf(image.getCalculatedRating()) + " ★");
                viewNumberOfRates.setText("1 rate");
                break;
            default:
                viewAvgRates.setText(String.valueOf(image.getCalculatedRating()) + " ★");
                viewNumberOfRates.setText(image.ratingArray.size() + " rates");
        }

        linLayShowImageRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionReviews transitionReviews = new TransitionReviews();
                PictureFragment.this.setExitTransition(transitionReviews);

                ShowRatesFragment showReviewsFragment = new ShowRatesFragment(image, getAppSessione(), pictureFromGalleryActivity);


                showReviewsFragment.setSharedElementEnterTransition(transitionReviews);
                showReviewsFragment.setEnterTransition(transitionReviews);

                PictureFragment.this.setSharedElementReturnTransition(transitionReviews);
                PictureFragment.this.setExitTransition(TransitionInflater.from(
                        getActivity()).inflateTransition(android.R.transition.fade));

                showReviewsFragment.setSharedElementEnterTransition(transitionReviews);
                showReviewsFragment.setEnterTransition(TransitionInflater.from(
                        getActivity()).inflateTransition(android.R.transition.fade));


                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(false).replace(R.id.frag_container_gallery, showReviewsFragment)
                        .addSharedElement(linLayShowImageRates, "transitionRates")
                        .addToBackStack("FragmentStack")
                        .commit();
            }
        });
    }


    protected void showCommentsHandler() {
        switch (image.comments.size()) {
            case 0:
                commentsButton.setText("Comments (0)");
                break;
            case 1:
                commentsButton.setText(" Comment (1)");
                break;
            default:
                commentsButton.setText(" Comments (" + image.comments.size() + ") ");
        }

        commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionReviews transitionReviews = new TransitionReviews();
                PictureFragment.this.setExitTransition(transitionReviews);

                ShowReviewsFragment showReviewsFragment = new ShowReviewsFragment(image, getAppSessione(), pictureFromGalleryActivity);


                showReviewsFragment.setSharedElementEnterTransition(transitionReviews);
                showReviewsFragment.setEnterTransition(transitionReviews);

                PictureFragment.this.setSharedElementReturnTransition(transitionReviews);
                PictureFragment.this.setExitTransition(TransitionInflater.from(
                        getActivity()).inflateTransition(android.R.transition.fade));

                showReviewsFragment.setSharedElementEnterTransition(transitionReviews);
                showReviewsFragment.setEnterTransition(TransitionInflater.from(
                        getActivity()).inflateTransition(android.R.transition.fade));


                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(false).replace(R.id.frag_container_gallery, showReviewsFragment)
                        .addSharedElement(commentsButton, "transitionReviews")
                        .addToBackStack("FragmentStack")
                        .commit();
            }
        });
    }

    protected void makeBlurBackground(View view) {
        float radius = 5f;
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

    protected void removeShareCommunityButton(View view) {
        shareCommunityButton.setVisibility(View.GONE);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) shareOutsideButton.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        shareOutsideButton.setLayoutParams(layoutParams);
        // Get the constraint layout of the parent constraint view.
        ConstraintLayout mConstraintLayout = view.findViewById(R.id.share_and_favorite_buttons_bar);
        // Define a constraint set that will be used to modify the constraint layout parameters of the child.
        ConstraintSet mConstraintSet = new ConstraintSet();
        // Start with a copy the original constraints.
        mConstraintSet.clone(mConstraintLayout);
        // Define new constraints for the child (or multiple children as the case may be).
        mConstraintSet.constrainPercentWidth(R.id.shareOutside, 1f);
        // Apply the constraints for the child view to the parent layout.
        mConstraintSet.applyTo(mConstraintLayout);
    }

    protected void imageDescriptionHandler() {
        modifyImageDescriptionButton.post(new Runnable() {
            @Override
            public void run() {
                ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) modifyImageDescriptionButton.getLayoutParams();
                lp.width = modifyImageDescriptionButton.getHeight();
                modifyImageDescriptionButton.setLayoutParams(lp);
            }
        });
        modifyImageDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_imageDescription.setEnabled(true);
                view_imageDescription.requestFocus();
                view_imageDescription.setCursorVisible(true);
                if (view_imageDescription.getText().toString().equals("No description"))
                    view_imageDescription.setText("");

                view_imageDescription.setSelection(view_imageDescription.getText().toString().length());
                view_imageDescription.setBackgroundTintList(AppCompatResources.getColorStateList(getContext(), R.color.translucentBlack));

                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view_imageDescription, InputMethodManager.SHOW_IMPLICIT);



                view_imageDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            view_imageDescription.setEnabled(false);
                            image.description = view_imageDescription.getText().toString();

                            AppSessione appSessione = getAppSessione();
                            for (int i = 0; i < appSessione.images.size(); i++) {
                                if (appSessione.images.get(i).imageSource.equals(image.imageSource)) {
                                    appSessione.images.set(i, image);
                                    setAppSessione(appSessione);
                                }
                            }
                        }
                    }
                });

            }
        });
    }

    protected void imageNameHandler() {
        modifyImageNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_imageName.setInputType(InputType.TYPE_CLASS_TEXT);
                view_imageName.setEnabled(true);
                view_imageName.requestFocus();
                view_imageName.setCursorVisible(true);
                view_imageName.setSelection(view_imageName.getText().length());
                view_imageName.setBackgroundTintList(AppCompatResources.getColorStateList(getContext(), R.color.translucentBlack));


                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view_imageName, InputMethodManager.SHOW_IMPLICIT);

                view_imageName.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        return false;
                    }
                });

                view_imageName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            view_imageName.setEnabled(false);
                            image.name = view_imageName.getText().toString();

                            AppSessione appSessione = getAppSessione();
                            for (int i = 0; i < appSessione.images.size(); i++) {
                                if (appSessione.images.get(i).imageSource.equals(image.imageSource)) {
                                    appSessione.images.set(i, image);
                                    setAppSessione(appSessione);
                                }
                            }
                        }
                    }
                });

            }
        });
    }


    public void shareOutside(Image image1) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), image1.imageSource);
        File f = new File(requireActivity().getExternalCacheDir() + "/" + image1.name + ".png");
        Intent shareIntent;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            bitmapDrawable.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        startActivity(Intent.createChooser(shareIntent, "share image"));
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

}