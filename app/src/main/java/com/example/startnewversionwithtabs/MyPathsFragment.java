package com.example.startnewversionwithtabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.preference.PreferenceManager;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

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


        arrayOfElements = generatePathSingleElementArray_MyPath();

        PathSingleElementAdapter adapter1 = new PathSingleElementAdapter(
                requireActivity(),
                R.layout.my_paths_list_view, arrayOfElements);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PathSingleElement pathSingleElement = (PathSingleElement) parent.getItemAtPosition(position);
                int idButton = (int) view.getId();

                switch (idButton) {
                    case R.id.isPref:
                        /* Se viene triggerato questo button significa che si è appena accettato di voler
                        rimuovere un elemento dai preferiti: bisogna poi riaggiornare il fragment
                        */
                        if (pathSingleElement.isPref) {
                            dialogConferma(getResources(), pathSingleElement);

                        } else {
                            ( (StartTrainingActivity) requireActivity()).appSessione.pathItem
                                    .get(pathSingleElement.index).isPref = true;
                            reloadfrag();

                        }

                        //myPathViewHolder = (MyPathViewHolder) finalConvertView.getTag();

                        break;
                    case R.id.expand:
                        if (pathSingleElement.isFocused_mypath) {
                            ( (StartTrainingActivity) requireActivity()).appSessione.pathItem
                                    .get(pathSingleElement.index).isFocused_mypath = false;
                        } else {
                            ( (StartTrainingActivity) requireActivity()).appSessione.pathItem
                                    .get(pathSingleElement.index).isFocused_mypath = true;
                        }
                        reloadfrag();


                        break;

                    default:





                }
            }
        });

        //adapter1.add(newElement); // Funzione per aggiungere elementi dopo la creazione dell'adapter

        return view;
    }

    public void dialogConferma(Resources res, PathSingleElement pathSingleElement) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setTitle("Warning")
                .setMessage("Are you sure you want to REMOVE " + pathSingleElement.title +" from favourites?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Questo è quello locale: praticamente serve a poco, una volta che il fragment
                        // si riaggiorna viene rinizializzato, perdendo i dati
                        pathSingleElement.isPref = false;


                        // Questo è quello che rimane in tutta l'activity
                        ( (StartTrainingActivity) requireActivity()).appSessione.pathItem
                                .get(pathSingleElement.index).isPref = false;
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





    public void reloadfrag() {
        List<Fragment> fragments = getParentFragmentManager().getFragments();
        for (Fragment frag : fragments) {
            if (frag instanceof MyPathsFragment || frag instanceof  NewPathFragment) {
                getParentFragmentManager().beginTransaction().detach(frag).commit();
                getParentFragmentManager().beginTransaction().attach(frag).commit();
            }
        }
    }

    public ArrayList<PathSingleElement> generatePathSingleElementArray_MyPath() {
        ArrayList<PathSingleElement> arrayPronto = new ArrayList<>();
        ArrayList<Integer> focusedItemID = new ArrayList<>();
        ArrayList<PathSingleElement> rawArr = recuperaArrayPathSingleElement_fromFragment();
        for (PathSingleElement singleElement : rawArr) {
            if (singleElement.isPref || singleElement.isSubCategory) {
                if (singleElement.isFocused_mypath) {
                    focusedItemID.add(singleElement.index);
                }
                if (singleElement.isSubCategory) {

                    if (focusedItemID.contains(singleElement.parentIndex)) {
                        arrayPronto.add(singleElement);
                    }
                } else {
                    arrayPronto.add(singleElement);
                }
            }
        }
        return arrayPronto;
    }


    public ArrayList<PathSingleElement> recuperaArrayPathSingleElement_fromFragment() {
        /*
        SharedPreferences  mPrefs = getActivity().getSharedPreferences("PROVA", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("AppSessione", "");
        AppSessione obj = gson.fromJson(json, AppSessione.class); */

        return ( (StartTrainingActivity) requireActivity()).appSessione.pathItem;

    }
}