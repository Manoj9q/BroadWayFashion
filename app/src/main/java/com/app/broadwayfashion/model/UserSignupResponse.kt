package com.app.broadwayfashion.model


import com.google.gson.annotations.SerializedName

data class UserSignupResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("username")
    val username: String?
)