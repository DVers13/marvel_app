package com.example.marvel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.marvel.navigation.AppNavigator
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MarvelScreen(
    navController: NavHostController = rememberNavController(),
    systemUiController: SystemUiController = rememberSystemUiController()
) {
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent
        )
    }
        AppNavigator(navController)
}