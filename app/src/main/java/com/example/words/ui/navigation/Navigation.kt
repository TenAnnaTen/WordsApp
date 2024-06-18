package com.example.words.ui.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.words.ui.account.autorization.SignIn
import com.example.words.ui.account.autorization.SignUp
import com.example.words.ui.account.autorization.ViewModelAutorization
import com.example.words.ui.account.categories.ScreenCategories
import com.example.words.ui.account.categories.ViewModelCategories
import com.example.words.ui.account.learning.ScreenLearning
import com.example.words.ui.account.learning.ViewModelLearning
import com.example.words.ui.account.settings.ScreenSettings
import com.example.words.ui.account.settings.ScreenSettingsViewModel
import com.example.words.ui.account.words.ScreenWords
import com.example.words.ui.account.words.ViewModelWords
import com.example.words.ui.welcome.ScreenWelcome

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(
    navController: NavHostController,
    viewModelSettings: ScreenSettingsViewModel,
    context: Context
) {

    val viewModelWords = ViewModelWords()
    val viewModelCategories = ViewModelCategories()
    val viewModelAutorization = ViewModelAutorization(navController)
    val viewModelLearning = ViewModelLearning()
//    val viewModelSettings = ScreenSettingsViewModel()

    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    Scaffold(
        bottomBar = {
            if (
                currentRoute != ScreenRoute.ScreenWelcome.name &&
                currentRoute != ScreenRoute.ScreenLoginIn.name &&
                currentRoute != ScreenRoute.ScreenLoginOut.name
                ) {
                BottomNavigation(navController = navController)
            }
        }
    ) {
        NavHost(navController = navController, startDestination = ScreenRoute.ScreenWelcome.name) {
            composable(ScreenRoute.ScreenWelcome.name) {
                ScreenWelcome(navController = navController)
            }
            composable(ScreenRoute.ScreenCategories.name) {
                ScreenCategories(
                    navController = navController,
                    viewModel = viewModelCategories,
                    context = context
                )
            }
            composable(ScreenRoute.ScreenLoginIn.name) {
                SignIn(
                    navController,
                    viewModelAutorization,
                    context
                )
            }
            composable(ScreenRoute.ScreenLoginOut.name) {
                SignUp(
                    navController,
                    viewModelAutorization,
                    context
                )
            }
            composable(ScreenRoute.ScreenWords.name + "/{categories.id}/{categories.category_name}") {backStackEntry ->
                val categoryId = backStackEntry.arguments?.getString("categories.id")
                val categoryName = backStackEntry.arguments?.getString("categories.category_name")
                Log.d("MyLog", categoryId.toString())
                ScreenWords(
                    viewModel = viewModelWords,
                    viewModelCategories = viewModelCategories,
                    categoryId = categoryId?.toInt() ?: 0,
                    categoryName = categoryName ?: "",
                    navController = navController,
                    context = context
                )
            }
            composable(ScreenRoute.ScreenLearning.name) {
                ScreenLearning(
                    viewModelLearning = viewModelLearning,
                    viewModelCategories = viewModelCategories,
                    viewModelWords = viewModelWords,
                    context = context
                )
            }
            composable(ScreenRoute.ScreenSettings.name) {
                ScreenSettings(
                    viewModelSettings = viewModelSettings,
                    viewModelCategories =  viewModelCategories,
                    navController = navController,
                    context = context
                )
            }
        }
    }
}