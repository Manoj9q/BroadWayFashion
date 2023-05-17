package com.app.broadwayfashion.model.cart


import com.google.gson.annotations.SerializedName

data class AddToCartResponse(
    @SerializedName("cart_hash")
    val cartHash: String?,
    @SerializedName("cart_key")
    val cartKey: String?,
    @SerializedName("coupons")
    val coupons: List<Any>?,
    @SerializedName("cross_sells")
    val crossSells: List<Any>?,
    @SerializedName("currency")
    val currency: Currency?,
    @SerializedName("customer")
    val customer: Customer?,
    @SerializedName("fees")
    val fees: List<Any>?,
    @SerializedName("item_count")
    val itemCount: Int?,
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("items_weight")
    val itemsWeight: Int?,
    @SerializedName("needs_payment")
    val needsPayment: Boolean?,
    @SerializedName("needs_shipping")
    val needsShipping: Boolean?,
/*    @SerializedName("notices")
    val notices: Notices?,*/
/*    @SerializedName("removed_items")
    val removedItems: List<Any>?,*/
    @SerializedName("shipping")
    val shipping: Shipping?,
 /*   @SerializedName("taxes")
    val taxes: Taxes?,*/
    @SerializedName("totals")
    val totals: TotalsX?
)