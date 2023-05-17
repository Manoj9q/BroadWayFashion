package com.app.broadwayfashion.model.addtocart


import com.google.gson.annotations.SerializedName

data class Default(
    @SerializedName("chosen_method")
    val chosenMethod: String?,
    @SerializedName("formatted_destination")
    val formattedDestination: String?,
    @SerializedName("index")
    val index: Int?,
    @SerializedName("package_details")
    val packageDetails: String?,
    @SerializedName("package_name")
    val packageName: String?,
    @SerializedName("rates")
    val rates: Rates?
)