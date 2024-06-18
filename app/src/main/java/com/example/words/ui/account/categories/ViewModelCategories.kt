package com.example.words.ui.account.categories

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import com.example.words.ui.account.words.ScreenWordsUiState
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

    private val _wordUiState = MutableStateFlow(ScreenWordsUiState())
    val wordUiState = _wordUiState.asStateFlow()

    var enter by mutableStateOf("")
    var openAlertDialog by mutableStateOf(false)
//    var selectedTabIndex by mutableStateOf(false)

    fun addCategory(categories: Categories, context: Context) {
        viewModelScope.launch {
            try {
                categoriesRepository.addCategories(
                    categories,
                    accountStorage.getUserId()
                )
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
                Toast.makeText(context, "Ошибка сети", Toast.LENGTH_LONG).show()
            }
            getMyCategories(context)
        }
    }

    fun getMyCategories(context: Context) {
        viewModelScope.launch {
            try {
                val response = categoriesRepository.getMyCategories(accountStorage.getUserId())
                categoriesListResponse = response
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
                Toast.makeText(context, "Ошибка сети", Toast.LENGTH_LONG).show()
            }
            _uiState.value = CategoriesUiState(list = categoriesListResponse)
        }
    }

    fun getUsersOfCategories(categoryId: Int, context: Context) {
        viewModelScope.launch {
            try {
                val response = categoriesRepository.getUsersOfCategories(categoryId)
                usersListResponse = response
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
                Toast.makeText(context, "Ошибка сети", Toast.LENGTH_LONG).show()
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

    fun delCategory(categoryId: Int, context: Context) {
        viewModelScope.launch {
            try {
                categoriesRepository.delCategory(categoryId)
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
            }
            getMyCategories(context)
        }
    }

    fun updateCategoryName(categoryId: Int, category: Categories, context: Context) {
        viewModelScope.launch {
            try {
                _wordUiState.value = ScreenWordsUiState(category.category_name!!)
                val response = categoriesRepository.updateNameCategory(categoryId, category)
                if (response.isSuccessful) {
                    Log.d("MyLog", "Category updated successfully")
                } else {
                    Log.d("MyLog", "Error updating category: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Ошибка сети", Toast.LENGTH_LONG).show()
            }
        }
    }

//    fun getNameOwner(userId: Int){
//        getUserById(userId)
//    }

    fun updateEnter(enter: String) {
        this.enter = enter
        Log.d("MyLog", "enter: ${this.enter}")
    }

//    fun switchList() {
//        selectedTabIndex = !selectedTabIndex
//    }

    fun openDialog() {
        openAlertDialog = !openAlertDialog
    }
}