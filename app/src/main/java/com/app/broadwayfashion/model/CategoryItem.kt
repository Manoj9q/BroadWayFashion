package com.app.broadwayfashion.model


import com.google.gson.annotations.SerializedName

data class CategoryItem(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("parent")
    val parent: Int? = 0,
    @SerializedName("slug")
    val slug: String? = "",
   /* @SerializedName("title")
    val title: String? = "",*/
    var isSelected: Boolean = false,
    var otherId: Int = 0

)