package com.trr1ckster.feednews.data.repository

import com.trr1ckster.feednews.data.db.ArticleDatabase
import com.trr1ckster.feednews.data.model.Article
import com.trr1ckster.feednews.data.network.RetrofitInstance

class ArticleRepository(private val database: ArticleDatabase){

    suspend fun getHeadlines() =
        RetrofitInstance.newsApi.getTopHeadlines()

    suspend fun getEverything(searchQuery: String) =
        RetrofitInstance.newsApi.getEverything(searchQuery)

    suspend fun insertArticle(article: Article) = database.getArticleDao().insertArticle(article)

    suspend fun deleteArticle(article: Article) = database.getArticleDao().deleteArticle(article)

    fun getSavedArticles() = database.getArticleDao().getAllArticles()

}