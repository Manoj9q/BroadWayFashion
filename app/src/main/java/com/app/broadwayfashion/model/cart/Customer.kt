package com.app.broadwayfashion.model.cart


import com.google.gson.annotations.SerializedName

data class Customer(
    @SerializedName("billing_address")
    val billingAddress: BillingAddress?,
    @SerializedName("shipping_address")
    val shippingAddress: ShippingAddress?
)