package com.example.startnewversionwithtabs;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;

@RequiresApi(api = Build.VERSION_CODES.M)
public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {



    public CustomEditText(@NonNull Context context) {
        super(context);
      init();
    }

    public CustomEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clearFocus();

            InputMethodManager mgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(getWindowToken(), 0);
            return true;
        }
        return false;
    }

    void init() {
        setBackgroundTintList(AppCompatResources.getColorStateList(getContext(), android.R.color.transparent));
        setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.header_style_doc_rounded, null));
        setPadding(20,10,20, 25);
        setHint("    Write here");
        setHintTextColor(getResources().getColor(R.color.gray));
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (!focused) {
            setBackgroundTintList(AppCompatResources.getColorStateList(getContext(), android.R.color.transparent));
            InputMethodManager mgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }


}
