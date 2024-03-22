package com.example.marvel

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marvel.network.MarvelApi
import com.example.marvel.network.Result
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            var heroes by remember { mutableStateOf<List<Result>>(emptyList()) }
            var loading by remember { mutableStateOf(true) }
            var error by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = true) {
                try {
                    heroes = MarvelApi.getCharacters(5)
                    loading = false
                } catch (e: Exception) {
                    error = true
                    loading = false
                    Log.e("MainActivity", "Error fetching data", e)
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
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent
                    )
                    systemUiController.setNavigationBarColor(
                        color = Color.Transparent
                    )
                }
                val navController = rememberNavController()
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
                            Log.e("MainActivity", "Invalid hero id")
                        }
                    }
                }
            }
        }
    }
}
