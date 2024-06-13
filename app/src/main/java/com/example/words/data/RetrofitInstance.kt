package com.example.words.data

import com.example.words.data.service.AccountService
import com.example.words.data.service.CategoriesService
import com.example.words.data.service.WordsService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8000/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val accountService: AccountService by lazy {
        retrofit.create(AccountService::class.java)
    }

    val categoriesService: CategoriesService by lazy {
        retrofit.create(CategoriesService::class.java)
    }
    val wordsService: WordsService by lazy {
        retrofit.create(WordsService::class.java)
    }
}