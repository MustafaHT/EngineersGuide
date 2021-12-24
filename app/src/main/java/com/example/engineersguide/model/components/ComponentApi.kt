package com.example.engineersguide.model.components


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class ComponentApi(
    @SerializedName("component_imageUrl")
    val componentImageUrl: String,
    @SerializedName("component_name")
    val componentTitle: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("functionality")
    val functionality: String,
    @SerializedName("equations")
    val equations: String,
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("source1")
    val source1: String,
    @SerializedName("source2")
    val source2: String,
    @SerializedName("source3")
    val source3: String,
    @SerializedName("isFavorite")
    val isFavorite: Boolean
)