package com.example.newsapp.ui.navigation

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.newsapp.data.ServerApi
import com.example.newsapp.data.entity.Article
import com.example.newsapp.ui.screens.currentScreen.NewsDetailsScreen
import com.example.newsapp.ui.screens.currentScreen.NewsDetailsScreenViewModel
import com.example.newsapp.ui.screens.newsByCategoryScreen.NewsByCategoryScreen
import com.example.newsapp.ui.screens.newsByCategoryScreen.NewsByCategoryScreenViewModel
import com.google.gson.Gson
import org.koin.core.parameter.parametersOf
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel


const val SCREEN_MAIN_SCREEN = "mainScreen"
const val SCREEN_CATEGORIES_SCREEN = "categoriesScreen"
const val SCREEN_SEARCH_SCREEN = "searchScreen"


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "mainFlow",
        modifier = modifier.fillMaxSize()
    ) {
        // Horizontal scroll between MainScreen and CategoriesScreen
        composable("mainFlow") {
            MainFlow(navController = navController)
        }
        composable(
            route = "newsDetailsScreen/{articleJson}",
            arguments = listOf(navArgument("articleJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val articleJson = backStackEntry.arguments?.getString("articleJson")
            val article = Gson().fromJson(articleJson, Article::class.java)
            val viewModel: NewsDetailsScreenViewModel = viewModel()

            LaunchedEffect(article) {
                viewModel.loadArticle(article)
            }
            NewsDetailsScreen(viewModel = viewModel)
        }

        composable(
            route = "newsByCategoryScreen/{categoryName}",
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""

            if (categoryName.isEmpty()) {
                Text("Invalid category")
                return@composable
            }

            val serverApi: ServerApi = get()
            val viewModel: NewsByCategoryScreenViewModel = getViewModel { parametersOf(serverApi) }

            LaunchedEffect(categoryName) {
                viewModel.loadNewsByCategory(categoryName)
            }

            NewsByCategoryScreen(
                categoryName = categoryName,
                viewModel = viewModel,
                onArticleClick = { article ->
                    val articleJson = Uri.encode(Gson().toJson(article))
                    navController.navigate("newsDetailsScreen/$articleJson")
                }
            )
        }
    }
}

