package com.nurikhsan.tugasptcms.data.network

import com.nurikhsan.tugasptcms.data.response.LoginRequest
import com.nurikhsan.tugasptcms.data.response.MovieId
import com.nurikhsan.tugasptcms.data.response.RatingRequest
import com.nurikhsan.tugasptcms.data.response.RequestCreateList
import com.nurikhsan.tugasptcms.data.response.ResponseAccount
import com.nurikhsan.tugasptcms.data.response.ResponseAddMovieToPersonalList
import com.nurikhsan.tugasptcms.data.response.ResponseAddRating
import com.nurikhsan.tugasptcms.data.response.ResponseCreatedList
import com.nurikhsan.tugasptcms.data.response.ResponseDeletedList
import com.nurikhsan.tugasptcms.data.response.ResponseDetailMovie
import com.nurikhsan.tugasptcms.data.response.ResponseDetailPersonalList
import com.nurikhsan.tugasptcms.data.response.ResponseMovies
import com.nurikhsan.tugasptcms.data.response.ResponsePersonalList
import com.nurikhsan.tugasptcms.data.response.ResponseRequestToken
import com.nurikhsan.tugasptcms.data.response.ResponseSessionId
import com.nurikhsan.tugasptcms.data.response.ResponseTrailers
import com.nurikhsan.tugasptcms.utils.Constant.ACCOUNT_DETAIL
import com.nurikhsan.tugasptcms.utils.Constant.ADD_MOVIE_TO_PERSONAL_LIST
import com.nurikhsan.tugasptcms.utils.Constant.ADD_RATING
import com.nurikhsan.tugasptcms.utils.Constant.CREATE_LIST
import com.nurikhsan.tugasptcms.utils.Constant.CREATE_SESSION
import com.nurikhsan.tugasptcms.utils.Constant.DELETE_LIST
import com.nurikhsan.tugasptcms.utils.Constant.DELETE_RATING
import com.nurikhsan.tugasptcms.utils.Constant.DETAIL_MOVIE
import com.nurikhsan.tugasptcms.utils.Constant.DETAIL_PERSONAL_LIST
import com.nurikhsan.tugasptcms.utils.Constant.DISCOVER
import com.nurikhsan.tugasptcms.utils.Constant.PERSONAL_LIST
import com.nurikhsan.tugasptcms.utils.Constant.RATED_MOVIES
import com.nurikhsan.tugasptcms.utils.Constant.REMOVE_MOVIE
import com.nurikhsan.tugasptcms.utils.Constant.REQUEST_TOKEN
import com.nurikhsan.tugasptcms.utils.Constant.SEARCH_MOVIE
import com.nurikhsan.tugasptcms.utils.Constant.TRAILERS
import com.nurikhsan.tugasptcms.utils.Constant.VALIDATE_WITH_LOGIN
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(REQUEST_TOKEN)
    suspend fun getToken(@Query("api_key") apiKey: String
    ): Response<ResponseRequestToken>

    @POST(VALIDATE_WITH_LOGIN)
    suspend fun validateLogin(
        @Query("api_key") apiKey: String,
        @Query("request_token") token: String,
        @Body loginRequest: LoginRequest
    ): Response<ResponseRequestToken>

    @POST(CREATE_SESSION)
    suspend fun createSessionId(
        @Query("api_key") apiKey: String,
        @Query("request_token") token: String
    ): Response<ResponseSessionId>

    @GET(ACCOUNT_DETAIL)
    suspend fun getAccountDetail(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Response<ResponseAccount>

    @POST(CREATE_LIST)
    suspend fun createPersonalList(
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body requestCreateList: RequestCreateList
    ): Response<ResponseCreatedList>

    @GET(PERSONAL_LIST)
    suspend fun getMyPersonalList(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Response<ResponsePersonalList>

    @GET(DETAIL_PERSONAL_LIST)
    suspend fun detailPersonalList(
        @Path("list_id") lisId: String,
        @Query("api_key") apiKey: String
    ): Response<ResponseDetailPersonalList>

    @POST(ADD_MOVIE_TO_PERSONAL_LIST)
    suspend fun addMovieToPersonalList(
        @Path("list_id") listId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body movieId: MovieId
    ): Response<ResponseAddMovieToPersonalList>

    @DELETE(DELETE_LIST)
    suspend fun deleteList(
        @Path("list_id") listId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
    ): Response<ResponseDeletedList>

    @GET(DISCOVER)
    suspend fun getDiscoverMovies(@Query("api_key") apiKey: String): Response<ResponseMovies>

    @GET(SEARCH_MOVIE)
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("api_key") apiKey: String
    ): Response<ResponseMovies>

    @GET(DETAIL_MOVIE)
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ): Response<ResponseDetailMovie>

    @GET(TRAILERS)
    suspend fun getTrailers(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ): Response<ResponseTrailers>

    @POST(ADD_RATING)
    suspend fun addRatingMovie(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body ratingRequest: RatingRequest
    ): Response<ResponseAddRating>

    @DELETE(DELETE_RATING)
    suspend fun deleteRating(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
    ): Response<ResponseAddRating>

    @GET(RATED_MOVIES)
    suspend fun getRatedMovies(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Response<ResponseMovies>

    @POST(REMOVE_MOVIE)
    suspend fun removeMovie(
        @Path("list_id") listId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body movieId: MovieId
    ): Response<ResponseDeletedList>
}