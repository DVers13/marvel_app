package com.example.marvel.network

import com.google.gson.annotations.SerializedName

data class MarvelResponse(
    @SerializedName("data")
    val data: Data
)
