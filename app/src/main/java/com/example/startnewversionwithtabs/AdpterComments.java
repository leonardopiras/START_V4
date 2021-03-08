package com.example.startnewversionwithtabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class AdpterComments extends BaseExpandableListAdapter {
    private Context context;
    private List<Comment> expandableListTitle;
    private LinkedHashMap<Comment, List<Comment>> expandableListDetail;

    public AdpterComments(Context context, LinkedHashMap<Comment, List<Comment>> expandableListDetail) {
        this.context = context;
        this.expandableListDetail = new LinkedHashMap<>(expandableListDetail);
        this.expandableListTitle = new ArrayList<Comment>(this.expandableListDetail.keySet());
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Comment comment = (Comment) getChild(listPosition, expandedListPosition);
        final Comment parentCommment = (Comment) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.inside_comment_line, null);
        }

        TextView commentOwnerName = convertView.findViewById(R.id.ownerName);
        TextView commentContent = convertView.findViewById(R.id.commentContent);
        ConstraintLayout consLayImgUsr = convertView.findViewById(R.id.cons_lay_username_img);

        if (comment.comment.equals("Reply")) {
            consLayImgUsr.setVisibility(View.GONE);
            commentContent.setTextSize(18);
            commentContent.setText("Reply");
        } else {
            consLayImgUsr.setVisibility(View.VISIBLE);
            commentContent.setTextSize(15);
            commentOwnerName.setText(comment.commentOwner.nome);
            commentContent.setText(comment.comment);
        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent1) {
        Comment comment = (Comment) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.single_comment_line, null);
        }




        TextView commentOwnerName = convertView.findViewById(R.id.owner_name_in_comment);
        CustomEditText commentContent = convertView.findViewById(R.id.commentContent);
        ConstraintLayout consLayImgUsr = convertView.findViewById(R.id.cons_lay_username_img);
        ConstraintLayout consLayEntireComment = convertView.findViewById(R.id.cons_lay_single_entire_comment);
        ImageView arrowExpand = convertView.findViewById(R.id.arrow_expand);
        ConstraintLayout consLayUserAndComment = convertView.findViewById(R.id.cons_lay_user_and_comment);





        if (comment.comment.equals("Add a comment")) {
            consLayImgUsr.setVisibility(View.GONE);
            commentContent.setEnabled(true);
            commentContent.setTextSize(25);
            commentContent.setFocusable(true);
            commentContent.setFocusableInTouchMode(true);
            commentContent.setBackgroundTintList(AppCompatResources.getColorStateList(context, R.color.transparentBlack));
            commentContent.setHint("Add a public comment");
            commentContent.setHintTextColor(AppCompatResources.getColorStateList(context, R.color.white));
            arrowExpand.setVisibility(View.GONE);



            //ratingBar.setVisibility(View.GONE);

            consLayUserAndComment.post(new Runnable() {
                @Override
                public void run() {
                    ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) consLayUserAndComment.getLayoutParams();
                    lp.setMargins(10,10, 10, 10);
                    consLayUserAndComment.setLayoutParams(lp);
                }
            });
            View finalConvertView = convertView;
            commentContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        commentContent.setHintTextColor(AppCompatResources.getColorStateList(context, R.color.gray));
                        android.widget.Button sendButton = finalConvertView.findViewById(R.id.send_button);
                        sendButton.setVisibility(View.VISIBLE);
                        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) commentContent.getLayoutParams();
                        lp.setMarginEnd(110);
                        commentContent.setLayoutParams(lp);
                        commentContent.addTextChangedListener(new TextWatcher() {
                            boolean firstTime = true;
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if (firstTime) {
                                    firstTime = false;
                                    sendButton.setBackgroundResource(R.drawable.ic_baseline_send_filled);
                                    sendButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            CustomEditText prova = commentContent;
                                            String cont = prova.getText().toString();
                                            expandableListTitle.get(listPosition).comment = cont;
                                            ((ExpandableListView) parent1).performItemClick(v, listPosition, 0); // Let the event be handled in onItemClick()
                                            notifyDataSetChanged();

                                        }
                                    });
                                }
                            }
                            @Override
                            public void afterTextChanged(Editable s) {
                            }
                        });

                    } else {
                        commentContent.setHintTextColor(AppCompatResources.getColorStateList(context, R.color.white));
                        android.widget.Button sendButton = finalConvertView.findViewById(R.id.send_button);
                        sendButton.setVisibility(View.GONE);
                        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) commentContent.getLayoutParams();
                        lp.setMarginEnd(0);
                        commentContent.setLayoutParams(lp);

                    }
                }
            });


        } else {
            consLayImgUsr.setVisibility(View.VISIBLE);
            arrowExpand.setVisibility(View.VISIBLE);
            commentOwnerName.setText(comment.commentOwner.nome);
            //ratingBar.setRating(comment.rating);
            commentContent.setText(comment.comment);
            commentContent.setFocusable(false);
            commentContent.setTextSize(15);
            commentContent.setFocusableInTouchMode(false);
            commentContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ExpandableListView) parent1).performItemClick(v, listPosition, 0); // Let the event be handled in onItemClick()
                    return;
                }
            });
        }


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    public void notifica(LinkedHashMap<Comment, List<Comment>> expandableListDetail) {
        this.expandableListDetail = expandableListDetail;
        this.expandableListTitle = new ArrayList<Comment>(expandableListDetail.keySet());
        notifyDataSetChanged();
    }

}

  /*
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.single_comment_line, null);
        Comment comment = getItem(position);
        TextView commentOwnerName = convertView.findViewById(R.id.owner_name_in_comment);
        TextView commentContent = convertView.findViewById(R.id.commentContent);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBarComment);

        commentOwnerName.setText(comment.commentOwner.nome);
        commentContent.setText(comment.comment);
        ratingBar.setRating(comment.rating);

        return convertView;
    }*/


/*public class CustomAdapter extends ArrayAdapter<ElementoLista>{

    public CustomAdapter(Context context, int textViewResourceId,
            List <ElementoLista> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
             .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listitem, null);
        TextView titolo = (TextView)convertView.findViewById(R.id.title);
        TextView descrizione = (TextView)convertView.findViewById(R.id.description);
        ElementoLista c = getItem(position);
        titolo.setText(c.getTitolo());
        descrizione.setText(c.getDescrizione());
        return convertView;
    }

}

Nell*/