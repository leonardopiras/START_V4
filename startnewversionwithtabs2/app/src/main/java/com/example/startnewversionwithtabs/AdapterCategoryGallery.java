package com.example.startnewversionwithtabs;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class AdapterCategoryGallery extends ArrayAdapter<GridItem> {
    private int layout;
    GridItem itemGalleryCategory;
    GalleryItemViewHolder galleryItemViewHolder = null;

    public AdapterCategoryGallery(@NonNull Context context, int resource, @NonNull ArrayList<GridItem> objects) {
        super(context, resource, objects);
        layout = resource; // Int che rappresenta il layout xml della singola riga
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        itemGalleryCategory = getItem(position);
        Resources res = getContext().getResources();

        if (convertView == null) {
            convertView = LayoutInflater.from((getContext())).inflate(layout, parent, false);
            galleryItemViewHolder = new GalleryItemViewHolder();
            galleryItemViewHolder.title  = (TextView) convertView.findViewById(R.id.textView_galleryitem_category);
            galleryItemViewHolder.constraintLayout = (ConstraintLayout) convertView.findViewById(R.id.constraintLayout_galleryitem_category);
            convertView.setTag(galleryItemViewHolder);

            View finalConvertView = convertView; // Problemi di java con classi interne

            galleryItemViewHolder.title.setText(itemGalleryCategory.name);
            galleryItemViewHolder.constraintLayout.setBackground(itemGalleryCategory.background);
        }
        return convertView;
    }
}

class GalleryItemViewHolder {
    TextView title;
    ConstraintLayout constraintLayout;
}