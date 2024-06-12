package com.example.words.ui.views

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.words.R
import com.example.words.data.model.Categories
import com.example.words.data.model.Word
import com.example.words.ui.account.categories.ViewModelCategories
import com.example.words.ui.account.words.ViewModelWords
import com.example.words.ui.navigation.AlertDialogExample
import com.example.words.ui.navigation.ScreenRoute

@Composable
fun MyCategories(
    categories: Categories,
    viewModel: ViewModelCategories,
    navController: NavHostController,
) {

    val openAlertDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .height(140.dp)
            .width(500.dp)
            .padding(10.dp)
            .clickable {
                navController.navigate("${ScreenRoute.ScreenWords.name}/${categories.id}/${categories.category_name}")
            }
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
            Spacer(modifier = Modifier.width(30.dp))
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
                viewModel.delCategory(categories.id!!)
            },
            dialogTitle = stringResource(id = R.string.delAlert)
        )
    }
}

//@SuppressLint("StateFlowValueCalledInComposition")
//@Composable
//fun PublicCategories(
//    categories: Categories,
//    viewModel: ViewModelCategories
//) {
//
//    val openAlertDialog = remember { mutableStateOf(false) }
////    viewModel.getUserById(categories.owner_id!!)
//
//    Card(
//        modifier = Modifier
//            .height(140.dp)
//            .width(500.dp)
//            .padding(10.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(8.dp)
//        ) {
//            Column {
//                Text(
//                    text = categories.category_name!!,
//                    fontSize = 22.sp,
//                    modifier = Modifier
//                        .width(200.dp)
//                        .padding(start = 20.dp)
//                        .height(75.dp)
//                )
////                if (categories.owner_id != null) {
////                    viewModel.getUserById(categories.owner_id)
////                        Log.d("MyLog", viewModel.state.value.name.toString())
////                        Text(
////                            text = viewModel.state.value.name.toString(),
////                            fontSize = 18.sp,
////                            modifier = Modifier
////                                .width(200.dp)
////                                .padding(start = 20.dp)
////                        )
////                }
////                Text(
////                    text = categories.owner_id!!.toString(),
////                    fontSize = 18.sp,
////                    modifier = Modifier
////                        .width(200.dp)
////                        .padding(start = 20.dp)
////                )
//            }
//            Spacer(modifier = Modifier.width(45.dp))
//            Button(
//                onClick = {
//                    openAlertDialog.value = true
//                },
//                modifier = Modifier
//                    .padding(top = 20.dp)
//                    .height(35.dp)
//            ) {
//                Text(
//                    text = stringResource(id = R.string.exit),
//                    fontSize = 16.sp,
//                    modifier = Modifier
//                        .align(Alignment.Bottom)
//                )
//            }
//        }
//
//    }
//    if (openAlertDialog.value) {
//        AlertDialogExample(
//            onDismissRequest = { openAlertDialog.value = false },
//            onConfirmation = {
//                openAlertDialog.value = false
//            },
//            dialogTitle = stringResource(id = R.string.exitAlert)
//        )
//    }
//}

@Composable
fun WordsCard(
    word: Word,
    viewModel: ViewModelWords
) {
    val isExpanded = viewModel.expandedCards[word.id] ?: false

    Card(
        modifier = Modifier
            .width(500.dp)
            .padding(10.dp)
            .animateContentSize()
            .height(if (isExpanded) 500.dp else 140.dp)
            .clickable { viewModel.updateCard(word.id!!) }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            if (!isExpanded) {
                Text(
                    text = word.main_language ?: "",
                    fontSize = 22.sp
                )
            } else {
                if (word.main_language.isNullOrEmpty()) {
                    NewWordField(
                        viewModel,
                        word.id!!
                    )
                } else {
                    UpdateFieldWord(
                        word,
                        viewModel,
                        word.main_language,
                        word.second_language!!,
                        word.transcription!!
                    )
                }
            }
        }
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

@Composable
private fun EditTextWords(
    text: String,
    onChange: (String) -> Unit,
    @StringRes label: Int,
    placeholder: String?
) {
    OutlinedTextField(
        value = text,
        label = {
            Text(text = stringResource(id = label))
        },
        placeholder = {
            Text(text = placeholder.toString())
        },
        onValueChange = { onChange(it) },
        modifier = Modifier.width(280.dp)
    )
}

@Composable
private fun NewWordField(viewModel: ViewModelWords, wordId: Int) {
    Column {
        ButtonRow(
            saveAction = {
                         viewModel.updateWord(wordId)
                         },
            cancelAction = { /*TODO*/ },
            isUpdate = false
        )
        EditList(
            viewModel,
            "",
            "",
            ""
        )
    }
}

@Composable
private fun UpdateFieldWord(
    word: Word,
    viewModel: ViewModelWords,
    placeholder1: String?,
    placeholder2: String?,
    placeholder3: String?,
) {
    Column {
        ButtonRow(
            saveAction = { /*TODO*/ },
            cancelAction = { /*TODO*/ },
            isUpdate = true
        )
        EditList(
            viewModel,
            placeholder1,
            placeholder2,
            placeholder3
        )
    }
}

@Composable
private fun ButtonRow(
    saveAction: () -> Unit = {},
    cancelAction: () -> Unit = {},
    updateAction: () -> Unit = {},
    deleteAction: () -> Unit = {},
    isUpdate: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            if (isUpdate) {
                Button(
                    onClick = updateAction,
                    modifier = Modifier
                        .height(50.dp)
                        .width(200.dp)
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.update))
                }
                Button(
                    onClick = deleteAction,
                    modifier = Modifier
                        .height(50.dp)
                        .width(200.dp)
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.delete))
                }
            } else {
                Button(
                    onClick = saveAction,
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.save))
                }
                Button(
                    onClick = cancelAction,
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.otmena))
                }
            }
        }
    }
}

@Composable
private fun EditList(
    viewModel: ViewModelWords,
    placeholder1: String?,
    placeholder2: String?,
    placeholder3: String?,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            EditTextWords(
                text = viewModel.mainLanguage,
                onChange = { viewModel.updateEnterMainLanguage(it) },
                label = R.string.enterMainLanguage,
                placeholder = placeholder1
            )
            EditTextWords(
                text = viewModel.secondLanguage,
                onChange = { viewModel.updateEnterSecondLanguage(it) },
                label = R.string.enterSecondLanguage,
                placeholder = placeholder2
            )
            EditTextWords(
                text = viewModel.transcription,
                onChange = { viewModel.updateTranscription(it) },
                label = R.string.enterTranscription,
                placeholder = placeholder3
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WordsCard(word = Word(1, "Watermelon", "Арбуз", "Ватермелон"), viewModel = ViewModelWords())
}