package com.example.words.ui.account.settings

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.words.data.model.User
import com.example.words.data.model.colorState
import com.example.words.data.repository.AccountRepository
import com.example.words.data.storage.AccountStorage
import com.example.words.ui.navigation.ScreenRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScreenSettingsViewModel: ViewModel() {

    val alertDialogField = mutableStateOf(false)
    val alertDialogExit = mutableStateOf(false)
    val alertDialogDelete = mutableStateOf(false)

    val selected = mutableStateOf(true)

    private var _uiStateColor = MutableStateFlow(colorState(dark = false))
    val uiStateColor = _uiStateColor.asStateFlow()

    val accountRepository = AccountRepository()
    val accountStorage = AccountStorage()

    fun updateSelected(selected: Boolean) {
        this.selected.value = selected
    }

    fun updateColor() {
        val currentTheme = _uiStateColor.value
        _uiStateColor.value = currentTheme.copy(dark = !currentTheme.dark)
    }

    fun openAlert(
        alert: Int
    ) {
        when (alert) {
            1 -> alertDialogField.value = !alertDialogField.value
            3 -> alertDialogExit.value = !alertDialogExit.value
            2 -> alertDialogDelete.value = !alertDialogDelete.value
        }
    }

//    fun updateUser(name: String) {
//        viewModelScope.launch {
//            try {
//                val response = accountRepository.getUserById(accountStorage.getUserId())
//                Log.d("MyLog", response.body().toString())
//                if (response.isSuccessful) {
//                    Log.d("MyLog", response.body()?.username.toString())
//                    Log.d("MyLog", response.body()?.password.toString())
//                    accountRepository.updateUser(
//                        User(
//                            username = response.body()?.username,
//                            name = name,
//                            password = response.body()?.password
//                        ),
//                        accountStorage.getUserId()
//                    )
//                }
//            } catch (e: Exception) {
//                Log.d("MyLog", e.toString())
//            }
//        }
//    }

    fun deleteAccount(navController: NavHostController, context: Context) {
        viewModelScope.launch {
            try {
                accountRepository.deleteUser(accountStorage.getUserId())
                accountStorage.saveUserId(0)
                navController.navigate(ScreenRoute.ScreenWelcome.name)
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
                Toast.makeText(context, "Ошибка сети", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun exit(navController: NavHostController) {
        try {
            accountStorage.saveUserId(0)
            navController.navigate(ScreenRoute.ScreenWelcome.name)
        } catch (e: Exception) {
            Log.d("MyLog", e.toString())
        }
    }
}