package com.example.newsapp.ui.screens.categoriesScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ui.components.CategoryCard

@Composable
fun CategoriesScreen(
    viewModel: CategoriesScreenViewModel = viewModel(),
    onCategoryClick: (String) -> Unit
) {
    val categories by viewModel.categories.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Categories",
                        style = MaterialTheme.typography.h6.copy(color = Color.White),
                        modifier = Modifier.padding(top = 30.dp)
                    )
                },
                backgroundColor = Color(0xFF005195),
                elevation = 8.dp,
                modifier = Modifier.height(80.dp)
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(categories) { category ->
                CategoryCard(category = category, onClick = { onCategoryClick(category.name) })
            }
        }
    }
}