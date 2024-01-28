package com.nurikhsan.tugasptcms.utils

import android.content.Context
import android.content.SharedPreferences

class PrefAuth(context: Context) {

    private val PRIVATE_MODE = 0
    private val PREF_NAME = "AUTH_PREF"
    private val IS_LOGIN = "IS_LOGIN"
    private val SESSION_ID = "SESSION_ID"

    var pref : SharedPreferences? = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor : SharedPreferences.Editor? = pref?.edit()

    fun setLogin(isLogin: Boolean){
        editor?.putBoolean(IS_LOGIN, isLogin)
        editor?.commit()
    }

    fun isLogin(): Boolean?{
        return pref?.getBoolean(IS_LOGIN, false)
    }

    fun setSessionId(sessionId: String?){
        editor?.putString(SESSION_ID, sessionId)
        editor?.commit()
    }

    fun getSessionId(): String?{
        return pref?.getString(SESSION_ID, "")
    }

    fun logoOut(){
        editor?.clear()
        editor?.commit()
    }
}