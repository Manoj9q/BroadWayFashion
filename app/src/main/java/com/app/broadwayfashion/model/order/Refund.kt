package com.app.broadwayfashion.model.order


import com.google.gson.annotations.SerializedName

data class Refund(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("reason")
    val reason: String?,
    @SerializedName("total")
    val total: String?
):java.io.Serializable