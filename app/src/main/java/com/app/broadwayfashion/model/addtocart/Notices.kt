package com.app.broadwayfashion.model.addtocart


import com.google.gson.annotations.SerializedName

data class Notices(
    @SerializedName("success")
    val success: List<String?>?
)