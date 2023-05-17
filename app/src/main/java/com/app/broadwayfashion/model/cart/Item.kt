package com.app.broadwayfashion.model.cart


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("backorders")
    val backorders: String?,
/*    @SerializedName("cart_item_data")
    val cartItemData: List<Any?>?,*/
/*    @SerializedName("categories")
    val categories: Boolean?,*/
    @SerializedName("featured_image")
    val featuredImage: String?,
/*    @SerializedName("gallery")
    val gallery: List<Any?>?,*/
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_discounted")
    val isDiscounted: Boolean?,
    @SerializedName("item_key")
    val itemKey: String?,
    @SerializedName("meta")
    val meta: Meta?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("permalink")
    val permalink: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("price_discounted")
    val priceDiscounted: String?,
    @SerializedName("price_regular")
    val priceRegular: String?,
    @SerializedName("price_sale")
    val priceSale: String?,
    @SerializedName("quantity")
    val quantity: Quantity?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("stock_status")
    val stockStatus: StockStatus?,
    @SerializedName("tags")
    val tags: Boolean?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("totals")
    val totals: Totals?
)