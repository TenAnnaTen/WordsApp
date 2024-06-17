package com.example.words.ui.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.words.ui.account.learning.ScreenLearning
import com.example.words.ui.account.learning.ViewModelLearning

@Composable
fun BottomNavigation(
    navController: NavController
) {
    val listItems = listOf(
        BottomBarItem.Screen1,
        BottomBarItem.Screen2,
        BottomBarItem.Screen3
    )
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .border(width = 2.dp, color = Color.Black, RoundedCornerShape(10.dp))
        ) {
            NavigationBar(
                containerColor = Color.Transparent,
                modifier = Modifier
                    .width(250.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route
                listItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route)
                        },
                        icon = {
                            Box(
                                modifier = Modifier
                                    .border(
                                        width = 2.dp,
                                        color = Color.Black,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = item.iconId),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(50.dp)
                                )
                            }

                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewFlippableCard() {
    BottomNavigation(navController = rememberNavController())
}