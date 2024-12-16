package com.example.newsapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.newsapp.data.entity.Article

@Composable
fun NewsList(articles: List<Article>, onArticleClick: (Article) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(articles) { article ->
            NewsItem(article = article, onClick = { onArticleClick(article) })
        }
    }
}