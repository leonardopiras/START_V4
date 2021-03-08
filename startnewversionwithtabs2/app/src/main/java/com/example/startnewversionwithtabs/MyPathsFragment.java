package com.example.startnewversionwithtabs;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPathsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MyPathsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyPathsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPathsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPathsFragment newInstance(String param1, String param2) {
        MyPathsFragment fragment = new MyPathsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_paths, container, false);

        ArrayList<PathSingleElement> arrayOfElements;
        boolean firstTimeIn = true;
        if (getArguments() != null)
            firstTimeIn = getArguments().getBoolean("FirstTime", true);
        if (firstTimeIn)
            arrayOfElements = FirstTime_generatePathSingleElementArray_MyPath();
        else
            arrayOfElements = generatePathSingleElementArray_MyPath();

        PathSingleElementAdapter adapter1 = new PathSingleElementAdapter(
                Objects.requireNonNull(getActivity()),
                R.layout.my_paths_list_view, arrayOfElements);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PathSingleElement pathSingleElement = (PathSingleElement) parent.getItemAtPosition(position);
                long idButton = view.getId();
                if (idButton == R.id.isPref) {
                    /* Se viene triggerato questo button significa che si è appena accettato di voler
                    rimuovere un elemento dai preferiti: bisogna riaggiornare il fragment
                     */
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("FirstTime", false);
                    MyPathsFragment.this.setArguments(bundle);
                    reloadfrag();
                }
            }
        });

        //adapter1.add(newElement); // Funzione per aggiungere elementi dopo la creazione dell'adapter

        return view;
    }
    public void reloadfrag() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    public ArrayList<PathSingleElement> generatePathSingleElementArray_MyPath() {
        ArrayList<PathSingleElement> arrayOfElements = new ArrayList<>();
        arrayOfElements.add(new PathSingleElement("Charachter\nDesign", true, ResourcesCompat.getDrawable(getResources(), R.drawable.charachter_design_background, null)));
        //arrayOfElements.add(new PathSingleElement("Landscapes", true, ResourcesCompat.getDrawable(getResources(), R.drawable.landscapes_background, null)));
        return arrayOfElements;
    }



    public ArrayList<PathSingleElement> FirstTime_generatePathSingleElementArray_MyPath() {
        ArrayList<PathSingleElement> arrayOfElements = new ArrayList<>();
        arrayOfElements.add(new PathSingleElement("Charachter\nDesign", true, ResourcesCompat.getDrawable(getResources(), R.drawable.charachter_design_background, null)));
        arrayOfElements.add(new PathSingleElement("Landscapes", true, ResourcesCompat.getDrawable(getResources(), R.drawable.landscapes_background, null)));
        //arrayOfElements.add(new PathSingleElement("Comics", false, ResourcesCompat.getDrawable(getResources(), R.drawable.comics_background, null)));
        return arrayOfElements;
    }
}