package com.example.feednews.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @Embedded(prefix = "id_source")
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)