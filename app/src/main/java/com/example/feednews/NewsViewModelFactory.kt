package com.example.feednews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feednews.db.ArticleDao
import com.example.feednews.ui.home.MainViewModel

class NewsViewModelFactory(private val articleRepository: ArticleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(articleRepository) as T
    }
}