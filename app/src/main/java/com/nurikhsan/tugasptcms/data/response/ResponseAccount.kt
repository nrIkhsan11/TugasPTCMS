package com.nurikhsan.tugasptcms.data.response

import com.google.gson.annotations.SerializedName

data class ResponseAccount(

	@field:SerializedName("avatar")
	val avatar: Avatar,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("iso_639_1")
	val iso6391: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("name")
	val name: String
)

data class Gravatar(

	@field:SerializedName("hash")
	val hash: String
)

data class Tmdb(

	@field:SerializedName("avatar_path")
	val avatarPath: String
)

data class Avatar(

	@field:SerializedName("tmdb")
	val tmdb: Tmdb,

	@field:SerializedName("gravatar")
	val gravatar: Gravatar
)
