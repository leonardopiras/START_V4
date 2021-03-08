package com.example.startnewversionwithtabs;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        ArrayList<GridItem> arrayElementi = Image.imageToGalleryItemGalleryFavorites(
                ( (GalleryActivity) requireActivity()).appSessione.images,
                ( (GalleryActivity) requireActivity()).appSessione.user);

        AdapterCategoryGallery adapter1 = new AdapterCategoryGallery(requireActivity(),
                R.layout.gallery_item_category, arrayElementi);
        ExpandableHeightGridView gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridView_favorites);
        gridView.setExpanded(true);
        gridView.setAdapter(adapter1);
        // Inflate the layout for this fragment

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridItem  gridItem = (GridItem) parent.getItemAtPosition(position);
                long idButton = view.getId();
                if (idButton == R.id.isPrefButtonGrid) {
                    dialogConferma(getResources(), gridItem);
                } else {

                    shareOutside(gridItem);
                    switch (gridItem.name) { // Attenzione!!!! sto considerando categoria e sotto categoria in un unico swich
                        // ogni immagine riconsciuta solo dal titolo
                        case "Add":
                            ((GalleryActivity) requireActivity()).selectImage();
                            break;


                    }
                }
            }
        });


        return view;
    }


    public void shareOutside(GridItem gridItem){
        for (int i = 0; i < (( (GalleryActivity) requireActivity()).appSessione.images).size(); i++) {
            if ( (( (GalleryActivity) requireActivity()).appSessione.images).get(i).name.equals(gridItem.name)) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());


                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),gridItem.background);
                File f = new File(requireActivity().getExternalCacheDir() + "/" + gridItem.name + ".png");
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
        }

    }


    public void dialogConferma(Resources res, GridItem gridItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme);
        builder.setTitle("Warning")
                .setMessage("Are you sure you want to REMOVE \"" + gridItem.name +"\" from favorites?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeIsPrefState(gridItem.name);
                        reloadfrag();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();

    }

    void changeIsPrefState(String itemName) {
        for (int i = 0; i < (( (GalleryActivity) requireActivity()).appSessione.images).size(); i++) {
            if ( (( (GalleryActivity) requireActivity()).appSessione.images).get(i).name.equals(itemName)) {
                (( (GalleryActivity) requireActivity()).appSessione.images).get(i).isPref = !( (( (GalleryActivity) requireActivity()).appSessione.images).get(i).isPref);
            }
        }

    }

    public void reloadfrag() {
        List<Fragment> fragments = (List<Fragment>) getParentFragmentManager().getFragments();

        for (Fragment frag : fragments) {
            if (frag instanceof GalleryFragment || frag instanceof  FavoritesFragment) {
                getParentFragmentManager().beginTransaction().detach(frag).commit();
                getParentFragmentManager().beginTransaction().attach(frag).commit();
            }
        }
    }
}