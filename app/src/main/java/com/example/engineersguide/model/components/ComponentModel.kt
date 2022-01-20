package com.example.engineersguide.model.components


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class ComponentModel(
    @SerializedName("component_imageUrl")
    var componentImageUrl: String,
    @SerializedName("component_name")
    var componentName: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("equations")
    val equations: String,
    @SerializedName("functionality")
    var functionality: String,
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("isFavorite")
    val isFavorite: Boolean,
    @SerializedName("source1")
    var source1: String,
    @SerializedName("source2")
    var source2: String,
    @SerializedName("source3")
    var source3: String,
    @SerializedName("username")
    var username: String
)