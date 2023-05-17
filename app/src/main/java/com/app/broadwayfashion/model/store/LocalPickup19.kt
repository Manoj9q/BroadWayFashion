package com.app.broadwayfashion.model.store


import com.google.gson.annotations.SerializedName

data class LocalPickup19(
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
    @SerializedName("method_id")
    val methodId: String?,
    @SerializedName("taxes")
    val taxes: List<Any?>?
)