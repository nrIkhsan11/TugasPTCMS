package com.nurikhsan.tugasptcms.data.response

import com.google.gson.annotations.SerializedName

data class ResponseRequestToken(

	@field:SerializedName("expires_at")
	val expiresAt: String,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("request_token")
	val requestToken: String
)

data class LoginRequest(val username: String, val password: String)
