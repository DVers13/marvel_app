package com.example.marvel.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marvel.ui.DETAIL_ARGUMENT_KEY
import com.example.marvel.ui.HeroDetailScreen
import com.example.marvel.ui.HeroesPageScreen
import com.example.marvel.ui.Screen
import com.example.marvel.network.MarvelApi
import com.example.marvel.network.Result
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppNavigator(navController: NavHostController) {
    var heroes by rememberSaveable { mutableStateOf<List<Result>>(emptyList()) }
    var loading by rememberSaveable { mutableStateOf(true) }
    var error by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        try {
            heroes = MarvelApi.getCharacters(10)
            loading = false
        } catch (e: Exception) {
            error = true
            loading = false
            Log.e("AppNavigator", "Error fetching data", e)
        }
    }

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (error) {
        Box(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Failed to load data. Please check your internet connection.")
        }
    } else {
        heroes = heroes.filterNot { it.thumbnail.path == "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available" }
        NavHost(navController, startDestination = Screen.HeroesPage.route) {
            composable(Screen.HeroesPage.route) {
                Log.d("Args", Screen.HeroesPage.route)
                HeroesPageScreen(navController, heroes)
            }
            composable(
                route = Screen.HeroDetail.route,
                arguments = listOf(navArgument(DETAIL_ARGUMENT_KEY) {
                    type = NavType.IntType
                })
            ) {
                val id = it.arguments?.getInt("index")
                if (id != null) {
                    val hero = heroes[id]
                    HeroDetailScreen(hero = hero) {
                        navController.navigateUp()
                    }
                } else {
                    Log.e("AppNavigator", "Invalid hero id")
                }
            }
        }
    }
}