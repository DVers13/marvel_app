package com.example.marvel.network

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest


object MarvelApi {
    private const val BASE_URL = "https://gateway.marvel.com:443/v1/public/"
    private const val PUBLIC_KEY = "9d2c1ca0b8fbd53fa69875dde6baa731"
    private const val PRIVATE_KEY = "c0648e47a3b120b8d2e638ebb35a765142feb180"

    private fun generateHash(ts: String): String {
        val input = "$ts$PRIVATE_KEY$PUBLIC_KEY"
        val bytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: MarvelService = retrofit.create(MarvelService::class.java)

    suspend fun getCharacters(limit: Int): List<Result> {
        val ts = System.currentTimeMillis().toString()
        val hash = generateHash(ts)
        val response = service.getCharacters(limit, PUBLIC_KEY, ts, hash)
        return response.data.results
    }
}