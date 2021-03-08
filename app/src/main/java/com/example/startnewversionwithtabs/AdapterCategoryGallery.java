package com.example.startnewversionwithtabs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
            galleryItemViewHolder.imageView = convertView.findViewById(R.id.image);
            galleryItemViewHolder.isPrefImage = (ImageButton) convertView.findViewById(R.id.isPrefButtonGrid);
            convertView.setTag(galleryItemViewHolder);

            View finalConvertView = convertView; // Problemi di java con classi interne

            galleryItemViewHolder.title.setText(itemGalleryCategory.name);
            //galleryItemViewHolder.constraintLayout.setBackground(itemGalleryCategory.background);



            //galleryItemViewHolder.imageView.setImageDrawable(new BitmapDrawable(getContext().getResources(), itemGalleryCategory.background));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 7;
            galleryItemViewHolder.imageView.setImageBitmap(BitmapFactory.decodeFile(itemGalleryCategory.background, options));


            if (!itemGalleryCategory.isCategory) {
                galleryItemViewHolder.title.setBackground(ResourcesCompat.getDrawable(res, R.drawable.header_grid_item4, null));

                galleryItemViewHolder.isPrefImage.setMinimumWidth(convertView.getMeasuredWidth() / 2);
                galleryItemViewHolder.isPrefImage.setMinimumHeight(convertView.getMeasuredHeight() / 2);

                if (itemGalleryCategory.isPref) {
                    galleryItemViewHolder.isPrefImage.setBackground(ResourcesCompat.getDrawable(res, R.drawable.ic_baseline_star_24, null));
                } else {

                    galleryItemViewHolder.isPrefImage.setBackground(ResourcesCompat.getDrawable(res, R.drawable.ic_baseline_star_border_24, null));
                }

                galleryItemViewHolder.isPrefImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ExpandableHeightGridView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
                    }
                });

            } else {
                galleryItemViewHolder.title.setBackground(ResourcesCompat.getDrawable(res, R.drawable.header_grid_item_only_categories, null));
                galleryItemViewHolder.isPrefImage.setVisibility(View.GONE);
            }



        }
        return convertView;
    }
}

class GalleryItemViewHolder {
    TextView title;
    ImageView imageView;
    ConstraintLayout constraintLayout;
    ImageButton isPrefImage;
}