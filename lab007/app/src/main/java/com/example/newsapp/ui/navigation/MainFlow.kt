package com.example.newsapp.ui.navigation

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newsapp.ui.components.Indicator
import com.example.newsapp.ui.screens.categoriesScreen.CategoriesScreen
import com.example.newsapp.ui.screens.mainScreen.MainScreen
import com.example.newsapp.ui.screens.searchScreen.SearchScreen
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson

@Composable
fun MainFlow(navController: NavHostController) {
    val pagerState = rememberPagerState(initialPage = 0)
    val screenList = listOf(SCREEN_MAIN_SCREEN, SCREEN_CATEGORIES_SCREEN, "searchScreen")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF005195))
    ) {
        HorizontalPager(
            state = pagerState,
            count = screenList.size,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (screenList[page]) {
                SCREEN_MAIN_SCREEN -> {
                    MainScreen(
                        onArticleClick = { article ->
                            val articleJson = Uri.encode(Gson().toJson(article))
                            navController.navigate("newsDetailsScreen/$articleJson")
                        }
                    )
                }
                SCREEN_CATEGORIES_SCREEN -> {
                    CategoriesScreen(
                        onCategoryClick = { category ->
                            val categoryName = category.toString()
                            navController.navigate("newsByCategoryScreen/${Uri.encode(categoryName)}")
                        }
                    )
                }
                SCREEN_SEARCH_SCREEN -> {
                    SearchScreen(
                        onArticleClick = { article ->
                            val articleJson = Uri.encode(Gson().toJson(article))
                            navController.navigate("newsDetailsScreen/$articleJson")
                        }
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 30.dp)
                .background(Color(0xFF005195))
        ) {
            screenList.forEachIndexed { index, _ ->
                val isSelected = pagerState.currentPage == index
                Indicator(isSelected = isSelected)
            }
        }
    }
}