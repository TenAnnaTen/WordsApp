package com.example.words.ui.views

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.words.data.model.Categories
import com.example.words.data.model.Word
import com.example.words.ui.account.categories.ViewModelCategories
import com.example.words.ui.account.words.ViewModelWords

@Composable
fun ListWithCategories(
    categoriesList: MutableList<Categories>,
    viewModel: ViewModelCategories,
    navController: NavHostController,
    context: Context
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 8.dp)
    ){
        itemsIndexed(items = categoriesList){ _, item ->
            MyCategories(categories = item, viewModel, navController, context)
        }
    }
}

@Composable
fun ListWithWords(
    wordsList: MutableList<Word>,
    viewModel: ViewModelWords,
    categoryId: Int,
    context: Context
){
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 8.dp)
    ) {
            itemsIndexed(items = wordsList) {_, item ->
                WordsCard(word = item, viewModel = viewModel, categoryId, context)
            }
    }
}

//@Composable
//fun ListWithPublicCategories(
//    categoriesList: MutableList<Categories>,
//    viewModel: ViewModelCategories
//) {
//    LazyColumn(
//        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 8.dp)
//    ){
//        itemsIndexed(items = categoriesList){index, item ->
//            PublicCategories(categories = item, viewModel)
//        }
//    }
//}