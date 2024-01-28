package com.nurikhsan.tugasptcms.utils

object Constant {

    const val API_KEY = "5b0c2665401600420e433e4b6efa2cb3"
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val SHARE = "https://www.themoviedb.org/movie/"
    const val URL_AVATAR = "https://image.tmdb.org/t/p/w300/"
    const val URL_POSTER = "https://image.tmdb.org/t/p/w300/"
    const val URL_BACKDROP_PATH = "https://image.tmdb.org/t/p/w1280/"
    const val URL_THUMBNAIL = "https://img.youtube.com/vi/"
    const val URL_TRAILER = "https://www.youtube.com/watch?v="

    //movies
    const val DISCOVER = "discover/movie"
    const val DETAIL_MOVIE = "movie/{movie_id}"
    const val SEARCH_MOVIE = "search/movie"
    const val TRAILERS = "movie/{movie_id}/videos"

    //auth
    const val REQUEST_TOKEN = "authentication/token/new"
    const val VALIDATE_WITH_LOGIN = "authentication/token/validate_with_login"
    const val CREATE_SESSION = "authentication/session/new"
    const val ACCOUNT_DETAIL = "account/{account_id}"

    //personal list
    const val PERSONAL_LIST = "account/{account_id}/lists"
    const val CREATE_LIST = "list"
    const val DETAIL_PERSONAL_LIST = "list/{list_id}"
    const val ADD_MOVIE_TO_PERSONAL_LIST = "list/{list_id}/add_item"
    const val DELETE_LIST = "list/{list_id}"
    const val REMOVE_MOVIE = "list/{list_id}/remove_item"

    //Rating
    const val ADD_RATING = "movie/{movie_id}/rating"
    const val DELETE_RATING = "movie/{movie_id}/rating"
    const val RATED_MOVIES = "account/{account_id}/rated/movies"


}