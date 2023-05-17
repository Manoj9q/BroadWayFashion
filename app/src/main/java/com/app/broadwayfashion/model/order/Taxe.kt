package com.app.broadwayfashion.model.order


import com.google.gson.annotations.SerializedName

data class Taxe(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("subtotal")
    val subtotal: String?,
    @SerializedName("total")
    val total: String?
):java.io.Serializable