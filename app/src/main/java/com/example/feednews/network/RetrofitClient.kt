package com.example.feednews.network

import com.example.feednews.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val newsApi: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}

//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(GsonConverterFactory.create())
//    .baseUrl(BASE_URL)
//    .build()
//
//object NewsApi {
//    val retrofitService: ApiService by lazy {
//        retrofit.create(ApiService::class.java)
//    }
//}


