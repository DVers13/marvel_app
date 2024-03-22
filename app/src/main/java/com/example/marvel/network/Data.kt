package com.example.marvel.network

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("results")
    val results: List<Result>
)