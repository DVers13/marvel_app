package com.example.marvel.ui.CharactersList

import com.example.marvel.domain.model.Character

data class MarvelListState(
    val isLoading : Boolean = false,
    val characterList: List<Character> = emptyList(),
    val error : String = ""
)
