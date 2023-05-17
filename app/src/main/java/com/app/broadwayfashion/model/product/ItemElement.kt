package com.app.broadwayfashion.model.product


import com.google.gson.annotations.SerializedName

data class ItemElement(
    @SerializedName("item")
    val item: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("position")
    val position: Int?,
    @SerializedName("@type")
    val type: String?
)