package com.example.startnewversionwithtabs;


import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

public class PathSingleElementAdapter extends ArrayAdapter<PathSingleElement> {


    private int layout;
    Drawable isPrefDrawable;
    MyPathViewHolder myPathViewHolder = null;
    PathSingleElement pathSingleElement;


    public PathSingleElementAdapter(@NonNull Context context, int resource,
                                    @NonNull ArrayList<PathSingleElement> objects) {
        super(context, resource, objects);
        layout = resource; // Int che rappresenta il layout xml della singola riga
    }


    /*public PathSingleElementAdapter(Context context, ArrayList<PathSingleElement> users) {
        super(context, 0, users);
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        pathSingleElement = getItem(position);
        Resources res = getContext().getResources();


        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layout, parent,
                    false);
            myPathViewHolder = new MyPathViewHolder();
            myPathViewHolder.title = (TextView) convertView.findViewById(R.id.pathTitle);
            myPathViewHolder.constraintLayout = (ConstraintLayout) convertView.findViewById(R.id.backgroudPathRowSingleElement);
            myPathViewHolder.isPrefButton = (ImageButton) convertView.findViewById(R.id.isPref);
            myPathViewHolder.expand = (ImageView) convertView.findViewById((R.id.expand));
            convertView.setTag(myPathViewHolder);
            View finalConvertView = convertView;  // Problemi di java classi interne

            ConstraintLayout.LayoutParams parametriMargini = new ConstraintLayout.LayoutParams(convertView.getLayoutParams());

            //Setto le varie robe
            if(pathSingleElement.isSubCategory) {
                //convertView.setLayoutParams(new ViewGroup.LayoutParams(300, 100));
                parametriMargini.width = 800;
                parametriMargini.height = 130;
                convertView.setLayoutParams(parametriMargini);
                myPathViewHolder.expand.setVisibility(View.GONE);
            } else {
                //parametriMargini.topMargin = 1000;
                convertView.setLayoutParams(parametriMargini);
            }


            myPathViewHolder.title.setText(pathSingleElement.getTitle());

            myPathViewHolder.constraintLayout.setBackground(ResourcesCompat.getDrawable(
                    getContext().getResources(), pathSingleElement.backGroundImageString, null));

            if(pathSingleElement.isPref)
                isPrefDrawable = ResourcesCompat.getDrawable(res, R.drawable.star_icon, null);
            else
                isPrefDrawable = ResourcesCompat.getDrawable(res, R.drawable.empty_star_icon, null);
            myPathViewHolder.isPrefButton.setBackground(isPrefDrawable);

            myPathViewHolder.isPrefButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()

                }
            });

            myPathViewHolder.expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
                }
            });
        } else {
            myPathViewHolder = (MyPathViewHolder) convertView.getTag(); // Recupero view
        }

        return convertView;
    }





}

class MyPathViewHolder { // classe per gestire tutte le view all'interno della riga
    TextView title;
    ConstraintLayout constraintLayout;
    ImageButton isPrefButton;
    ImageView expand;
}