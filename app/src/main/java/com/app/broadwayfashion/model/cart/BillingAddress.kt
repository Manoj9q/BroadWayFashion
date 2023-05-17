package com.app.broadwayfashion.model.cart


import com.google.gson.annotations.SerializedName

data class BillingAddress(
    @SerializedName("billing_address_1")
    val billingAddress1: String?,
    @SerializedName("billing_address_2")
    val billingAddress2: String?,
    @SerializedName("billing_city")
    val billingCity: String?,
    @SerializedName("billing_company")
    val billingCompany: String?,
    @SerializedName("billing_country")
    val billingCountry: String?,
    @SerializedName("billing_email")
    val billingEmail: String?,
    @SerializedName("billing_first_name")
    val billingFirstName: String?,
    @SerializedName("billing_last_name")
    val billingLastName: String?,
    @SerializedName("billing_phone")
    val billingPhone: String?,
    @SerializedName("billing_postcode")
    val billingPostcode: String?,
    @SerializedName("billing_state")
    val billingState: String?
)