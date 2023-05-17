package com.app.broadwayfashion.model.addtocart


import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("currency_code")
    val currencyCode: String?,
    @SerializedName("currency_decimal_separator")
    val currencyDecimalSeparator: String?,
    @SerializedName("currency_minor_unit")
    val currencyMinorUnit: Int?,
    @SerializedName("currency_prefix")
    val currencyPrefix: String?,
    @SerializedName("currency_suffix")
    val currencySuffix: String?,
    @SerializedName("currency_symbol")
    val currencySymbol: String?,
    @SerializedName("currency_thousand_separator")
    val currencyThousandSeparator: String?
)