package com.example.marvel.ui.utils

import androidx.compose.ui.graphics.Color

fun convertUrl(url: String, extension: String): String =
    "${url.replace("http", "https")}.$extension"

val colors = listOf(
    Color.Red,
    Color.Blue,
    Color.Green,
    Color.Yellow,
    Color.Magenta
)
