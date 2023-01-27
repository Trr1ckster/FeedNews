package com.trr1ckster.feednews.ui


import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trr1ckster.feednews.data.db.ArticleDatabase
import com.trr1ckster.feednews.data.repository.ArticleRepository

class NewsViewModelFactory(private val articleRepository: ArticleRepository) : ViewModelProvider.Factory {
@Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(articleRepository ) as T
    }
}

fun Fragment.factory() = NewsViewModelFactory(ArticleRepository(ArticleDatabase(requireContext())))