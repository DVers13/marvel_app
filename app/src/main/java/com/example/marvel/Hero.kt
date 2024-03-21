package com.example.marvel

import kotlinx.serialization.Serializable

@Serializable
data class Hero(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)


@Serializable
data class Thumbnail(
    val path: String,
    val extension: String,
)
@Serializable
data class HeroAll(
    val results: List<Hero>
)

@Serializable
data class HeroResponse(
    val data: Data
)
@Serializable
data class Data(
    val limit: Int,
    val results: List<Hero>
)