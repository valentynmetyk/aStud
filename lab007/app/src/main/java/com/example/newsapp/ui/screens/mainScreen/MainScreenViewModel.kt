package com.example.newsapp.ui.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.API_KEY
import com.example.newsapp.data.ServerApi
import com.example.newsapp.data.entity.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(private val serverApi: ServerApi) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchNews(category: String = "general") {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = serverApi.getNews(category, API_KEY)
                if (response.isSuccessful) {
                    _articles.value = response.body()?.articles ?: emptyList()
                } else {
                    _errorMessage.value = "Error: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch news: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}