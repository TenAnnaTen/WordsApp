package com.example.words.ui.account.words

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.words.R
import com.example.words.data.model.Categories
import com.example.words.data.storage.AccountStorage
import com.example.words.ui.account.categories.ViewModelCategories
import com.example.words.ui.navigation.DialogWithEditField
import com.example.words.ui.navigation.TabRowScreen
import com.example.words.ui.views.ListWithUsers
import com.example.words.ui.views.ListWithWords

@Composable
fun ScreenWords(
    modifier: Modifier = Modifier,
    viewModel: ViewModelWords,
    viewModelCategories: ViewModelCategories,
    categoryId: Int,
    categoryName: String,
    ownerId: Int,
    navController: NavHostController,
    context: Context
) {

    val viewModelState = viewModelCategories.wordUiState.collectAsState()
    val listWords = viewModel.uiState.collectAsState()

    val accountStorage = AccountStorage()

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            IconButton(
                onClick = {
                    navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = modifier
                        .size(35.dp)
                )
            }
            Spacer(modifier = modifier.width(90.dp))
            Text(
                text = if (viewModelState.value.title.isNotEmpty()) viewModelState.value.title else categoryName,
                fontSize = 26.sp,
                modifier = modifier
                    .padding(vertical = 20.dp)
                    .clickable {
                        viewModel.openCloseDialog()
                    }
            )
        }
        TabRowScreen(
            tabTitles = mutableListOf(
                stringResource(id = R.string.words),
                stringResource(id = R.string.people)
            ),
            selectedTabIndex = viewModel.selectedTabIndex,
            onClick = { viewModel.switchList() },
        )
        if (!viewModel.selectedTabIndex) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(550.dp)
                    .padding(10.dp)
            ) {
                if (listWords.value.listWords.isEmpty()){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = stringResource(id = R.string.noneWord))
                    }
                }
                ListWithWords(
                    wordsList = listWords.value.listWords,
                    viewModel = viewModel,
                    categoryId,
                    context
                )
                viewModel.getWordsOfCategory(categoryId, context)
            }
            Button(
                onClick = {
                    viewModel.addWord(categoryId, context)
                },
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(id = R.string.add_categories),
                        fontSize = 16.sp
                    )
                }
            }
        } else {
            if (ownerId == accountStorage.getUserId()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(550.dp)
                        .padding(10.dp)
                ) {
                    ListWithUsers(
                        usersList = viewModelState.value.listUsers,
                        ownerId = ownerId,
                        categoryId = categoryId,
                        viewModel = viewModelCategories,
                        context = context
                    )
                    viewModelCategories.getUsersOfCategories(categoryId, context)
                }
                Button(
                    onClick = {
                        viewModel.openCloseDialogByUsers()
                    },
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(id = R.string.add_categories),
                            fontSize = 16.sp
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(550.dp)
                        .padding(10.dp)
                ) {
                    ListWithUsers(
                        usersList = viewModelState.value.listUsers,
                        ownerId = ownerId,
                        categoryId = categoryId,
                        viewModel = viewModelCategories,
                        context = context
                    )
                    viewModelCategories.getUsersOfCategories(categoryId, context)
                }
            }
        }
    }
    if (viewModel.dialog) {
        DialogWithEditField(
            onDismissRequest = {
                viewModel.openCloseDialog()
                viewModelCategories.updateEnter("")
                },
            onConfirmation = {
                val categoryNam = viewModelCategories.enter
                if (categoryNam.replace(" ", "").isNotEmpty()) {
                    viewModel.openCloseDialog()
                    viewModelCategories.updateCategoryName(
                        categoryId,
                        Categories(category_name = categoryNam),
                        context
                    )
                    viewModelCategories.updateEnter("")
                } else {
                    Toast.makeText(context, "Название не может быть пустым", Toast.LENGTH_LONG).show()
                }
            },
            viewModel = viewModelCategories,
            text = stringResource(id = R.string.updateNameCategory)
        )
    }
    if (viewModel.dialogByUsersAdd) {
        DialogWithEditField(
            onDismissRequest = {
                viewModel.openCloseDialogByUsers()
                viewModelCategories.updateEnter("")
                },
            onConfirmation = {
                val userId = viewModelCategories.enter
                if (userId.replace(" ", "").isNotEmpty() && userId.replace(" ", "").isDigitsOnly()) {
                    viewModel.openCloseDialogByUsers()
                    viewModelCategories.addUserByCategory(
                        categoryId,
                        userId.replace(" ", "").toInt(),
                        context
                    )
                } else {
                    Toast.makeText(context, "Неверный формат ввода", Toast.LENGTH_LONG).show()
                }
            },
            viewModel = viewModelCategories,
            text = stringResource(id = R.string.addUser)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ScreenWords()
//}