package com.trr1ckster.feednews.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.trr1ckster.feednews.data.model.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)
}