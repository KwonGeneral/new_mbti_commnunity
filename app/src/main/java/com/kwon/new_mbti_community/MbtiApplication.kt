package com.kwon.new_mbti_community

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.kwon.new_mbti_community.Common.PasswordCheck
import com.kwon.new_mbti_community.Common.SharedDB
import com.kwon.new_mbti_community.Contain.ApiDefine
import com.kwon.new_mbti_community.Contain.ColorStatus
import com.kwon.new_mbti_community.ViewModel.LoginSignupViewModel

// 공용 함수나 변수 관리
class MbtiApplication: Application() {

    fun snackBarShow(layout: ConstraintLayout, text: String, duration: Int, back_color: String, text_color: String, view: View? = null, view_color: String? = null) {
        Snackbar.make(layout, text, duration).setBackgroundTint(Color.parseColor(back_color)).setTextColor(Color.parseColor(text_color)).show()
        view?.let { view.backgroundTintList = ColorStateList.valueOf(Color.parseColor(view_color)) }
    }

    fun viewColorChange(color: String, vararg view: View) {
        for(k in view) { k.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color)) }
    }

    fun textIsBlank(vararg str:String): Boolean {
        for(k in str) { if(k.isBlank()) return true }
        return false
    }

    fun editCheckViewColor(reg: String, editText: EditText, view: View) {
        if(PasswordCheck().check(reg, editText.text.toString()) != null) {
            view.backgroundTintList = ColorStateList.valueOf(Color.parseColor(ColorStatus.TEXT_SUCCESS.color))
        } else {
            view.backgroundTintList = ColorStateList.valueOf(Color.parseColor(ColorStatus.TEXT_DEFAULT.color))
        }
    }

    fun layoutClickKeyboardDown(activity: Activity? = null, view: View? = null, context: Context? = null, layout: ViewGroup, vararg editText: EditText) {
        layout.setOnClickListener {
            if(activity != null) {
                val mInputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                mInputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
            }else if(view != null && context != null) {
                val mInputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                mInputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
            for(k in editText) { k.clearFocus() }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun spinnerClickKeyboardDown(activity: Activity? = null, view: View? = null, context: Context? = null, spinner: Spinner, vararg editText: EditText) {
        spinner.setOnTouchListener { v, event ->
            if(activity != null) {
                val mInputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                mInputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
            }else if(view != null && context != null) {
                val mInputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                mInputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
            for(k in editText) { k.clearFocus() }
           false
        }
    }
    fun setMaxLength(editText: EditText, maxLength: Int, maxLines: Int){
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        editText.maxLines = maxLines
    }
    fun getADS(context:Context, adv_view: AdView) {
        MobileAds.initialize(context)
        adv_view.loadAd(AdRequest.Builder().build())
    }

    override fun onCreate() {
        super.onCreate()

        // FCM
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            SharedDB(this).setString(ApiDefine.FCM_TOKEN, task.result.toString())
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
        })
    }
}