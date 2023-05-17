package com.app.broadwayfashion.model.customer


import com.google.gson.annotations.SerializedName

data class CustomerDataResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("billing")
    val billing: Billing?,
    @SerializedName("date_created")
    val dateCreated: String?,
    @SerializedName("date_created_gmt")
    val dateCreatedGmt: String?,
    @SerializedName("date_modified")
    val dateModified: Any?,
    @SerializedName("date_modified_gmt")
    val dateModifiedGmt: Any?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_paying_customer")
    val isPayingCustomer: Boolean?,
    @SerializedName("last_name")
    val lastName: String?,
/*    @SerializedName("_links")
    val links: Links?,
    @SerializedName("meta_data")
    val metaData: List<MetaData?>?,*/
    @SerializedName("role")
    val role: String?,
    @SerializedName("shipping")
    val shipping: Shipping?,
    @SerializedName("username")
    val username: String?
)