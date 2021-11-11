package com.kwon.new_mbti_community

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.InputFilter
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.kwon.new_mbti_community.Common.PasswordCheck
import com.kwon.new_mbti_community.Contain.ColorStatus
import com.kwon.new_mbti_community.ViewModel.LoginSignupViewModel

class MbtiApplication: Application() {
    fun editCheckViewColor(reg: String, editText: EditText, view: View) {
        if(PasswordCheck().check(reg, editText.text.toString()) != null) {
            LoginSignupViewModel.getInstance().input_username_check_count.value = 1
            view.backgroundTintList = ColorStateList.valueOf(
                Color.parseColor(
                    ColorStatus.TEXT_SUCCESS.color))
        } else {
            LoginSignupViewModel.getInstance().input_username_check_count.value = 0
            view.backgroundTintList = ColorStateList.valueOf(
                Color.parseColor(
                    ColorStatus.TEXT_DEFAULT.color))
        }
    }
    fun layoutClickKeyboardDown(activity: Activity? = null, view: View? = null, context: Context? = null, layout: ConstraintLayout, vararg editText: EditText) {
        layout.setOnClickListener {
            if(activity != null) {
                val mInputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                mInputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
            }else if(view != null && context != null) {
                val mInputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                mInputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
            for(k in editText) {
                k.clearFocus()
            }
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
            for(k in editText) {
                k.clearFocus()
            }
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
}