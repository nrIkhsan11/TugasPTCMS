package com.nurikhsan.tugasptcms.data.response

import com.google.gson.annotations.SerializedName

data class ResponseSessionId(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("session_id")
	val sessionId: String
)
