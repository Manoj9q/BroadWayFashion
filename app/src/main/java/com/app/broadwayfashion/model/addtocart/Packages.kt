package com.app.broadwayfashion.model.addtocart


import com.google.gson.annotations.SerializedName

data class Packages(
    @SerializedName("default")
    val default: Default?
)