package com.example.feednews

import com.example.feednews.network.RetrofitInstance

class ArticleRepository {

    suspend fun getHeadlines(countryCode: String) =
        RetrofitInstance.newsApi.getTopHeadlines(countryCode)
}