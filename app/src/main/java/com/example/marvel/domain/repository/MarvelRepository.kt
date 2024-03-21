package com.example.marvel.domain.repository

import com.example.marvel.data.data_source.dto.CharactersDTO

interface MarvelRepository {
    suspend fun getAllCharacter(offset:Int):CharactersDTO
}