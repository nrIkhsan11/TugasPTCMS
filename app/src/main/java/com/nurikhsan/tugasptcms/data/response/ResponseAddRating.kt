package com.nurikhsan.tugasptcms.data.response

import com.google.gson.annotations.SerializedName

data class ResponseAddRating(

    @field:SerializedName("status_code")
    val statusCode: Int,

    @field:SerializedName("status_message")
    val statusMessage: String
)

data class RatingRequest(

    val value: Double

)
