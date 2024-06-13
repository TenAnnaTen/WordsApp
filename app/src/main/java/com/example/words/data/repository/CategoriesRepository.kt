package com.example.words.data.repository

import com.example.words.data.RetrofitInstance
import com.example.words.data.model.Categories
import com.example.words.data.model.User
import retrofit2.Response

class CategoriesRepository {

    suspend fun addCategories(categories: Categories, userId: Int) {
        return RetrofitInstance.categoriesService.addCategory(categories, userId)
    }

    suspend fun getMyCategories(userId: Int): MutableList<Categories> {
        return RetrofitInstance.categoriesService.getMyCategories(userId)
    }

    suspend fun getUsersOfCategories(categoryId: Int): MutableList<User> {
        return RetrofitInstance.categoriesService.getUsersOfCategories(categoryId)
    }

//    suspend fun getPublicCategories(userId: Int): MutableList<Categories> {
//        return RetrofitInstance.categoriesService.getPublicCategories(userId)
//    }

    suspend fun delCategory(categoryId: Int) {
        return RetrofitInstance.categoriesService.delCategory(categoryId)
    }

    suspend fun updateNameCategory(categoryId: Int, categories: Categories): Response<Categories> {
        return RetrofitInstance.categoriesService.updateNameCategory(categories, categoryId)
    }
}