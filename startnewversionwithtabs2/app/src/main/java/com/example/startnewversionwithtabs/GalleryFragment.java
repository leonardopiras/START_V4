package com.example.startnewversionwithtabs;

import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
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

        ArrayList<GridItem> arrayElementi;
        boolean weAreInCategoriesFragment = true;
        if (getArguments() != null) // Al primo ingresso questo bundle è null
            weAreInCategoriesFragment = getArguments().getBoolean("Categories", true);
        if (weAreInCategoriesFragment) {
            arrayElementi = generaArrayItemGallery();
            } else {
            arrayElementi = generaArrayItemCharacterDesign();
        }

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        AdapterCategoryGallery adapter1 = new AdapterCategoryGallery(Objects.requireNonNull(getActivity()),
                R.layout.gallery_item_category, arrayElementi);
        ExpandableHeightGridView gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridView_galleryCategory);
        gridView.setExpanded(true);
        gridView.setAdapter(adapter1);
        // Inflate the layout for this fragment

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridItem  gridItem = (GridItem) parent.getItemAtPosition(position);

                switch (gridItem.name) {
                    case "Add":
                        ((GalleryActivity) getActivity()).selectImage();
                        break;
                    case "Character design":
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("Categories", false);
                        GalleryFragment.this.setArguments(bundle);
                        reloadfrag();
                        break;

                }
            }
        });

        return view;
    }

    public void reloadfrag() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }


    public ArrayList<GridItem> generaArrayItemCharacterDesign() {
        ArrayList<GridItem> arrayList = new ArrayList<>();
        arrayList.add(new GridItem("", ResourcesCompat.getDrawable(getResources(), R.drawable.charachter_design_background, null)));
        arrayList.add(new GridItem("", ResourcesCompat.getDrawable(getResources(), R.drawable.landscapes_background, null)));
        arrayList.add(new GridItem("", ResourcesCompat.getDrawable(getResources(), R.drawable.comics_background, null)));
        arrayList.add(new GridItem("", ResourcesCompat.getDrawable(getResources(), R.drawable.logo, null)));
        arrayList.add(new GridItem("", ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_add_24, null)));
        return arrayList;
    }
    public ArrayList<GridItem> generaArrayItemGallery() {
        ArrayList<GridItem> arrayList = new ArrayList<>();
        arrayList.add(new GridItem("Character design", ResourcesCompat.getDrawable(getResources(), R.drawable.charachter_design_background, null)));
        arrayList.add(new GridItem("Landscapes", ResourcesCompat.getDrawable(getResources(), R.drawable.landscapes_background, null)));
        arrayList.add(new GridItem("Comics", ResourcesCompat.getDrawable(getResources(), R.drawable.comics_background, null)));
        arrayList.add(new GridItem("Logos", ResourcesCompat.getDrawable(getResources(), R.drawable.logo, null)));
        arrayList.add(new GridItem("Add", ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_add_24, null)));
        return arrayList;
    }
}