package com.app.broadwayfashion.model.addtocart


import com.google.gson.annotations.SerializedName

data class ShippingAddress(
    @SerializedName("shipping_address_1")
    val shippingAddress1: String?,
    @SerializedName("shipping_address_2")
    val shippingAddress2: String?,
    @SerializedName("shipping_city")
    val shippingCity: String?,
    @SerializedName("shipping_company")
    val shippingCompany: String?,
    @SerializedName("shipping_country")
    val shippingCountry: String?,
    @SerializedName("shipping_first_name")
    val shippingFirstName: String?,
    @SerializedName("shipping_last_name")
    val shippingLastName: String?,
    @SerializedName("shipping_postcode")
    val shippingPostcode: String?,
    @SerializedName("shipping_state")
    val shippingState: String?
)