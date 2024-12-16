package com.example.newsapp.ui.screens.currentScreen

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsDetailsScreen(viewModel: NewsDetailsScreenViewModel) {
    val article = viewModel.article.collectAsState().value
    if (article != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "News Details",
                            style = MaterialTheme.typography.h6.copy(color = Color.White),
                            modifier = Modifier.padding(top = 30.dp)
                        )
                    },
                    backgroundColor = Color(0xFF005195),
                    elevation = 8.dp,
                    modifier = Modifier.height(80.dp)
                )
            },
            contentColor = Color(0xFF121212),
            modifier = Modifier.background(Color(0xFFF4F6FA))
        ) {
            LazyColumn(modifier = Modifier.padding(it)) {
                item {
                    if (!article.urlToImage.isNullOrEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(article.urlToImage),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .clip(MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.h5.copy(color = Color.Black),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // URL of the article (clickable)
                    val context = LocalContext.current
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = "Read the full article here:",
                            style = MaterialTheme.typography.body1.copy(color = Color.Black)
                        )
                        Text(
                            text = article.url,
                            style = MaterialTheme.typography.body1.copy(color = Color(0xFF005195)),
                            modifier = Modifier
                                .clickable {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                                    context.startActivity(intent) // for open in browser
                                }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        elevation = 4.dp,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = article.description ?: "No description available",
                            style = MaterialTheme.typography.body2.copy(color = Color.Black),
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    article.source?.name?.let {
                        Text(
                            text = "Source: $it",
                            style = MaterialTheme.typography.body2.copy(color = Color.Black),
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    article.author?.let {
                        Text(
                            text = "Author: $it",
                            style = MaterialTheme.typography.body2.copy(color = Color.Black),
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    article.publishedAt?.let {
                        val formattedDate = formatDate(it)
                        Text(
                            text = "Published on: $formattedDate",
                            style = MaterialTheme.typography.body2.copy(color = Color.Black),
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    } else {
        Text(
            text = "Error: Article not found",
            style = MaterialTheme.typography.body1.copy(color = Color.Red),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(date: String): String {
    return try {
        val instant = Instant.parse(date)
        // Set locale to English explicitly for the formatter
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")
            .withZone(ZoneId.systemDefault())
            .withLocale(Locale.ENGLISH) // Force English locale for month name
        formatter.format(instant)
    } catch (e: Exception) {
        "Unknown date"
    }
}
