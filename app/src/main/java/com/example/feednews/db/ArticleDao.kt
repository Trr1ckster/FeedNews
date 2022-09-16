package com.example.feednews.db

import androidx.room.*
import com.example.feednews.model.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAllArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)
}