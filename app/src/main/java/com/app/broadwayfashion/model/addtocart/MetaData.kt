package com.app.broadwayfashion.model.addtocart


import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("items")
    val items: String?
)