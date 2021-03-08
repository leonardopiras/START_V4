package com.example.startnewversionwithtabs;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class AdapterRates extends ArrayAdapter<Comment> {

    public AdapterRates(@NonNull Context context, int resource, @NonNull List<Comment> objects) {
        super(context, resource, objects);
    }

    public AdapterRates(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from((getContext())).inflate(R.layout.inside_comment_line, parent, false);
        Comment comment = getItem(position);

        TextView commentContent = convertView.findViewById(R.id.commentContent);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBar);
        TextView ownerName = convertView.findViewById(R.id.ownerName);
        ConstraintLayout consLayEntireComment = convertView.findViewById(R.id.cons_lay_single_entire_comment);


        if (comment.rating < 0) { // siginifica che è l'item di aggiunta immagine
            ConstraintLayout consLayImgUsr = convertView.findViewById(R.id.cons_lay_username_img);

            consLayImgUsr.setVisibility(View.GONE);
            commentContent.setEnabled(true);
            commentContent.setTextSize(25);
            commentContent.setFocusable(true);
            commentContent.setFocusableInTouchMode(true);
            commentContent.setBackgroundTintList(AppCompatResources.getColorStateList(getContext(), R.color.transparentBlack));
            commentContent.setBackgroundResource(R.drawable.rounded);
            commentContent.setText("Rate this image");
            commentContent.setGravity(Gravity.CENTER);
            commentContent.setTextColor(getContext().getResources().getColor(R.color.white));
            commentContent.setPadding(0,10,0,10);
            commentContent.post(new Runnable() {
                @Override
                public void run() {
                    ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) commentContent.getLayoutParams();
                    lp.setMargins(30,30, 30, 30);

                    commentContent.setLayoutParams(lp);
                }
            });

        } else {
            commentContent.setVisibility(View.GONE);
            ratingBar.setVisibility(View.VISIBLE);
            ratingBar.setRating(comment.rating);
            ownerName.setText(comment.commentOwner.nome);
        }

        consLayEntireComment.post(new Runnable() {
            @Override
            public void run() {
                ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) consLayEntireComment.getLayoutParams();
                lp.setMarginStart(0);
                consLayEntireComment.setLayoutParams(lp);
            }
        });



        return  convertView;
    }
}
