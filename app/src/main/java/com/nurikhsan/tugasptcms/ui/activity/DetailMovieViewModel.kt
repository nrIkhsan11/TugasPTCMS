package com.nurikhsan.tugasptcms.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nurikhsan.tugasptcms.data.repositories.Repositories
import com.nurikhsan.tugasptcms.data.response.MovieId
import com.nurikhsan.tugasptcms.data.response.RatingRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val repositories: Repositories): ViewModel() {

    fun detailMovie(movieId: String) = repositories.getDetailMovies(movieId).asLiveData()

    fun addRating(movieId: String, sessionId: String, data: RatingRequest) = repositories.addRating(movieId, sessionId, data).asLiveData()

    fun addMovieToPersonalList(listId: String, sessionId: String, movieId: MovieId) = repositories.addToPersonalList(listId, sessionId, movieId).asLiveData()

    fun deleteRating(movieId: String, sessionId: String) = repositories.deleteRating(movieId, sessionId).asLiveData()

    fun getTrailers(movieId: String) = repositories.getTrailers(movieId).asLiveData()
}