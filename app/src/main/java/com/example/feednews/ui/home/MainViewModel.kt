package com.example.feednews.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feednews.ArticleRepository
import com.example.feednews.model.Article
import com.example.feednews.model.NewsResponse
import com.example.feednews.utils.Status
import kotlinx.coroutines.launch

class MainViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _newsLiveData: MutableLiveData<NewsResponse> = MutableLiveData()
    val newsLiveData: LiveData<NewsResponse> = _newsLiveData

    private val _everythingNewsLiveData: MutableLiveData<NewsResponse> = MutableLiveData()
    val everythingNewsLiveData: LiveData<NewsResponse> = _newsLiveData

    val status = MutableLiveData<Status>()

    init {
        getHeadlinesNews("ru")
    }

    private fun getHeadlinesNews(countryCode: String) = viewModelScope.launch {
        status.postValue(Status.LOADING)
        try {
            val response = articleRepository.getHeadlines(countryCode)
            _newsLiveData.postValue(response.body())
            status.postValue(Status.SUCCESS)
        } catch (e: Exception) {
            status.postValue(Status.ERROR)
        }
    }

    fun getEverythingNews(searchQuery: String) = viewModelScope.launch {
        status.postValue(Status.LOADING)
        try {
            val response = articleRepository.getEverything(searchQuery)
            _everythingNewsLiveData.postValue(response.body())
            status.postValue(Status.SUCCESS)
        } catch (e: Exception) {
            status.postValue(Status.ERROR)
        }
    }


    fun saveArticle(article: Article) = viewModelScope.launch {
        articleRepository.insertArticle(article)
    }

    fun getSavedArticles() = articleRepository.getSavedArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        articleRepository.deleteArticle(article)
    }
}