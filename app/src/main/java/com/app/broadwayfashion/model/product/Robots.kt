package com.app.broadwayfashion.model.product


import com.google.gson.annotations.SerializedName

data class Robots(
    @SerializedName("follow")
    val follow: String?,
    @SerializedName("index")
    val index: String?,
    @SerializedName("max-image-preview")
    val maxImagePreview: String?,
    @SerializedName("max-snippet")
    val maxSnippet: String?,
    @SerializedName("max-video-preview")
    val maxVideoPreview: String?
)