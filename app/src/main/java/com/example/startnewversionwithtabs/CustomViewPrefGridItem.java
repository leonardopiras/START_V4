package com.example.startnewversionwithtabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;




// Attenzioneeeeeeeeeee:   questa view non serve più nell'app. Abbiamo messo un immagine manuale

public class CustomViewPrefGridItem extends View {

    Rect rect;
    Paint paint;
    Rect rectSquare;
    Path path;

    Rect last;


    Point a, b ,c;

    public CustomViewPrefGridItem(Context context) {
        super(context);
        init(null);
    }

    public CustomViewPrefGridItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomViewPrefGridItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomViewPrefGridItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @SuppressLint("ResourceType")
    private void init(@Nullable AttributeSet set) {
        rect = new Rect();
        rectSquare = new Rect();
        last = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(50,0,0,0);
        path = new Path();
        a = new Point();
        b = new Point();
        c = new Point();
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        rect.left = 0;
        rect.top = 0;
        rect.right = getMeasuredWidth();
        rect.bottom = rect.top + (getMeasuredHeight() / 9);


        rectSquare.top = 0;
        rectSquare.left =  getMeasuredWidth() - (getMeasuredWidth() / 4);
        rectSquare.right = getMeasuredWidth();
        rectSquare.bottom = rect.top + (getMeasuredHeight() / 5);

/*
        last.bottom = rectSquare.bottom;
        last.right = rectSquare.left;
        last.top = rectSquare.top;
        last.left = rectSquare.left - 200;
        */

        Path path1 = new Path();

        path1.lineTo(0,0);
        path1.lineTo(getMeasuredWidth(), 0);
        path1.lineTo(getMeasuredWidth(), (getMeasuredHeight() / 5.0f));
        path1.lineTo(getMeasuredWidth() - (getMeasuredWidth() / 4.0f), (getMeasuredHeight() / 5.0f));
        path1.lineTo((getMeasuredWidth() - (getMeasuredWidth() / 4f)) -(getMeasuredWidth() / 3f),
                (getMeasuredHeight() / 9f) );
        path1.lineTo(0, (getMeasuredHeight() / 9f));
        path1.lineTo(0,0);
        path1.close();

        a.set(rectSquare.left ,rectSquare.top);
        b.set(rectSquare.left, rectSquare.bottom);
        c.set((rectSquare.left - (getMeasuredWidth() / 3)) , rectSquare.top);

        path.lineTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(a.x, a.y);
        path.close();



        canvas.drawRect(rect, paint);
        canvas.drawRect(rectSquare, paint);
        canvas.drawPath(path1, paint);



    }
}