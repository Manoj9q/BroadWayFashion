package com.app.broadwayfashion.model.addtocart


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("dimensions")
    val dimensions: Dimensions?,
    @SerializedName("downloadable")
    val downloadable: Boolean?,
    @SerializedName("product_type")
    val productType: String?,
    @SerializedName("sku")
    val sku: String?,
    @SerializedName("variation")
    val variation: Variation?,
    @SerializedName("virtual")
    val virtual: Boolean?,
    @SerializedName("weight")
    val weight: Int?
)