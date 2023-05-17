package com.app.broadwayfashion.model.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    val token: String?,
    @SerializedName("user_display_name")
    val userDisplayName: String?,
    @SerializedName("user_email")
    val userEmail: String?,
    @SerializedName("user_nicename")
    val userNicename: String?
)