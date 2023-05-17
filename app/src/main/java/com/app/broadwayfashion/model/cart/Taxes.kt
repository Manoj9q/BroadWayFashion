package com.app.broadwayfashion.model.cart


import com.google.gson.annotations.SerializedName

data class Taxes(
    @SerializedName("CA-ON-HST (13%)-1")
    val cAONHST131: CAONHST131?
)