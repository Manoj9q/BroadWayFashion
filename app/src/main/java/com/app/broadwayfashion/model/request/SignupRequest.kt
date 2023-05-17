package com.app.broadwayfashion.model.request

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("last_name")
    var lastName: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String
)
