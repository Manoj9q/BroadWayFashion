package com.app.broadwayfashion.model.cart


import com.google.gson.annotations.SerializedName

data class Shipping(
    @SerializedName("has_calculated_shipping")
    val hasCalculatedShipping: Boolean?,
   /* @SerializedName("packages")
    val packages: Packages?,*/
    @SerializedName("show_package_details")
    val showPackageDetails: Boolean?,
    @SerializedName("total_packages")
    val totalPackages: Int?
)