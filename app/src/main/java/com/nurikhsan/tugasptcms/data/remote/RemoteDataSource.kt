package com.nurikhsan.tugasptcms.data.remote

import com.nurikhsan.tugasptcms.data.network.ApiService
import com.nurikhsan.tugasptcms.data.response.LoginRequest
import com.nurikhsan.tugasptcms.data.response.MovieId
import com.nurikhsan.tugasptcms.data.response.RatingRequest
import com.nurikhsan.tugasptcms.data.response.RequestCreateList
import com.nurikhsan.tugasptcms.utils.Constant.API_KEY
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getRequestToken() = apiService.getToken(API_KEY)

    suspend fun validateLogin(token: String, data: LoginRequest) = apiService.validateLogin(API_KEY, token, data)

    suspend fun createSession(token: String) = apiService.createSessionId(API_KEY, token)

    suspend fun getDiscover() = apiService.getDiscoverMovies(API_KEY)

    suspend fun getDetailAccount(accountId: String, sessionId: String) = apiService.getAccountDetail(accountId, API_KEY, sessionId)

    suspend fun createPersonalList(sessionId: String, createList: RequestCreateList) = apiService.createPersonalList(
        API_KEY, sessionId, createList)

    suspend fun getMyPersonalList(accountId: String, sessionId: String) = apiService.getMyPersonalList(accountId, API_KEY, sessionId)

    suspend fun detailPersonalList(listId: String) = apiService.detailPersonalList(listId, API_KEY)

    suspend fun detailMovies(movieId: String) = apiService.getDetailMovie(movieId, API_KEY)

    suspend fun addRatingMovie(movieId: String, sessionId: String, data: RatingRequest) = apiService.addRatingMovie(movieId, API_KEY, sessionId, data)

    suspend fun deleteRating(movieId: String, sessionId: String) = apiService.deleteRating(movieId, API_KEY, sessionId)

    suspend fun addMovieToPersonalList(listId: String, sessionId: String, movieId: MovieId) = apiService.addMovieToPersonalList(listId, API_KEY, sessionId, movieId)

    suspend fun deletePersonalList(listId: String, sessionId: String) = apiService.deleteList(listId, API_KEY, sessionId)

    suspend fun removeMovie(listId: String, sessionId: String, movieId: MovieId) = apiService.removeMovie(listId, API_KEY, sessionId, movieId)

    suspend fun searchMovies(query: String) = apiService.searchMovie(query, API_KEY)

    suspend fun ratedMovies(accountId: String, sessionId: String) = apiService.getRatedMovies(accountId, API_KEY, sessionId)

    suspend fun getTrailers(movieId: String) = apiService.getTrailers(movieId, API_KEY)
}