package com.app.broadwayfashion.model.cart


import com.google.gson.annotations.SerializedName

data class FlatRate1(
    @SerializedName("chosen_method")
    val chosenMethod: Boolean?,
    @SerializedName("cost")
    val cost: String?,
    @SerializedName("html")
    val html: String?,
    @SerializedName("instance_id")
    val instanceId: Int?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("label")
    val label: String?,
    @SerializedName("meta_data")
    val metaData: MetaData?,
    @SerializedName("method_id")
    val methodId: String?,
    @SerializedName("taxes")
    val taxes: String?
)