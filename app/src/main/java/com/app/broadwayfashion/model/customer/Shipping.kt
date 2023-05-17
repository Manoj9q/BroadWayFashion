package com.app.broadwayfashion.model.customer


import com.google.gson.annotations.SerializedName

data class Shipping(
    @SerializedName("address_1")
    var address1: String? = null,
    @SerializedName("address_2")
    var address2: String? = null,
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("company")
    var company: String? = null,
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("first_name")
    var firstName: String? = null,
    @SerializedName("last_name")
    var lastName: String? = null,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("postcode")
    var postcode: String? = null,
    @SerializedName("state")
    var state: String? = null
):java.io.Serializable