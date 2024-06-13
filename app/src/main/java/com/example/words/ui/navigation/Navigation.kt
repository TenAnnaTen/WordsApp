package com.example.words.ui.navigation

import android.util.Log
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
import com.example.words.ui.account.words.ScreenWords
import com.example.words.ui.account.words.ViewModelWords
import com.example.words.ui.welcome.ScreenWelcome

@Composable
fun Navigation(
    navController: NavHostController
) {

    val viewModelWords = ViewModelWords()

    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    NavHost(navController = navController, startDestination = ScreenRoute.ScreenWelcome.name) {
        composable(ScreenRoute.ScreenWelcome.name) {
            ScreenWelcome(navController = navController)
        }
        composable(ScreenRoute.ScreenCategories.name) {
            ScreenCategories(
                navController = navController
            )
        }
        composable(ScreenRoute.ScreenLoginIn.name) {
            SignIn(
                navController,
                ViewModelAutorization(navController)
            )
        }
        composable(ScreenRoute.ScreenLoginOut.name) {
            SignUp(
                navController,
                ViewModelAutorization(navController)
            )
        }
        composable(ScreenRoute.ScreenWords.name + "/{categories.id}/{categories.category_name}") {backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categories.id")
            val categoryName = backStackEntry.arguments?.getString("categories.category_name")
            Log.d("MyLog", categoryId.toString())
            ScreenWords(
                viewModelCategories = ViewModelCategories(),
                categoryId = categoryId?.toInt() ?: 0,
                categoryName = categoryName ?: "",
                navController = navController
            )
        }
    }
}