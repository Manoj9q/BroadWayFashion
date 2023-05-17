package com.app.broadwayfashion.model.order


import com.google.gson.annotations.SerializedName

data class ShippingLine(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("instance_id")
    val instanceId: String?,
    @SerializedName("meta_data")
    val metaData: List<MetaData>?,
    @SerializedName("method_id")
    val methodId: String?,
    @SerializedName("method_title")
    val methodTitle: String?,
    @SerializedName("taxes")
    val taxes: List<Taxe>?,
    @SerializedName("total")
    val total: String?,
    @SerializedName("total_tax")
    val totalTax: String?
):java.io.Serializable