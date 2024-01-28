package com.nurikhsan.tugasptcms.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nurikhsan.tugasptcms.data.repositories.Repositories
import com.nurikhsan.tugasptcms.data.response.LoginRequest
import com.nurikhsan.tugasptcms.data.response.RequestCreateList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(private val repositories: Repositories): ViewModel() {

    val requestToken = repositories.getRequestToken().asLiveData()

    fun validateLogin(token: String, loginRequest: LoginRequest) = repositories.validateLogin(token, loginRequest).asLiveData()

    fun sessionId(token: String) = repositories.createSession(token).asLiveData()

    fun getDetailAccount(accountId: String, sessionId: String) = repositories.getDetailAccount(accountId, sessionId).asLiveData()

    fun createPersonalList(sessionId: String, requestCreateList: RequestCreateList) = repositories.createPersonalList(sessionId, requestCreateList).asLiveData()

    fun getRatedMovies(accountId: String, sessionId: String) = repositories.getRatedMovies(accountId, sessionId).asLiveData()

}