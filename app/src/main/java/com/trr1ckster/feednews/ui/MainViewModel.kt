package com.trr1ckster.feednews.ui

import androidx.lifecycle.*
import com.trr1ckster.feednews.ArticleRepository
import com.trr1ckster.feednews.data.model.Article
import com.trr1ckster.feednews.data.model.NewsResponse
import com.trr1ckster.feednews.utils.Status
import kotlinx.coroutines.launch

class MainViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _newsLiveData: MutableLiveData<NewsResponse> = MutableLiveData()
    val newsLiveData: LiveData<NewsResponse> = _newsLiveData

    private val _everythingNewsLiveData: MutableLiveData<NewsResponse> = MutableLiveData()
    val everythingNewsLiveData: LiveData<NewsResponse> = _everythingNewsLiveData

    val status = MutableLiveData<Status>()

    init {
        getHeadlinesNews()
    }

    private fun getHeadlinesNews() = viewModelScope.launch {
        status.postValue(Status.LOADING)
        try {
            val response = articleRepository.getHeadlines()
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