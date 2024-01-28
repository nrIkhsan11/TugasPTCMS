package com.nurikhsan.tugasptcms.utils

import android.content.Context
import android.content.SharedPreferences

class PrefsRating(context: Context) {

    private val PRIVATE_MODE = 0
    private val PREF_NAME = "shared_preferences_rating"

    var pref : SharedPreferences? = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor : SharedPreferences.Editor? = pref?.edit()

    fun setRating(movieId: String, value: Double?){
        editor?.putFloat(movieId, value!!.toFloat())
        editor?.commit()
    }

    fun getRating(movieId: String): Float? {
        return pref?.getFloat(movieId, 0.0f)
    }

    fun removeData(movieId: String){
        editor?.remove(movieId)
        editor?.commit()
    }
}