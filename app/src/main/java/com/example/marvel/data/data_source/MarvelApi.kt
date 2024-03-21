package com.example.marvel.data.data_source

import com.example.marvel.HeroResponse
import com.example.marvel.data.data_source.dto.CharactersDTO
import com.example.marvel.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {
    @GET("/v1/public/characters")
    suspend fun getAllCharacters(
        @Query("apikey") apiKey: String = Constants.API_KEY,
        @Query("ts") ts: String = Constants.timeStamp,
        @Query("hash") hash: String = Constants.hash(),
        @Query("offset") offset: String,
    ): CharactersDTO

    @GET("characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") characterId: Int,
    ): Response<HeroResponse>
}