package com.app.broadwayfashion.model.product


import com.google.gson.annotations.SerializedName

data class YoastHeadJson(
    @SerializedName("article_modified_time")
    val articleModifiedTime: String?,
    @SerializedName("canonical")
    val canonical: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("og_description")
    val ogDescription: String?,
    @SerializedName("og_image")
    val ogImage: List<OgImage?>?,
    @SerializedName("og_locale")
    val ogLocale: String?,
    @SerializedName("og_site_name")
    val ogSiteName: String?,
    @SerializedName("og_title")
    val ogTitle: String?,
    @SerializedName("og_type")
    val ogType: String?,
    @SerializedName("og_url")
    val ogUrl: String?,
    @SerializedName("robots")
    val robots: Robots?,
    @SerializedName("schema")
    val schema: Schema?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("twitter_card")
    val twitterCard: String?,
    @SerializedName("twitter_misc")
    val twitterMisc: TwitterMisc?
)