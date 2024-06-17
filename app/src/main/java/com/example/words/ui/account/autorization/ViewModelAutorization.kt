package com.example.words.ui.account.autorization

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.words.data.model.User
import com.example.words.data.model.idState
import com.example.words.data.repository.AccountRepository
import com.example.words.data.storage.AccountStorage
import com.example.words.ui.navigation.ScreenRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModelAutorization(
    private val navController: NavController
) : ViewModel() {

    private val _state = MutableStateFlow(idState())

    private val accountRepository = AccountRepository()
    private val accountStorage = AccountStorage()

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set

    var showPassword by mutableStateOf(false)
        private set
    fun signUp(user: User, context: Context) {
        viewModelScope.launch {
            try {
                val response = accountRepository.signUp(user)
                if (response.isSuccessful) {
                    val userId = response.body()?.id
                    navController.navigate(ScreenRoute.ScreenCategories.name)
                    _state.update { it.copy(id = userId) }
                    if (userId != null) accountStorage.saveUserId(userId)
                } else {
                    Toast.makeText(context, "Пользователь с таким логином уже существует", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
            }
        }
    }

    fun signIn(user: User, context: Context) {
        viewModelScope.launch {
            try {
                val response = accountRepository.signIn(user)
                if (response.isSuccessful) {
                    val userId = response.body()?.id
                    navController.navigate(ScreenRoute.ScreenCategories.name)
                    _state.update { it.copy(id = userId) }
                    if (userId != null) accountStorage.saveUserId(userId)
                } else {
                    Toast.makeText(context, "Такого пользователя не существует", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
            }
        }
    }

    fun updateEnterUsername(userName: String){
        username = userName
    }

    fun updateEnterPassword(pass: String){
        password = pass
    }
    fun updateEnterName(name: String){
        this.name = name
    }

    fun showPassword(show: Boolean) {
        showPassword = show
    }
}