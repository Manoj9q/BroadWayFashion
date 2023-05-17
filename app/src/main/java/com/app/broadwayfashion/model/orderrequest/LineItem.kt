package com.app.broadwayfashion.model.orderrequest


import com.google.gson.annotations.SerializedName

data class LineItem(
    @SerializedName("product_id")
    val productId: Int?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("variation_id")
    val variationId: Int?
):java.io.Serializable