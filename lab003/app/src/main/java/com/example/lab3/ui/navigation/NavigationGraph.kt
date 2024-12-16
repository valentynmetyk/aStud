package com.example.lab3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab3.ui.screens.placeDetails.PlaceDetailsScreen
import com.example.lab3.ui.screens.placesList.PlacesListScreen

const val SCREEN_PLACE_DETAILS = "placeDetails"
const val SCREEN_PLACE_LIST = "placeList"

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SCREEN_PLACE_LIST
    ){
        composable(
            route = SCREEN_PLACE_LIST
        ){
            PlacesListScreen(
                onDetailsScreen = {id ->
                    navController.navigate("$SCREEN_PLACE_DETAILS/$id")
                }
            )
        }
        composable(
            route = "$SCREEN_PLACE_DETAILS/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.Companion.IntType
                nullable = false
            })
        ) { backStack ->
            val id = backStack.arguments?.getInt("id") ?: -1
            PlaceDetailsScreen(
                id = id
            )

        }

    }
}