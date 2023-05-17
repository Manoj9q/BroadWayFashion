package com.app.broadwayfashion.model.order


import com.google.gson.annotations.SerializedName

data class MetaDataX(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("value")
    val value: String?
):java.io.Serializable