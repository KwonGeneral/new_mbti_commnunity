package com.kwon.new_mbti_community.ViewModel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData

class ScreenViewModel {
    val screen_status = MutableLiveData<String>()

    companion object {
        var instance: ScreenViewModel? = null

        @JvmName("fragment_getInstance")
        fun getInstance(): ScreenViewModel {
            instance?.let {
                return it
            }
            instance = ScreenViewModel()
            return instance!!
        }
    }
}