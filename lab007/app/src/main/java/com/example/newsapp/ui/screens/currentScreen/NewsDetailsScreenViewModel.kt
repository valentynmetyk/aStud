package com.example.newsapp.ui.screens.currentScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.entity.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsDetailsScreenViewModel : ViewModel() {
    private val _article = MutableStateFlow<Article?>(null)
    val article: StateFlow<Article?> get() = _article

    fun loadArticle(article: Article) {
        viewModelScope.launch {
            _article.value = article
        }
    }
}