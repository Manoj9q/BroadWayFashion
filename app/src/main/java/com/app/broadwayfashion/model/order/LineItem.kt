package com.app.broadwayfashion.model.order


import com.google.gson.annotations.SerializedName

data class LineItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("meta_data")
    val metaData: List<MetaData?>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("parent_name")
    val parentName: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("product_id")
    val productId: Int?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("sku")
    val sku: String?,
    @SerializedName("subtotal")
    val subtotal: String?,
    @SerializedName("subtotal_tax")
    val subtotalTax: String?,
    @SerializedName("tax_class")
    val taxClass: String?,
    @SerializedName("taxes")
    val taxes: List<Taxe?>?,
    @SerializedName("total")
    val total: String?,
    @SerializedName("total_tax")
    val totalTax: String?,
    @SerializedName("variation_id")
    val variationId: Int?,

    var status:String,
    var orderId:Int,
):java.io.Serializable