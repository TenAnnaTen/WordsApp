package com.example.words.data.service

import com.example.words.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountService {
    @POST("auth/signin")
    suspend fun signIn(@Body request: User): Response<User>

    @POST("auth/signup")
    suspend fun signUp(@Body request: User): Response<User>

    @GET("user/{user_id}")
    suspend fun getUserById(@Path("user_id") userId: Int): Response<User>

    @PUT("user/{user_id}")
    suspend fun updateUser(@Body request: User, @Path("user_id") userId: Int)

    @DELETE("user/{user_id}")
    suspend fun deleteAccount(@Path("user_id") userId: Int)
}