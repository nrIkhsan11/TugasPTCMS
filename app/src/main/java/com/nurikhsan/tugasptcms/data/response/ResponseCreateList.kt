package com.nurikhsan.tugasptcms.data.response

import com.google.gson.annotations.SerializedName

data class ResponseCreatedList(

	@field:SerializedName("status_message")
	val statusMessage: String,

	@field:SerializedName("status_code")
	val statusCode: Int,

	@field:SerializedName("list_id")
	val listId: String,

	@field:SerializedName("success")
	val success: Boolean
)

data class RequestCreateList(

	@field:SerializedName("name")
	val listName: String,

	@field:SerializedName("description")
	val listDescription: String

)

data class ResponseDeletedList(

	@field:SerializedName("status_message")
	val statusMessage: String,

	@field:SerializedName("status_code")
	val statusCode: Int,
)
