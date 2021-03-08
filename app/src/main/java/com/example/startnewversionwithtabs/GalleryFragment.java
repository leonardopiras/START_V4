package com.example.startnewversionwithtabs;

import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import androidx.fragment.app.FragmentTransaction;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.startnewversionwithtabs.Category.*;

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
        if (getArguments() != null) {// Al primo ingresso questo bundle è null
            weAreInCategoriesFragment = getArguments().getBoolean("Categories", true);
        }
        if (weAreInCategoriesFragment) {
            arrayElementi =   ( (GalleryActivity) requireActivity()).appSessione.gridItemsGallery;
        } else {
            arrayElementi = Image.imageToGalleryItemOffline(
                    ( (GalleryActivity) requireActivity()).appSessione.images,
                    ( (GalleryActivity) requireActivity()).appSessione.user, Category.valueOf(getArguments().getString("WhichCategory", CHARACTER_DESIGN.name())));
        }
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        AdapterCategoryGallery adapter1 = new AdapterCategoryGallery(requireActivity(),
                R.layout.gallery_item_category, arrayElementi);
        ExpandableHeightGridView gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridView_galleryCategory);
        gridView.setExpanded(true);
        gridView.setAdapter(adapter1);
        // Inflate the layout for this fragment

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridItem  gridItem = (GridItem) parent.getItemAtPosition(position);
                long idButton = view.getId();


                if (gridItem.isCategory) {
                    switch (gridItem.category) { // Attenzione!!!! sto considerando categoria e sotto categoria in un unico swich
                        // ogni immagine riconsciuta solo dal titolo
                        case ADD:
                            ((GalleryActivity) getActivity()).selectImage();
                            break;
                        default:
                            goToSubCat(gridItem);
                    }
                } else {
                    if (idButton == R.id.isPrefButtonGrid) {
                        changeIsPrefState(gridItem.name);
                        reloadfrag();
                    } else { // Non siamo in categorie e NON si è premuto il button dei preferiti
                        getParentFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.frag_container_gallery, new PictureFragment(getImageFromGridView(gridItem), true))
                                .addToBackStack("FragmentStack")
                                .commit();
                    }
                }

            }
        });
        return view;
    }

    protected void goToSubCat(GridItem gridItem) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("Categories", false);
        bundle.putString("WhichCategory", gridItem.category.name());
        GalleryFragment.this.setArguments(bundle);
        List<Fragment> fragments = getParentFragmentManager().getFragments();
        for (Fragment frag : fragments) {
            // Tutti e due, cosi se cambi preferito in uno si aggiorna anche l'altro
            if (frag instanceof GalleryFragment) {
                getParentFragmentManager().beginTransaction().detach(frag).commit();
                getParentFragmentManager().beginTransaction().attach(frag).commit();
            }
        }
    }


    Image getImageFromGridView(GridItem gridItem) {
        for (int i = 0; i < (( (GalleryActivity) requireActivity()).appSessione.images).size(); i++) {
            if ( (( (GalleryActivity) requireActivity()).appSessione.images).get(i).name.equals(gridItem.name)) {
                return (( (GalleryActivity) requireActivity()).appSessione.images).get(i);
            }
        }
        return null;
    }

    void changeIsPrefState(String itemName) {
        for (int i = 0; i < (( (GalleryActivity) requireActivity()).appSessione.images).size(); i++) {
            if ( (( (GalleryActivity) requireActivity()).appSessione.images).get(i).name.equals(itemName)) {
                (( (GalleryActivity) requireActivity()).appSessione.images).get(i).isPref = !( (( (GalleryActivity) requireActivity()).appSessione.images).get(i).isPref);
            }
        }

    }




    public void reloadfrag() {
        List<Fragment> fragments = getParentFragmentManager().getFragments();
        for (Fragment frag : fragments) {
            // Tutti e due, cosi se cambi preferito in uno si aggiorna anche l'altro
            if (frag instanceof GalleryFragment || frag instanceof FavoritesFragment) {
                getParentFragmentManager().beginTransaction().detach(frag).commit();
                getParentFragmentManager().beginTransaction().attach(frag).commit();
            }

        }
    }


}