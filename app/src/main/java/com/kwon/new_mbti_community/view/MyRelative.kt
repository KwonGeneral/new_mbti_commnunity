package com.kwon.new_mbti_community.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Parcel
import android.os.Parcelable
import android.view.MotionEvent
import android.widget.RelativeLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatCheckBox
import android.widget.TextView

import android.view.LayoutInflater

import android.os.Build
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText

import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.TypedArrayUtils.getText
import com.kwon.new_mbti_community.Contain.ColorStatus
import com.kwon.new_mbti_community.R
import kotlinx.android.synthetic.main.activity_login.view.*


class MyRelative : RelativeLayout {
    var mInflater: LayoutInflater
    var status: String? = ""

    constructor(context: Context?) : super(context) {
        mInflater = LayoutInflater.from(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs,defStyle) {
        mInflater = LayoutInflater.from(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mInflater = LayoutInflater.from(context)
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.MyRelative)
        status = typedArray?.getString(R.styleable.MyRelative_status)
        typedArray?.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("TEST", "status? : $status")
        when(status) {
            "id" -> {
                if(login_username_input.text.toString() == "test") {
                    login_username_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor(ColorStatus.TEXT_FAIL.color))
                }
            }
            "password" -> {
                if(login_password_input.text.toString() == "test") {
                    login_password_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor(ColorStatus.TEXT_FAIL.color))
                }
            }
        }
//        Log.d("TEST", "onMeasure 1??? : ${widthMeasureSpec}")
//        Log.d("TEST", "onMeasure 2??? : ${heightMeasureSpec}")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        Log.d("TEST", "onDraw??? : ${canvas}")
    }
}