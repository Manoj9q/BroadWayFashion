package com.app.broadwayfashion.model

import com.app.broadwayfashion.model.customer.Billing
import com.google.gson.annotations.SerializedName


data class BillingUpdateRequest(

    @SerializedName("billing")
    var billing: Billing
)