package com.app.broadwayfashion.model.addtocart


import com.google.gson.annotations.SerializedName

data class Variation(
    @SerializedName("Colour")
    val colour: String?,
    @SerializedName("Size")
    val size: String?
)