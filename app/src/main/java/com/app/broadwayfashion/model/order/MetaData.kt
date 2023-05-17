package com.app.broadwayfashion.model.order


import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("display_key")
    val displayKey: String?,
    @SerializedName("display_value")
    val displayValue: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("value")
    val value: String?
):java.io.Serializable