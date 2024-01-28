package com.nurikhsan.tugasptcms.data.response

import com.google.gson.annotations.SerializedName

data class ResponseDetailPersonalList(

	@field:SerializedName("item_count")
	val itemCount: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("favorite_count")
	val favoriteCount: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("created_by")
	val createdBy: String,

	@field:SerializedName("items")
	val items: List<PersonalListMovies>,

	@field:SerializedName("iso_639_1")
	val iso6391: String,

	@field:SerializedName("poster_path")
	val posterPath: String
)

data class PersonalListMovies(

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("media_type")
	val mediaType: String,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("id")
	val id: String
)
