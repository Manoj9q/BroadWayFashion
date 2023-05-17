package com.app.broadwayfashion.model.addtocart


import com.google.gson.annotations.SerializedName

data class TotalsX(
    @SerializedName("discount_tax")
    val discountTax: String?,
    @SerializedName("discount_total")
    val discountTotal: String?,
    @SerializedName("fee_tax")
    val feeTax: String?,
    @SerializedName("fee_total")
    val feeTotal: String?,
    @SerializedName("shipping_tax")
    val shippingTax: String?,
    @SerializedName("shipping_total")
    val shippingTotal: String?,
    @SerializedName("subtotal")
    val subtotal: String?,
    @SerializedName("subtotal_tax")
    val subtotalTax: String?,
    @SerializedName("total")
    val total: String?,
    @SerializedName("total_tax")
    val totalTax: String?
)