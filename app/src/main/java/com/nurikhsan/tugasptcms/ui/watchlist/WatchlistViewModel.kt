package com.nurikhsan.tugasptcms.ui.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nurikhsan.tugasptcms.data.repositories.Repositories
import com.nurikhsan.tugasptcms.data.response.MovieId
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(private val repositories: Repositories): ViewModel() {

    fun getMyPersonalList(accountId: String, sessionId: String) = repositories.getMyPersonalList(accountId, sessionId).asLiveData()

    fun detailPersonalList(listId: String) = repositories.detailPersonalList(listId).asLiveData()

    fun deletePersonalList(listId: String, sessionId: String) = repositories.deleteList(listId, sessionId).asLiveData()

    fun removeMovie(listId: String, sessionId: String, movieId: MovieId) = repositories.removeMovie(listId, sessionId, movieId).asLiveData()

}