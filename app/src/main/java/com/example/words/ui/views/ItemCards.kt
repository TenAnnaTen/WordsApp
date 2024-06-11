package com.example.words.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.words.R
import com.example.words.data.model.Categories
import com.example.words.ui.account.categories.ViewModelCategories
import com.example.words.ui.navigation.AlertDialogExample

@Composable
fun MyCategories(
    categories: Categories,
    viewModel: ViewModelCategories
) {

    val openAlertDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .height(140.dp)
            .width(500.dp)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = categories.category_name!!,
                fontSize = 22.sp,
                modifier = Modifier
                    .width(200.dp)
                    .padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.width(45.dp))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                if (categories.id != null) {
                    viewModel.getUsersOfCategories(categories.id)
                    if (viewModel.usersListResponse.isNotEmpty()) {
                        CirclesWithInitial(
                            initial = viewModel.usersListResponse.random().name!!.first().toString()
                        )
                    }
                }

                Button(
                    onClick = {
                        openAlertDialog.value = true
                    },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .height(35.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.delete),
                        fontSize = 16.sp
                    )
                }
            }
        }

    }
    if (openAlertDialog.value) {
        AlertDialogExample(
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = {
                openAlertDialog.value = false
            },
            dialogTitle = stringResource(id = R.string.delAlert)
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PublicCategories(
    categories: Categories,
    viewModel: ViewModelCategories
) {

    val openAlertDialog = remember { mutableStateOf(false) }
//    viewModel.getUserById(categories.owner_id!!)

    Card(
        modifier = Modifier
            .height(140.dp)
            .width(500.dp)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = categories.category_name!!,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .width(200.dp)
                        .padding(start = 20.dp)
                        .height(75.dp)
                )
//                if (categories.owner_id != null) {
//                    viewModel.getUserById(categories.owner_id)
//                        Log.d("MyLog", viewModel.state.value.name.toString())
//                        Text(
//                            text = viewModel.state.value.name.toString(),
//                            fontSize = 18.sp,
//                            modifier = Modifier
//                                .width(200.dp)
//                                .padding(start = 20.dp)
//                        )
//                }
//                Text(
//                    text = categories.owner_id!!.toString(),
//                    fontSize = 18.sp,
//                    modifier = Modifier
//                        .width(200.dp)
//                        .padding(start = 20.dp)
//                )
            }
            Spacer(modifier = Modifier.width(45.dp))
            Button(
                onClick = {
                    openAlertDialog.value = true
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(35.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.exit),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.Bottom)
                )
            }
        }

    }
    if (openAlertDialog.value) {
        AlertDialogExample(
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = {
                openAlertDialog.value = false
            },
            dialogTitle = stringResource(id = R.string.exitAlert)
        )
    }
}

@Composable
private fun CirclesWithInitial(initial: String) {
    Box(modifier = Modifier.size(45.dp)) {
        CircleWithInitial(
            initial = initial,
            color = androidx.compose.ui.graphics.Color.Red,
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.CenterEnd)
        )
        CircleWithInitial(
            initial = initial,
            color = androidx.compose.ui.graphics.Color.Magenta,
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.CenterStart)
        )
    }

}

@Composable
private fun CircleWithInitial(
    initial: String,
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color = color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    PublicCategories(categories = Categories(0, "Центральный процессор", "me", mutableListOf("Anna", "John", "UYTR")))
//}