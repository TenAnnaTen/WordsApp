package com.example.words.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation(
    navController: NavController
) {
//    val listItems = listOf(
//
//    )
//    NavigationBar {
//        val backStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = backStackEntry?.destination?.route
//        listItems.forEach { item ->
//            NavigationBarItem(
//                selected = currentRoute == item.route,
//                onClick = {
//                    navController.navigate(item.route)
//                },
//                icon = {
//                    Icon(
//                        painter = painterResource(id = item.iconId),
//                        contentDescription = null,
//                        modifier = Modifier.size(50.dp)
//                    )
//                }
//            )
//        }
//    }
}