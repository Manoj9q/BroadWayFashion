package com.app.broadwayfashion.model.product


import com.google.gson.annotations.SerializedName

data class PotentialAction(
    @SerializedName("query-input")
    val queryInput: String?,
 /*   @SerializedName("target")
    val target: List<String?>?,*/
    @SerializedName("@type")
    val type: String?
)