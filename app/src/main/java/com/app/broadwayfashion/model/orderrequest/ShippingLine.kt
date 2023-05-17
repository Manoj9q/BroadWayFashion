package com.app.broadwayfashion.model.orderrequest


import com.google.gson.annotations.SerializedName

data class ShippingLine(
    @SerializedName("method_id")
    val methodId: String?,
    @SerializedName("method_title")
    val methodTitle: String?,
    @SerializedName("total")
    val total: String?
):java.io.Serializable