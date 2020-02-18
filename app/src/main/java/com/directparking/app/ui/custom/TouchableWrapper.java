package com.directparking.app.ui.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

class TouchableWrapper extends FrameLayout {

    public TouchableWrapper(@NonNull Context context) {
        super(context);
    }

    public TouchableWrapper(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchableWrapper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                mMapIsTouched = true;
                break;
            case MotionEvent.ACTION_UP:
//                mMapIsTouched = false;
                break;
        }

        return super.dispatchTouchEvent(ev);

    }

}