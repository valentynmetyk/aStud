package com.example.newsapp.di

import com.example.newsapp.data.ServerApi
import com.example.newsapp.ui.screens.mainScreen.MainScreenViewModel
import com.example.newsapp.ui.screens.newsByCategoryScreen.NewsByCategoryScreenViewModel
import com.example.newsapp.ui.screens.searchScreen.SearchScreenViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.androidx.viewmodel.dsl.viewModel

private const val BASE_URL = "https://newsapi.org/"

val appModule = module {

    /**
     * Initializing of the Retrofit instance, which generate API schema from ServerApi interface
     */

    single<ServerApi> {
        val client = OkHttpClient() // unique client for server
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) // interceptor here just makes pretty logs of each request in the LogCat
        val clientBuilder: OkHttpClient.Builder = client.newBuilder().addInterceptor(interceptor)

        Retrofit.Builder()
            .baseUrl(BASE_URL)// base url set here
            .addConverterFactory(GsonConverterFactory.create()) // Gson converter factory converts pure json to your data classes
            .client(clientBuilder.build()) // add client here
            .build()
            .create(ServerApi::class.java)// creating schema of requests by retrofit
    }
    viewModel { MainScreenViewModel(get()) }
    viewModel { (api: ServerApi) -> NewsByCategoryScreenViewModel(api) }
    viewModel { SearchScreenViewModel(get()) }

}