package com.example.marvel.data.repository
import com.example.marvel.data.data_source.MarvelApi
import com.example.marvel.data.data_source.dto.CharactersDTO
import com.example.marvel.domain.repository.MarvelRepository
import  javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val api:MarvelApi
):MarvelRepository{
    override suspend fun getAllCharacter(offset: Int): CharactersDTO {
        return api.getAllCharacters(offset = offset.toString())
    }
}