package com.example.words.ui.account.autorization

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.words.R
import com.example.words.data.model.User
import com.example.words.ui.navigation.ScreenRoute

@Composable
fun SignIn(
    navController: NavHostController,
    viewModel: ViewModelAutorization,
    context: Context
) {

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
            onChange = { viewModel.updateEnterUsername(it) },
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        EditTextPassword(
            text = viewModel.password,
            label = R.string.password,
            onChange = { viewModel.updateEnterPassword(it) },
            visualTransformation = if (viewModel.showPassword)
                VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            viewModel = viewModel
        )
        Spacer(
            modifier = Modifier
                .height(100.dp)
        )
        Button(
            onClick = {
                if (viewModel.username.replace(" ", "").isEmpty() || viewModel.password.replace(
                        " ",
                        ""
                    ).isEmpty()
                ) {
                    Toast.makeText(context, "Заполните все поля", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.signIn(
                        User(
                            username = viewModel.username,
                            password = viewModel.password
                        ),
                        context
                    )
                }
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
    viewModel: ViewModelAutorization,
    context: Context
) {

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
            onChange = { viewModel.updateEnterUsername(it) },
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        EditText(
            text = viewModel.name,
            label = R.string.name,
            onChange = { viewModel.updateEnterName(it) },
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        EditTextPassword(
            text = viewModel.password,
            label = R.string.password,
            onChange = { viewModel.updateEnterPassword(it) },
            visualTransformation = if (viewModel.showPassword)
                VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            viewModel = viewModel
        )
        Spacer(
            modifier = Modifier
                .height(100.dp)
        )
        Button(onClick = {
            if (viewModel.username.replace(" ", "").isEmpty() || viewModel.name.replace(" ", "")
                    .isEmpty() || viewModel.password.replace(" ", "").isEmpty()
            ) {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_LONG).show()
            } else {
                viewModel.signUp(
                    User(
                        username = viewModel.username,
                        name = viewModel.name,
                        password = viewModel.password
                    ),
                    context
                )
            }
        }) {
            Text(text = stringResource(id = R.string.loginOut))
        }
        Text(
            text = stringResource(id = R.string.loginIn),
            modifier = Modifier.clickable {
                navController.navigate(ScreenRoute.ScreenLoginIn.name)
            }
        )
    }
}

@Composable
private fun EditText(
    text: String,
    onChange: (String) -> Unit,
    @StringRes label: Int,
    keyboardType: KeyboardType,
    imeAction: ImeAction
) {
    OutlinedTextField(
        value = text,
        label = {
            Text(text = stringResource(id = label))
        },
        singleLine = true,
        onValueChange = { onChange(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        modifier = Modifier.width(340.dp)
    )
}

@Composable
private fun EditTextPassword(
    text: String,
    onChange: (String) -> Unit,
    @StringRes label: Int,
    visualTransformation: VisualTransformation,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    viewModel: ViewModelAutorization
) {
    OutlinedTextField(
        value = text,
        label = {
            Text(text = stringResource(id = label))
        },
        singleLine = true,
        onValueChange = { onChange(it) },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        trailingIcon = {
            if (viewModel.showPassword) {
                IconButton(onClick = { viewModel.showPassword(false) }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = stringResource(id = R.string.hidePassword)
                    )
                }
            } else {
                IconButton(onClick = { viewModel.showPassword(true) }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(id = R.string.hidePassword)
                    )
                }
            }
        },
        modifier = Modifier.width(340.dp)
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    SignUp(navController = rememberNavController(), viewModel = ViewModelAutorization(
//        rememberNavController())
//    )
//}