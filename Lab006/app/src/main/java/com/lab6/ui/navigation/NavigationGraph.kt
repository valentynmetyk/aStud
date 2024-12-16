package com.lab6.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lab6.ui.screens.current.WeatherScreen


const val SCREEN_WEATHER_SCREEN = "weatherScreen"

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SCREEN_WEATHER_SCREEN
    ) {

        composable(
            route = SCREEN_WEATHER_SCREEN
        ) {
            WeatherScreen()
        }
    }
}