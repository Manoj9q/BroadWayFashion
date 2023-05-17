package com.app.broadwayfashion.model.addtocart


import com.google.gson.annotations.SerializedName

data class StockStatus(
    @SerializedName("hex_color")
    val hexColor: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("stock_quantity")
    val stockQuantity: Int?
)