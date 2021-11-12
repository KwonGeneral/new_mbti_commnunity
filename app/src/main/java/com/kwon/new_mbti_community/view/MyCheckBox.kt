package com.kwon.new_mbti_community.view

import android.content.Context
import android.graphics.Canvas
import android.os.Parcel
import android.os.Parcelable
import android.view.MotionEvent
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatCheckBox

class MyCheckBox(context:Context):AppCompatCheckBox(context) {


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }



}