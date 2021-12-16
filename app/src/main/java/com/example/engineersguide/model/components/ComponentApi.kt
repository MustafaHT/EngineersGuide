package com.example.engineersguide.model.components


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class ComponentApi(
    @SerializedName("component_imageUrl")
    val componentImageUrl: String,
    @SerializedName("component_name")
    val componentName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("equations")
    val equations: String,
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("isFavorite")
    val isFavorite: Boolean,
    @SerializedName("source")
    val source: String
)