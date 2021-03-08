package com.example.startnewversionwithtabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

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
        return inflater.inflate(R.layout.fragment_new_path, container, false);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        landscapes = Objects.requireNonNull(getView()).findViewById(R.id.landscapes_button);
        charachterDesign = Objects.requireNonNull(getView()).findViewById(R.id.charachter_design_button);
        comics = Objects.requireNonNull(getView()).findViewById(R.id.comics_button);

        landscapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Not available yet", Toast.LENGTH_SHORT).show();
            }
        });

        charachterDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(getActivity(), CharachterDesignActivity.class);
                startActivity(showResult);
            }
        });

        comics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Not available yet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}