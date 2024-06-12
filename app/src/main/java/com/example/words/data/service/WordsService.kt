package com.example.words.data.service

import com.example.words.data.model.Word
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface WordsService {

    @GET("{category_id}/words")
    suspend fun getWordsOfCategory(@Path("category_id") categoryId: Int): MutableList<Word>

    @POST("word/{category_id}")
    suspend fun addWord(@Body request: Word, @Path("category_id") categoryId: Int) : Response<Word>

    @PUT("word/{word_id}")
    suspend fun updateWord(@Body request: Word, @Path("word_id") wordId: Int)
}