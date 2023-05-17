package com.app.broadwayfashion.model.addtocart


import com.google.gson.annotations.SerializedName

data class Quantity(
    @SerializedName("max_purchase")
    val maxPurchase: Int?,
    @SerializedName("min_purchase")
    val minPurchase: Int?,
    @SerializedName("value")
    val value: Int?
)