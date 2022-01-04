package com.example.engineersguide.model.components


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class ComponentApi(
    @SerializedName("component_imageUrl")
    var componentImageUrl: String,
    @SerializedName("component_name")
    var componentTitle: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("functionality")
    var functionality: String,
    @SerializedName("equations")
    var equations: String,
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("source1")
    var source1: String,
    @SerializedName("source2")
    var source2: String,
    @SerializedName("source3")
    var source3: String,
    @SerializedName("isFavorite")
    var isFavorite: Boolean
)