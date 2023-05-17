package com.app.broadwayfashion.model.addtocart


import com.google.gson.annotations.SerializedName

data class Dimensions(
    @SerializedName("height")
    val height: String?,
    @SerializedName("length")
    val length: String?,
    @SerializedName("unit")
    val unit: String?,
    @SerializedName("width")
    val width: String?
)