package com.example.startnewversionwithtabs;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewPathFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPathFragment extends Fragment {

    ImageButton landscapes, charachterDesign, comics;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewPathFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewPathFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPathFragment newInstance(String param1, String param2) {
        NewPathFragment fragment = new NewPathFragment();
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

        View view =  inflater.inflate(R.layout.fragment_new_path, container, false);

        ArrayList<PathSingleElement> itemsNewPath = generatePathSingleElementArray_NewPath();

        PathSingleElementAdapter adapter = new PathSingleElementAdapter(
                requireActivity(), R.layout.my_paths_list_view, itemsNewPath
        );

        ListView listView = view.findViewById(R.id.list_view_newPath);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PathSingleElement pathSingleElement = (PathSingleElement) parent.getItemAtPosition(position);
                int idButton = (int) view.getId();

                switch (idButton) {
                    case R.id.isPref:
                        if (pathSingleElement.isPref) {
                            ( (StartTrainingActivity) requireActivity()).appSessione.pathItem
                                    .get(pathSingleElement.index).isPref = false;
                            reloadfrag();
                            Toast.makeText(getContext(), pathSingleElement.title + " REMOVED from favourites", Toast.LENGTH_SHORT).show();


                        } else {
                            ( (StartTrainingActivity) requireActivity()).appSessione.pathItem
                                    .get(pathSingleElement.index).isPref = true;
                            reloadfrag();
                            Toast.makeText(getContext(), pathSingleElement.title + " ADDED to favourites", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.expand:
                        if (pathSingleElement.isFocused_newpath) {
                            ( (StartTrainingActivity) requireActivity()).appSessione.pathItem
                                    .get(pathSingleElement.index).isFocused_newpath = false;
                        } else {
                            ( (StartTrainingActivity) requireActivity()).appSessione.pathItem
                                    .get(pathSingleElement.index).isFocused_newpath = true;
                        }
                        reloadfrag();
                        break;
                    default:
                        if (pathSingleElement.index == 1) {
                            Intent showResult = new Intent(getActivity(), CharachterDesignActivity.class);
                            showResult.putExtra("AppSessione", ( ( (StartTrainingActivity) requireActivity()).appSessione));
                            startActivity(showResult);
                        }


                }


            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    public ArrayList<PathSingleElement> generatePathSingleElementArray_NewPath() {
        ArrayList<PathSingleElement> arrayPronto = new ArrayList<>();
        ArrayList<Integer> focusedItemID = new ArrayList<>();
        PathSingleElement precedente;

        ArrayList<PathSingleElement> rawArr = ( (StartTrainingActivity) requireActivity()).appSessione.pathItem;
        for (PathSingleElement singleElement : rawArr) {
            if (singleElement.isFocused_newpath) {
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
        return arrayPronto;
    }
}