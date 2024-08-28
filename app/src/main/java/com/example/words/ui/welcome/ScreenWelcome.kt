package com.example.words.ui.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.compose.primaryLight
import com.example.words.R
import com.example.words.data.storage.AccountStorage
import com.example.words.ui.navigation.ScreenRoute

@Composable
fun ScreenWelcome(
    navController: NavHostController
) {
    val accountStorage = AccountStorage()

    if (accountStorage.getUserId() != 0) {
        navController.navigate(ScreenRoute.ScreenCategories.name)
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = primaryLight)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp)
                    )
                    .weight(2f)
            ) {
                ButtonAutor(
                    text = stringResource(id = R.string.loginIn),
                    toDo = { navController.navigate(ScreenRoute.ScreenLoginIn.name) }
                )
                Spacer(modifier = Modifier.height(30.dp))
                ButtonAutor(
                    text = stringResource(id = R.string.loginOut),
                    toDo = { navController.navigate(ScreenRoute.ScreenLoginOut.name) }
                )
            }
        }
    }
}

@Composable
private fun ButtonAutor(
    text: String,
    toDo: () -> Unit
) {
    Button(
        onClick = { toDo() },
        colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
        modifier = Modifier
            .width(340.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ScreenWelcome(navController = rememberNavController())
//}
