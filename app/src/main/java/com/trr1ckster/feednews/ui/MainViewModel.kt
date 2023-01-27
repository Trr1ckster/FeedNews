package com.trr1ckster.feednews.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trr1ckster.feednews.data.model.Article
import com.trr1ckster.feednews.data.model.NewsResponse
import com.trr1ckster.feednews.data.repository.ArticleRepository
import com.trr1ckster.feednews.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _newsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val newsLiveData: LiveData<Resource<NewsResponse>> = _newsLiveData

    private val _everythingNewsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val everythingNewsLiveData: LiveData<Resource<NewsResponse>> = _everythingNewsLiveData

    init {
        getHeadlinesNews()
    }

    private fun getHeadlinesNews() = viewModelScope.launch {
        _newsLiveData.postValue(Resource.Loading())
        val response = articleRepository.getHeadlines()
        _newsLiveData.postValue(handleGetHeadlinesNews(response))
    }

    private fun handleGetHeadlinesNews(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getEverythingNews(searchQuery: String) = viewModelScope.launch {
        _everythingNewsLiveData.postValue(Resource.Loading())
        val response = articleRepository.getEverything(searchQuery)
        _everythingNewsLiveData.postValue(handleGetEverythingNews(response))

    }

    private fun handleGetEverythingNews(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())

    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        articleRepository.insertArticle(article)
    }

    fun getSavedArticles() = articleRepository.getSavedArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        articleRepository.deleteArticle(article)
    }

}