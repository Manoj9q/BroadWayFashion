package com.app.broadwayfashion.model

import com.app.broadwayfashion.model.customer.Billing
import com.app.broadwayfashion.model.customer.Shipping
import com.google.gson.annotations.SerializedName

data class ShippingUpdateRequest(
    @SerializedName("shipping")
    var shipping: Shipping,
    @SerializedName("billing")
    var billing: Billing
)