package com.example.words.ui.views

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.compose.inversePrimaryLight
import com.example.compose.primaryLight
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
    context: Context
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
                    viewModel.getUsersOfCategories(categories.id, context)
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
                viewModel.delCategory(categories.id!!, context)
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
    viewModel: ViewModelWords,
    categoryId: Int,
    context: Context
) {
    val isExpanded = viewModel.expandedCards[word.id] ?: false

    Card(
        modifier = Modifier
            .width(500.dp)
            .padding(10.dp)
            .height(if (isExpanded) 500.dp else 140.dp)
            .clickable {
                viewModel.updateCard(word.id!!)
//                viewModel.getWordsOfCategory(categoryId, context)
            }
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
                        word.id!!,
                        categoryId,
                        context
                    )
                } else {
                    UpdateFieldWord(
                        word.id!!,
                        viewModel,
                        categoryId,
                        word.main_language,
                        word.second_language!!,
                        word.transcription!!,
                        context
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
            color = primaryLight,
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.CenterEnd)
        )
        CircleWithInitial(
            initial = initial,
            color = inversePrimaryLight,
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
    color: Color
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
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
private fun EditTextWords(
    text: String,
    onChange: (String) -> Unit,
    @StringRes label: Int,
    placeholder: String?,
    imeAction: ImeAction
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        modifier = Modifier.width(280.dp)
    )
}

@Composable
private fun NewWordField(
    viewModel: ViewModelWords,
    wordId: Int, categoryId: Int,
    context: Context
) {
    Column {
        ButtonRow(
            wordId,
            viewModel,
            categoryId,
            isUpdate = false,
            context
        )
        EditList(
            viewModel,
            "",
            "",
            "",
            context
        )
    }
}

@Composable
private fun UpdateFieldWord(
    wordId: Int,
    viewModel: ViewModelWords,
    categoryId: Int,
    placeholder1: String?,
    placeholder2: String?,
    placeholder3: String?,
    context: Context
) {
    Column {
        ButtonRow(
            wordId,
            viewModel,
            categoryId,
            isUpdate = true,
            context
        )
        EditList(
            viewModel,
            placeholder1,
            placeholder2,
            placeholder3,
            context
        )
    }
}

@Composable
private fun ButtonRow(
    wordId: Int,
    viewModel: ViewModelWords,
    categoryId: Int,
    isUpdate: Boolean,
    context: Context
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            if (isUpdate) {
                Button(
                    onClick = {
                        viewModel.updateWord(wordId, categoryId, context)
//                        viewModel.getWordsOfCategory(categoryId)
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .width(200.dp)
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.update))
                }
                Button(
                    onClick = {
                        viewModel.deleteWord(wordId, categoryId, context)
//                        viewModel.getWordsOfCategory(categoryId, context)
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .width(200.dp)
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.delete))
                }
            } else {
                Button(
                    onClick = {
                        viewModel.updateWord(wordId, categoryId, context)
//                        viewModel.getWordsOfCategory(categoryId)
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.save))
                }
                Button(
                    onClick = {
                        viewModel.deleteWord(wordId, categoryId, context)
//                        viewModel.getWordsOfCategory(categoryId, context)
                    },
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
    context: Context
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            EditTextWords(
                text = viewModel.mainLanguage,
                onChange = {
                    if (it.replace(" ", "").isEmpty()){
                        Toast.makeText(context, "Слово не может быть пустым", Toast.LENGTH_LONG).show()
                    } else {
                        viewModel.updateEnterMainLanguage(it)
                    }
                           },
                label = R.string.enterMainLanguage,
                placeholder = placeholder1,
                imeAction = ImeAction.Next
            )
            EditTextWords(
                text = viewModel.secondLanguage,
                onChange = {
                    if (it.replace(" ", "").isEmpty()){
                        Toast.makeText(context, "Перевод не может быть пустым", Toast.LENGTH_LONG).show()
                    } else {
                        viewModel.updateEnterSecondLanguage(it)
                    }
                           },
                label = R.string.enterSecondLanguage,
                placeholder = placeholder2,
                imeAction = ImeAction.Next
            )
            EditTextWords(
                text = viewModel.transcription,
                onChange = { viewModel.updateTranscription(it) },
                label = R.string.enterTranscription,
                placeholder = placeholder3,
                imeAction = ImeAction.Done
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    WordsCard(word = Word(1, "Watermelon", "Арбуз", "Ватермелон"), viewModel = ViewModelWords())
//}