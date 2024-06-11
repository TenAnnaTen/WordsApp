package com.example.words.data.repository

import com.example.words.data.RetrofitInstance
import com.example.words.data.model.User
import retrofit2.Response

class AccountRepository {

    suspend fun signIn(user: User): Response<User> {
        return RetrofitInstance.accountService.signIn(user)
    }

    suspend fun signUp(user: User): Response<User> {
        return RetrofitInstance.accountService.signUp(user)
    }

    suspend fun getUserById(userId: Int): Response<User> {
        return RetrofitInstance.accountService.getUserById(userId)
    }

}