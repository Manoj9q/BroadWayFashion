package com.app.broadwayfashion.model.admintoken


import com.google.gson.annotations.SerializedName

data class AdminTokenResponse(
    @SerializedName("token")
    val token: String?,
    @SerializedName("user_display_name")
    val userDisplayName: String?,
    @SerializedName("user_email")
    val userEmail: String?,
    @SerializedName("user_nicename")
    val userNicename: String?
)