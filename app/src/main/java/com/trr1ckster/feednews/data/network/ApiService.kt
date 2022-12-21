package com.trr1ckster.feednews.data.network

import com.trr1ckster.feednews.data.model.NewsResponse
import com.trr1ckster.feednews.utils.Constants
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