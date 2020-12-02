package com.dhl_myid.customedittext.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.dhl_myid.customedittext.R;

public class EditTextWithClear extends androidx.appcompat.widget.AppCompatEditText {

    private static final String TAG = "EditTextWithClear";
    Drawable clearButtonImage;

    public EditTextWithClear(@NonNull Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        clearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24, null);

        setOnTouchListener((v, event) -> {
            if ((getCompoundDrawablesRelative()[2] != null)) {
                float clearButtonStart;
                float clearButtonEnd;
                boolean isClearButtonClicked = false;

                //Detect LTR or RTL layout
                if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                    // If right to left, calculate the end of the button
                    clearButtonEnd = clearButtonImage.getIntrinsicWidth() + getPaddingStart();

                    // Handle click normally if touched elsewhere
                    if (event.getX() < clearButtonEnd) {
                        isClearButtonClicked = true;
                    }
                } else {

                    // Layout is left to right
                    clearButtonStart = (getWidth() - getPaddingEnd() - clearButtonImage.getIntrinsicWidth());

                    // Handle touch
                    if (event.getX() > clearButtonStart) {
                        isClearButtonClicked = true;
                    }
                }

                // Actions on button touch
                if (isClearButtonClicked) {
                    //Check for ACTION_DOWN
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        clearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_black_24, null);
                        showClearButton();
                    }

                    //Check ACTION_UP
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        clearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24, null);
                        getText().clear();
                        hideClearButton();
                        return true;
                    }
                } else {
                    return false;
                }


            }
            return false;
        });

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, clearButtonImage, null);
        Log.d(TAG, "showClearButton: clear button shown!");
    }

    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        Log.d(TAG, "hideClearButton: clear button hidden!");
    }
}
