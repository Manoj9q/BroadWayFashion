package com.app.broadwayfashion.model.cart


import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("items")
    val items: String?
)