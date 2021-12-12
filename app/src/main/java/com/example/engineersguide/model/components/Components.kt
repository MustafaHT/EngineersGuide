package com.example.engineersguide.model.components


import com.google.gson.annotations.SerializedName

data class Components(
    @SerializedName("component_imageUrl")
    val componentImageUrl: String,
    @SerializedName("component_name")
    val componentName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("equations")
    val equations: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("isFavorite")
    val isFavorite: Boolean,
    @SerializedName("source")
    val source: String
)