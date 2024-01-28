package com.nurikhsan.tugasptcms.data.response

import com.google.gson.annotations.SerializedName

data class ResponseDetailMovie(

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("backdrop_path")
	val backdropPath: String,

	@field:SerializedName("genres")
	val genres: List<GenresItem>,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("original_title")
	val originalTitle: String,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("tagline")
	val tagline: String,

	@field:SerializedName("homepage")
	val homepage: String,

	@field:SerializedName("vote_average")
	val vote: Double

)

data class GenresItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
