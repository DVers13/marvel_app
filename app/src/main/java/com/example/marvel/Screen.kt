package com.example.marvel

const val DETAIL_ARGUMENT_KEY = "index"

sealed class Screen(val route: String) {
    object HeroesPage : Screen("heroes")
    object HeroDetail : Screen("heroDetail/{$DETAIL_ARGUMENT_KEY}"){
        fun passIndex(index: Int): String{
            return "heroDetail/$index"
        }
    }
}