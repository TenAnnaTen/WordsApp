package com.example.words.ui.account.categories

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.words.R
import com.example.words.data.model.Categories
import com.example.words.ui.account.words.ViewModelWords
import com.example.words.ui.navigation.DialogWithEditField
import com.example.words.ui.views.ListWithCategories

@Composable
fun ScreenCategories(
    modifier: Modifier = Modifier,
    viewModel: ViewModelCategories = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController
) {

    val listCategories = viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.categories_title),
            fontSize = 26.sp,
            modifier = modifier
                .padding(vertical = 20.dp)
        )
//        TabRowScreen(
//            tabTitles = mutableListOf(stringResource(id = R.string.my_categories), stringResource(id = R.string.public_categories)),
//            selectedTabIndex = viewModel.selectedTabIndex,
//            onClick = { viewModel.switchList() },
//        )
//        if (!viewModel.selectedTabIndex) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(10.dp)
        ) {
            ListWithCategories(
                categoriesList = listCategories.value.list,
                viewModel,
                navController
            )
            viewModel.getMyCategories()
        }
        Button(
            onClick = {
                viewModel.openDialog()
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
//        } else {
//            ListWithPublicCategories(
//                categoriesList = viewModel.categoriesListResponse,
//                viewModel
//            )
//            viewModel.getPublicCategories()
//        }
    }
    if (viewModel.openAlertDialog) {
        DialogWithEditField(
            onDismissRequest = { viewModel.openDialog() },
            onConfirmation = {
                viewModel.openDialog()
                viewModel.addCategory(
                    Categories(
                        category_name = viewModel.enter
                    )
                )
                Log.d("MyLog", viewModel.enter)
//                viewModel.updateEnter("")
            },
            viewModel = viewModel,
            text = stringResource(id = R.string.enterNameCategory)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ScreenCategories()
//}