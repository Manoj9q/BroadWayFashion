package com.app.broadwayfashion.model.cart


import com.google.gson.annotations.SerializedName

data class Packages(
    @SerializedName("default")
    val default: Default?
)