package com.example.marvel.network

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail
)
