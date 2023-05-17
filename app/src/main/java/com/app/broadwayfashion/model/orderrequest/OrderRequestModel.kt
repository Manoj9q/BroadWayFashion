package com.app.broadwayfashion.model.orderrequest


import com.app.broadwayfashion.model.customer.Billing
import com.app.broadwayfashion.model.customer.Shipping
import com.google.gson.annotations.SerializedName

data class OrderRequestModel(
    @SerializedName("billing")
    var billing: Billing? = null,
    @SerializedName("shipping")
    var shipping: Shipping? = null,
    @SerializedName("customer_id")
    var customerId: String? = null,
    @SerializedName("line_items")
    var lineItems: ArrayList<LineItem?>? = ArrayList(),
    @SerializedName("payment_method")
    var paymentMethod: String? = null,
    @SerializedName("set_paid")
    var setPaid: Boolean? = false,
    @SerializedName("shipping_lines")
    var shippingLines: ArrayList<ShippingLine?>? = ArrayList(),
    @SerializedName("status")
    var status: String? = null
) : java.io.Serializable