package com.example.marvel

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marvel.ui.CharactersList.CharactersViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainWindow(viewModel: CharactersViewModel) {
    val heroItems by viewModel.marvelValue.collectAsState()
    Log.d("HHH", "$heroItems")
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
//            HeroesPageScreen(navController)
        }
        composable(
            route = Screen.HeroDetail.route,
            arguments = listOf(navArgument(DETAIL_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {
            Log.d("Args", it.arguments?.getInt(DETAIL_ARGUMENT_KEY).toString())
            val id = it.arguments?.getInt("index")
            if (id != null) {
//                val hero = HeroList.heroList[id]
//                HeroDetailScreen(hero = hero){
//                    navController.navigateUp()
//                }
            } else {
                Log.e("MainActivity", "Invalid hero id")
            }
        }
    }
}