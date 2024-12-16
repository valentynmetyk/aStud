package com.example.newsapp.ui.screens.categoriesScreen

import androidx.lifecycle.ViewModel
import com.example.newsapp.data.entity.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoriesScreenViewModel : ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        _categories.value = listOf(
            Category(name = "Technology"),
            Category(name = "Sports"),
            Category(name = "Health"),
            Category(name = "Science"),
        )
    }
}