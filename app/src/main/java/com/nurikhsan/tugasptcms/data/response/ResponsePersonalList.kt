package com.nurikhsan.tugasptcms.data.response

import com.google.gson.annotations.SerializedName

data class ResponsePersonalList(

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<PersonalListItem>,

	@field:SerializedName("total_results")
	val totalResults: Int
)

data class PersonalListItem(

	@field:SerializedName("item_count")
	val itemCount: Int,

	@field:SerializedName("list_type")
	val listType: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("favorite_count")
	val favoriteCount: Int,

	@field:SerializedName("id")
	val id: String
)
