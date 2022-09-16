package com.example.feednews.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feednews.ArticleRepository
import com.example.feednews.db.ArticleDao
import com.example.feednews.model.NewsResponse
import com.example.feednews.network.RetrofitInstance
import kotlinx.coroutines.launch

class MainViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _newsLiveData: MutableLiveData<NewsResponse> = MutableLiveData()
    val newsLiveData: LiveData<NewsResponse> = _newsLiveData

    init {
        getData("ru")
    }

     private fun getData(countryCode: String) = viewModelScope.launch {
        try {
            val response = articleRepository.getHeadlines(countryCode)
            _newsLiveData.postValue(response.body())
            Log.d("TAG", "${_newsLiveData.value}")

        } catch (e: Exception) {
            Log.d("TAG", "$e")
        }
    }

}