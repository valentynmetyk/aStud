package com.example.newsapp.data.entity

data class Article(
    val author: String?,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val content: String?,
    val source: Source,
    val publishedAt: String?,
    val url: String
)