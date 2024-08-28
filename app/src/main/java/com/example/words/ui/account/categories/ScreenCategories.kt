package com.example.words.ui.account.categories

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.compose.primaryLight
import com.example.words.R
import com.example.words.data.model.Categories
import com.example.words.ui.navigation.DialogWithEditField
import com.example.words.ui.navigation.TabRowScreen
import com.example.words.ui.views.ListWithCategories
import com.example.words.ui.views.ListWithPublicCategories

@Composable
fun ScreenCategories(
    modifier: Modifier = Modifier,
    viewModel: ViewModelCategories,
    navController: NavHostController,
    context: Context
) {

    val listCategories = viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    primaryLight,
                    shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                )
                .fillMaxWidth()
        ){
            Text(
                text = stringResource(id = R.string.categories_title),
                fontSize = 24.sp,
                color = Color.White,
                modifier = modifier
                    .padding(vertical = 20.dp)
            )
        }
        TabRowScreen(
            tabTitles = mutableListOf(
                stringResource(id = R.string.my_categories),
                stringResource(id = R.string.public_categories)
            ),
            selectedTabIndex = viewModel.selectedTabIndex,
            onClick = { viewModel.switchList() },
        )
        if (!viewModel.selectedTabIndex) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(580.dp)
                    .padding(10.dp)
            ) {
                if (listCategories.value.list.isEmpty()){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = stringResource(id = R.string.noneCategories))
                    }
                }
                ListWithCategories(
                    categoriesList = listCategories.value.list,
                    viewModel,
                    navController,
                    context
                )
                viewModel.getMyCategories(context)
            }
            Button(
                onClick = {
                    viewModel.openDialog()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryLight
                )
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
                    .height(580.dp)
                    .padding(10.dp)
            ) {
                if (listCategories.value.list2.isEmpty()){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = stringResource(id = R.string.nonePublicCategories))
                    }
                }
                ListWithPublicCategories(
                    categoriesList = listCategories.value.list2,
                    viewModel,
                    context,
                    navController
                )
                viewModel.getPublicCategories()
            }
        }
        if (viewModel.openAlertDialog) {
            DialogWithEditField(
                onDismissRequest = {
                    viewModel.openDialog()
                    viewModel.updateEnter("")
                                   },
                onConfirmation = {
                    if (viewModel.enter.replace(" ", "").isEmpty()) {
                        Toast.makeText(context, "Название не может быть пустым", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        viewModel.openDialog()
                        viewModel.addCategory(
                            Categories(
                                category_name = viewModel.enter
                            ),
                            context
                        )
                        viewModel.updateEnter("")
                    }
                },
                viewModel = viewModel,
                text = stringResource(id = R.string.enterNameCategory)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ScreenCategories(Modifier, ViewModelCategories(), rememberNavController())
//}