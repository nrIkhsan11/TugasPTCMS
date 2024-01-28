package com.nurikhsan.tugasptcms.data.repositories

import android.content.Context
import android.util.Log
import com.nurikhsan.tugasptcms.data.remote.RemoteDataSource
import com.nurikhsan.tugasptcms.data.response.LoginRequest
import com.nurikhsan.tugasptcms.data.response.MovieId
import com.nurikhsan.tugasptcms.data.response.RatingRequest
import com.nurikhsan.tugasptcms.data.response.RequestCreateList
import com.nurikhsan.tugasptcms.utils.PrefAuth
import com.nurikhsan.tugasptcms.utils.PrefsRating
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repositories @Inject constructor(private val remoteDataSource: RemoteDataSource, private val context: Context) {

    private val prefsAuth = PrefAuth(context)
    private val prefsRating = PrefsRating(context)

    fun getRequestToken() = flow {
        try {
            remoteDataSource.getRequestToken().let {
                if (it.isSuccessful){
                    prefsAuth.setLogin(true)
                    val token = it.body()?.requestToken
                    emit(token)
                    Log.e("Tag", token.toString())
                }
            }
        }catch (e: Exception){
            Log.e("Tag", e.message.toString())
        }
    }

    fun validateLogin(token: String, data: LoginRequest) = flow {
        try {
            remoteDataSource.validateLogin(token, data).let {
                if (it.isSuccessful){
                    prefsAuth.setLogin(true)
                    val login = it.body()?.requestToken
                    emit(login)
                    Log.e("Tag", login.toString())
                }
            }
        }catch (e: Exception){
            Log.e("Tag", e.message.toString())
        }
    }

    fun createSession(token: String) = flow {
        try {
            remoteDataSource.createSession(token).let {
                if (it.isSuccessful){
                    prefsAuth.setLogin(true)
                    val session = it.body()?.sessionId
                    emit(session)
                    prefsAuth.setSessionId(session)
                    Log.e("Tag", session.toString())
                }
            }
        }catch (e: Exception){
            Log.e("Tag", e.message.toString())
        }
    }

    fun getDetailAccount(accountId: String, sessionId: String) = flow {
        try {
            remoteDataSource.getDetailAccount(accountId, sessionId).let {
                if (it.isSuccessful){
                    val account = it.body()
                    emit(account)
                    Log.e("Tag", account.toString())
                }
            }
        }catch (e: Exception){
            Log.e("Tag", e.message.toString())
        }
    }

    fun createPersonalList(sessionId: String, requestCreateList: RequestCreateList) = flow {
        try {
            remoteDataSource.createPersonalList(sessionId, requestCreateList).let {
                if (it.isSuccessful){
                    val list = it.body()
                    emit(list)
                    Log.e("Tag", list.toString())
                }
            }
        }catch (e: Exception){
            Log.e("Tag", e.message.toString())
        }
    }

    fun getMyPersonalList(accountId: String, sessionId: String) = flow {
        try {
            remoteDataSource.getMyPersonalList(accountId, sessionId).let {
                if (it.isSuccessful){
                    val detailPersonalList = it.body()?.results
                    emit(detailPersonalList)
                    Log.e("Tag", detailPersonalList.toString())
                }
            }
        }catch (e: Exception){
            Log.e("Tag", e.message.toString())
        }
    }

    fun detailPersonalList(listId: String) = flow {
        try {
            remoteDataSource.detailPersonalList(listId).let {
                if (it.isSuccessful){
                    val detailPersonalList = it.body()
                    emit(detailPersonalList)
                    Log.e("Tag", detailPersonalList.toString())
                }
            }
        }catch (e: Exception){
            Log.e("Tag", e.message.toString())
        }
    }

    fun addToPersonalList(listId: String, sessionId: String, movieId: MovieId) = flow {
        try {
            remoteDataSource.addMovieToPersonalList(listId, sessionId, movieId).let {
                if (it.isSuccessful){
                    val list = it.body()
                    emit(list)
                    Log.e("Tag", list.toString())
                }
            }
        }catch (e: Exception){
            Log.e("Tag", e.message.toString())
        }
    }

    fun getDiscoverMovies() = flow {
        try {
            remoteDataSource.getDiscover().let {
                if (it.isSuccessful){
                    val discover = it.body()?.results
                    emit(discover)
                    Log.e("Tag", discover.toString())
                }
            }
        }catch (e: Exception){
            Log.e("Tag", e.message.toString())
        }
    }

    fun searchMovie(query: String) = flow {
        try {
            remoteDataSource.searchMovies(query).let {
                if (it.isSuccessful) {
                    val movie = it.body()?.results
                    emit(movie)
                    Log.e("Tag", movie.toString())
                }
            }
        } catch (e: Exception) {
            Log.e("Tag", e.message.toString())
        }
    }

    fun getDetailMovies(movieId: String) = flow {
        try {
            remoteDataSource.detailMovies(movieId).let {
                if (it.isSuccessful){
                    val detailMovie = it.body()
                    emit(detailMovie)
                    Log.e("Tag", detailMovie.toString())
                }
            }
        }catch (e: Exception){
            Log.e("Tag", e.message.toString())
        }
    }

    fun getTrailers(movieId: String) = flow {
        try {
            remoteDataSource.getTrailers(movieId).let {
                if (it.isSuccessful){
                    val trailers = it.body()?.results
                    emit(trailers)
                    Log.e("Tag", trailers.toString())
                }
            }
        }catch (e: Exception){
            Log.e("Tag", e.message.toString())
        }
    }

    fun addRating(movieId: String, sessionId: String, data: RatingRequest) = flow {
        try {
            remoteDataSource.addRatingMovie(movieId, sessionId, data).let {
                if (it.isSuccessful){
                    prefsAuth.setLogin(true)
                    val rating = it.body()
                    emit(rating)
                    prefsRating.setRating(movieId, data.value)
                    Log.e("Tag", rating.toString())
                }
            }
        }catch (e: Exception){
            Log.e("Tag", e.message.toString())
        }
    }

    fun deleteRating(movieId: String, sessionId: String) = flow {
        try {
            remoteDataSource.deleteRating(movieId, sessionId).let {
                if (it.isSuccessful) {
                    val rating = it.body()
                    emit(rating)
                    Log.e("Tag", rating.toString())
                }
            }
        } catch (e: Exception) {
            Log.e("Tag", e.message.toString())
        }
    }

    fun deleteList(listId: String, sessionId: String) = flow {
        try {
            remoteDataSource.deletePersonalList(listId, sessionId).let {
                if (it.isSuccessful) {
                    val list = it.body()
                    emit(list)
                    Log.e("Tag", list.toString())
                }
            }
        } catch (e: Exception) {
            Log.e("Tag", e.message.toString())
        }
    }

    fun removeMovie(listId: String, sessionId: String, movieId: MovieId) = flow {
        try {
            remoteDataSource.removeMovie(listId, sessionId, movieId).let {
                if (it.isSuccessful) {
                    val remove = it.body()
                    emit(remove)
                    Log.e("Tag", remove.toString())
                }
            }
        } catch (e: Exception) {
            Log.e("Tag", e.message.toString())
        }
    }

    fun getRatedMovies(accountId: String, sessionId: String) = flow {
        try {
            remoteDataSource.ratedMovies(accountId, sessionId).let {
                if (it.isSuccessful){
                    val rated = it.body()?.results
                    emit(rated)
                    Log.e("Tag", rated.toString())
                }
            }
        }catch (e: Exception){
            Log.e("Tag", e.message.toString())
        }
    }
}