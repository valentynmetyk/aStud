package com.example.newsapp.ui.screens.searchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.newsapp.data.entity.Article
import com.example.newsapp.ui.components.NewsList
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = koinViewModel(),
    onArticleClick: (Article) -> Unit
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val articles by viewModel.articles.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(

                backgroundColor = Color(0xFF005195),
                elevation = 8.dp,
                modifier = Modifier.height(120.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = searchQuery.text,
                        onValueChange = { query -> searchQuery = TextFieldValue(query) },
                        placeholder = {
                            Text("Search news...", color = Color.White.copy(alpha = 0.6f))
                        },
                        textStyle = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Transparent),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            viewModel.searchNews(searchQuery.text)
                        })
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { viewModel.searchNews(searchQuery.text) },
                        modifier = Modifier.height(50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF302e7c))
                    ) {
                        Text("Search", color = Color.White)
                    }

                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center),
                        color = Color(0xFF005195)
                    )
                }
                errorMessage != null -> Text(
                    text = errorMessage ?: "",
                    style = MaterialTheme.typography.body2.copy(color = Color.Red),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
                else -> NewsList(articles, onArticleClick)
            }
        }
    }
}