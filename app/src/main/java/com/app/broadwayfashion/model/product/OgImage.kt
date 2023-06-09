package com.app.broadwayfashion.model.product


import com.google.gson.annotations.SerializedName

data class OgImage(
    @SerializedName("height")
    val height: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
)