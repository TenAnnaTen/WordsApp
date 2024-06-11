package com.example.words.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.words.ui.account.autorization.SignIn
import com.example.words.ui.account.autorization.SignUp
import com.example.words.ui.account.autorization.ViewModelAutorization
import com.example.words.ui.account.categories.ScreenCategories
import com.example.words.ui.welcome.ScreenWelcome

@Composable
fun Navigation(
    navController: NavHostController
){
    NavHost(navController = navController, startDestination = ScreenRoute.ScreenWelcome.name){
        composable(ScreenRoute.ScreenWelcome.name){
            ScreenWelcome(navController = navController)
        }
        composable(ScreenRoute.ScreenCategories.name){
            ScreenCategories()
        }
        composable(ScreenRoute.ScreenLoginIn.name){
            SignIn(
                navController,
                ViewModelAutorization(navController)
            )
        }
        composable(ScreenRoute.ScreenLoginOut.name){
            SignUp(
                navController,
                ViewModelAutorization(navController)
            )
        }
    }
}