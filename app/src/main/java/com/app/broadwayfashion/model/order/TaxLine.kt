package com.app.broadwayfashion.model.order


import com.google.gson.annotations.SerializedName

data class TaxLine(
    @SerializedName("compound")
    val compound: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("label")
    val label: String?,
    @SerializedName("meta_data")
    val metaData: List<Any?>?,
    @SerializedName("rate_code")
    val rateCode: String?,
    @SerializedName("rate_id")
    val rateId: Int?,
    @SerializedName("rate_percent")
    val ratePercent: Double?,
    @SerializedName("shipping_tax_total")
    val shippingTaxTotal: String?,
    @SerializedName("tax_total")
    val taxTotal: String?
) : java.io.Serializable