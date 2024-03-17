package com.example.marvel

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marvel.ui.theme.MarvelTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
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
                    HeroesPageScreen(navController)
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
                        val hero = HeroList.heroList[id]
                        HeroDetailScreen(hero = hero){
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainActivity()
}