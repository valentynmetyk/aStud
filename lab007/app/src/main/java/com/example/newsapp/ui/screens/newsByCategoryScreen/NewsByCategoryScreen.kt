package com.example.newsapp.ui.screens.newsByCategoryScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.newsapp.data.entity.Article
import com.example.newsapp.ui.components.NewsList


@Composable
fun NewsByCategoryScreen(
    categoryName: String,
    viewModel: NewsByCategoryScreenViewModel,
    onArticleClick: (Article) -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val articles by viewModel.articles.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(categoryName) {
        viewModel.loadNewsByCategory(categoryName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "News: $categoryName",
                        style = MaterialTheme.typography.h6.copy(color = Color.White),
                        modifier = Modifier.padding(top = 30.dp)
                    )
                },
                backgroundColor = Color(0xFF005195),
                elevation = 8.dp,
                modifier = Modifier.height(80.dp)
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                errorMessage != null -> Text(
                    text = errorMessage.orEmpty(),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.Center)
                )
                articles.isNotEmpty() -> NewsList(
                    articles = articles,
                    onArticleClick = onArticleClick
                )
                else -> Text(
                    text = "No news available",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
