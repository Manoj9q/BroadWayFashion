package com.app.broadwayfashion.model.order


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("src")
    val src: String?
):java.io.Serializable