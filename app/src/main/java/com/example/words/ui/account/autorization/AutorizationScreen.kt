package com.example.words.ui.account.autorization

import android.widget.EditText
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.words.R
import com.example.words.data.model.User
import com.example.words.ui.navigation.ScreenRoute

@Composable
fun SignIn(
    navController: NavHostController,
    viewModel: ViewModelAutorization = androidx.lifecycle.viewmodel.compose.viewModel()
){

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 30.sp
        )
        Spacer(
            modifier = Modifier
                .height(100.dp)
        )
        EditText(
            text = viewModel.username,
            label = R.string.login,
            onChange = { viewModel.updateEnterUsername(it) }
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        EditText(
            text = viewModel.password,
            label = R.string.password,
            onChange = { viewModel.updateEnterPassword(it) }
        )
        Spacer(
            modifier = Modifier
                .height(100.dp)
        )
        Button(
            onClick = {
            viewModel.signIn(
                User(
                    username = viewModel.username,
                    password = viewModel.password
                )
            )
        },
            modifier = Modifier.width(340.dp)
        ) {
            Text(text = stringResource(id = R.string.loginIn))
        }
        Text(
            text = stringResource(id = R.string.loginOut),
            modifier = Modifier.clickable {
                navController.navigate(ScreenRoute.ScreenLoginOut.name)
            }
        )
    }
}

@Composable
fun SignUp(
    navController: NavHostController,
    viewModel: ViewModelAutorization = androidx.lifecycle.viewmodel.compose.viewModel()
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 30.sp
        )
        Spacer(
            modifier = Modifier
                .height(100.dp)
        )
        EditText(
            text = viewModel.username,
            label = R.string.login,
            onChange = {viewModel.updateEnterUsername(it)}
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        EditText(
            text = viewModel.name,
            label = R.string.name,
            onChange = {viewModel.updateEnterName(it)}
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        EditText(
            text = viewModel.password,
            label = R.string.password,
            onChange = {viewModel.updateEnterPassword(it)}
        )
        Spacer(
            modifier = Modifier
                .height(100.dp)
        )
        Button(onClick = {
            viewModel.signUp(
                User(
                    username = viewModel.username,
                    name = viewModel.name,
                    password = viewModel.password
                )
            )
        }) {
            Text(text = stringResource(id = R.string.loginOut))
        }
        Text(
            text = stringResource(id = R.string.loginIn),
            modifier = Modifier.clickable {
                navController.navigate(ScreenRoute.ScreenLoginOut.name)
            }
        )
    }
}

@Composable
private fun EditText(
    text: String,
    onChange: (String) -> Unit,
    @StringRes label: Int
){
    OutlinedTextField(
        value = text,
        label = {
                Text(text = stringResource(id = label))
        },
        onValueChange = { onChange(it) },
        modifier = Modifier.width(340.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SignUp(navController = rememberNavController(), viewModel = ViewModelAutorization(
        rememberNavController())
    )
}