package com.example.words.data.service

import com.example.words.data.model.Categories
import com.example.words.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoriesService {
    @POST("category/{user_id}")
    suspend fun addCategory(@Body request: Categories, @Path("user_id") userId: Int)

    @GET("{user_id}/categories")
    suspend fun getMyCategories(@Path("user_id") userId: Int): MutableList<Categories>

    @GET("user/{category_id}/users")
    suspend fun getUsersOfCategories(@Path("category_id") categoryId: Int): MutableList<User>

//    @GET("{user_id}/general/categories")
//    suspend fun getPublicCategories(@Path("user_id") userId: Int): MutableList<Categories>

    @DELETE("category/{category_id}")
    suspend fun delCategory(@Path("category_id") categoryId: Int)

    @PUT("category/{category_id}")
    suspend fun updateNameCategory(@Body request: Categories, @Path("category_id") categoryId: Int): Response<Categories>
}