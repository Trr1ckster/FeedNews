package com.example.feednews.network

import android.provider.SyncStateContract
import com.example.feednews.model.Article
import com.example.feednews.model.NewsResponse
import com.example.feednews.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "ru",
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") searchQuery: String,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>
}