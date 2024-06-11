package com.example.words.ui.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(300.dp))
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

@Composable
private fun ButtonAutor(
    text: String,
    toDo: () -> Unit
){
    Button(
        onClick = { toDo() },
        modifier = Modifier
            .width(340.dp)
    ) {
        Text(text = text)
    }
}
