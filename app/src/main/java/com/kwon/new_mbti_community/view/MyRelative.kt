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
import com.kwon.new_mbti_community.Contain.RegDefine.Companion.USERNAME_REG
import com.kwon.new_mbti_community.MbtiApplication
import com.kwon.new_mbti_community.R
import kotlinx.android.synthetic.main.activity_login.view.*


//class MyRelative : RelativeLayout {
//    var mInflater: LayoutInflater
//    var status: String? = ""
//
//    constructor(context: Context?) : super(context) {
//        mInflater = LayoutInflater.from(context)
//    }
//
//    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs,defStyle) {  // 여기서는 동작 안함.. 왜지? 이유가 뭘까..?
//        mInflater = LayoutInflater.from(context)
//    }
//
//    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { // 여기서 작업해야 status 값이 제대로 떨어짐.
//        mInflater = LayoutInflater.from(context)
//        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.MyRelative)
//        status = typedArray?.getString(R.styleable.MyRelative_status)
//        typedArray?.recycle() // recycle을 안적어도 문제가 없었다. 왜 사용하는걸까?
//        // C 언어에서 포인터를 지우는 개념과 비슷하다고 한다.
//        // 즉, 메모리 할당 / 해제에 관한 기능으로 보인다.
//        // 공식 사이트에서는 이와 같이 말한다. [ 나중에 호출자가 재사용할 수 있도록 TypedArray를 재활용합니다. ]
//        // 이에 대한 글을 찾아보니 아래와 같다.
//
//        // 호출의 목적은 Caching 때문인데, 내부적으로 TypedArray 는 Cache 를 위한 배열을 포함하고 있어 매번 할당하지 않기 위해 Static field로 Cache 된다.
//        // recycle을 호출하게되면 Cache로 사용한 객체를 반환해서 Garbage Collection의 대상에서 제외시키는 것으로 보인다.
//        // CustomView가 거의 재사용되지 않는다면 굳이 호출하지 않아 GC 대상에 포함시키는게 맞는 것 같다.
//    }
//
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        Log.d("TEST", "status? : $status")
//        when(status) {
//            "id" -> {
//                if(login_username_input.text.toString() == "test") {
//                    login_username_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor(ColorStatus.TEXT_FAIL.color))
//                }
//            }
//            "password" -> {
//                if(login_password_input.text.toString() == "test") {
//                    login_password_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor(ColorStatus.TEXT_FAIL.color))
//                }
//            }
//        }
////        Log.d("TEST", "onMeasure 1??? : ${widthMeasureSpec}")
////        Log.d("TEST", "onMeasure 2??? : ${heightMeasureSpec}")
//    }
//
//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//    }
//
//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
////        Log.d("TEST", "onDraw??? : ${canvas}")
//    }
//}

class MyRelative @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {
    var status: String? = ""
    var mbtiApplication: MbtiApplication? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRelative)
        status = typedArray.getString(R.styleable.MyRelative_status)
//        typedArray.recycle()
        // 위의 커스텀 뷰는 재활용이 될 가능성이 전무하다.
        // LoginActivity에서만 사용할 예정이기 때문에
        // recycle을 사용하지 않고, GC에 포함해서 처리하는 게 맞는 것으로 보인다.
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // measure(int, int)의 내부에서는 onMeasure(int, int)를 호출함으로써 뷰의 크기를 알아낸다.
        // 이 크기 값으로 onDraw()에서 그려지게 된다.
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
    // 처음 화면이 띄워지면
    // onMeasure의 Log는 id와 password의 status가 각각 2번씩 4번 Log가 찍힌다.
    // onLayout에서는 Log가 딱 2번 찍힌다.
    // 내부에서 뷰를 그릴 때, 호출되는 횟수로 보이는데, 나는 크기에 손을 댈 생각은 없기 때문에
    // 1번만 호출되는 onLayout에 로직을 구현하는게 좋아보인다.

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 부모노드에서 자식노드를 경유하며 실행되며, 뷰와 자식뷰들의 크기와 위치를 할당할 때 사용된다.
        // measure(int, int)에 의해 각 뷰에 저장된 크기를 사용하여 위치를 지정한다.
        // 내부적으로 onLayout()를 호출하고 onLayout()에서 실제 뷰의 위치를 할당하는 구조로 되어있다.
        // 주의할 점은 이 위치가 장비 디스플레이의 절대적 위치이다.
        // 부모를 기준으로한 상대적인 위치가 아니다.
        super.onLayout(changed, l, t, r, b)
        when(status) {
            "id" -> {
                when(login_username_input.text.toString()) {
                    "test" -> {login_username_circle.isChecked = true}
                    else -> {login_username_circle.isChecked = false}
                }
            }
            "password" -> {
                when(login_password_input.text.toString()) {
                    "test" -> {login_password_circle.isChecked = true}
                    else -> {login_password_circle.isChecked = false}
                }
            }
        }
//        when(status) {
//            "id" -> {
//                if(login_username_input.text.toString() == "test") {
//                    login_username_circle.isClickable = true
//                }
//
//            }
//            "password" -> {
//                if(login_password_input.text.toString() == "test") {
//                    login_password_circle.isClickable = true
//                }
//            }
//        }
    }
}


