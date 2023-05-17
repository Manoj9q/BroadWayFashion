package com.app.broadwayfashion.model.store


import com.google.gson.annotations.SerializedName

data class GetStoreInfoResponse(
    @SerializedName("flat_rate:1")
    val flatRate1: FlatRate1?,
    @SerializedName("free_shipping:3")
    val freeShipping3: FreeShipping3?,
    @SerializedName("local_pickup:19")
    val localPickup19: LocalPickup19?,
    @SerializedName("local_pickup:20")
    val localPickup20: LocalPickup19?,
    @SerializedName("local_pickup:22")
    val localPickup22: LocalPickup19?
)