package com.app.broadwayfashion.model.addtocart


import com.google.gson.annotations.SerializedName

data class Totals(
    @SerializedName("subtotal")
    val subtotal: String?,
    @SerializedName("subtotal_tax")
    val subtotalTax: Double?,
    @SerializedName("tax")
    val tax: Double?,
    @SerializedName("total")
    val total: Int?
)