package com.example.words.ui.account.categories

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.words.data.model.Categories
import com.example.words.data.model.User
import com.example.words.data.model.idState
import com.example.words.data.repository.AccountRepository
import com.example.words.data.repository.CategoriesRepository
import com.example.words.data.storage.AccountStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModelCategories : ViewModel() {

    val categoriesRepository = CategoriesRepository()
    val accountStorage = AccountStorage()
//    val accountRepository = AccountRepository()

    var categoriesListResponse: MutableList<Categories> by mutableStateOf(mutableListOf())
    var usersListResponse: MutableList<User> by mutableStateOf(mutableListOf())

//    private val _state = MutableStateFlow(User())
//    val state = _state.asStateFlow()

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState = _uiState.asStateFlow()

    var enter by mutableStateOf("")
    var openAlertDialog by mutableStateOf(false)
//    var selectedTabIndex by mutableStateOf(false)

    fun addCategory(categories: Categories) {
        viewModelScope.launch {
            try {
                categoriesRepository.addCategories(
                    categories,
                    accountStorage.getUserId()
                )
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
                Log.d("MyLog", accountStorage.getUserId().toString())
            }
            getMyCategories()
        }
    }

    fun getMyCategories() {
        viewModelScope.launch {
            try {
                val response = categoriesRepository.getMyCategories(accountStorage.getUserId())
                categoriesListResponse = response
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
                Log.d("MyLog", accountStorage.getUserId().toString())
            }
            _uiState.value = CategoriesUiState(list = categoriesListResponse)
        }
    }

    fun getUsersOfCategories(categoryId: Int) {
        viewModelScope.launch {
            try {
                val response = categoriesRepository.getUsersOfCategories(categoryId)
                usersListResponse = response
                Log.d("MyLog", usersListResponse[0].toString())
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
            }
        }
    }

//    fun getPublicCategories() {
//        viewModelScope.launch {
//            try {
//                categoriesListResponse = categoriesRepository.getPublicCategories(accountStorage.getUserId())
////                Log.d("MyLog", categoriesRepository.getPublicCategories(accountStorage.getUserId()).toString())
//            } catch (e: Exception) {
//                Log.d("MyLog", e.toString())
//            }
//        }
//    }

//    fun getUserById(userId: Int){
//        viewModelScope.launch {
//            try {
//                val response = accountRepository.getUserById(userId)
//                if (response.isSuccessful) {
//                    val ownerName = response.body()?.name
//                    _state.update { it.copy(name = ownerName) }
//                    Log.d("MyLog", _state.value.name + "VM")
//                } else {
//                    Log.d("MyLog", "Error: ${response.errorBody()}")
//                }
//            } catch (e: Exception) {
//                Log.d("MyLog", e.toString())
//            }
//        }
//    }

    fun delCategory(categoryId: Int) {
        viewModelScope.launch {
            try {
                categoriesRepository.delCategory(categoryId)
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
            }
            getMyCategories()
        }
    }

//    fun getNameOwner(userId: Int){
//        getUserById(userId)
//    }

    fun updateEnter(enter: String) {
        this.enter = enter
    }

//    fun switchList() {
//        selectedTabIndex = !selectedTabIndex
//    }

    fun openDialog() {
        openAlertDialog = !openAlertDialog
    }
}