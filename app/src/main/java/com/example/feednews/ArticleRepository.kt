package com.example.feednews

import com.example.feednews.db.ArticleDatabase
import com.example.feednews.model.Article
import com.example.feednews.network.RetrofitInstance

class ArticleRepository(private val database: ArticleDatabase) {

    suspend fun getHeadlines(countryCode: String) =
        RetrofitInstance.newsApi.getTopHeadlines(countryCode)

    suspend fun getEverything(searchQuery: String) =
        RetrofitInstance.newsApi.getTopHeadlines(searchQuery)

    suspend fun insertArticle(article: Article) = database.getArticleDao().insertArticle(article)

    suspend fun deleteArticle(article: Article) = database.getArticleDao().deleteArticle(article)

    fun getSavedArticles() = database.getArticleDao().getAllArticles()

}