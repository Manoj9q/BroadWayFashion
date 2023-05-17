package com.app.broadwayfashion.model.product


import com.google.gson.annotations.SerializedName

data class Graph(
    @SerializedName("breadcrumb")
    val breadcrumb: Breadcrumb?,
    @SerializedName("dateModified")
    val dateModified: String?,
    @SerializedName("datePublished")
    val datePublished: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("@id")
    val id: String?,
    @SerializedName("inLanguage")
    val inLanguage: String?,
    @SerializedName("isPartOf")
    val isPartOf: IsPartOf?,
    @SerializedName("itemListElement")
    val itemListElement: List<ItemElement?>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("potentialAction")
    val potentialAction: List<PotentialAction?>?,
    @SerializedName("@type")
    val type: String?,
    @SerializedName("url")
    val url: String?
)