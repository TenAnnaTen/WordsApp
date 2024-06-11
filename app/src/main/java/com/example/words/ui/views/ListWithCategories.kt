package com.example.words.ui.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.words.data.model.Categories
import com.example.words.data.model.User
import com.example.words.ui.account.categories.ViewModelCategories

@Composable
fun ListWithCategories(
    categoriesList: MutableList<Categories>,
    viewModel: ViewModelCategories
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 8.dp)
    ){
        itemsIndexed(items = categoriesList){index, item ->
            MyCategories(categories = item, viewModel)
        }
    }
}

@Composable
fun ListWithPublicCategories(
    categoriesList: MutableList<Categories>,
    viewModel: ViewModelCategories
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 8.dp)
    ){
        itemsIndexed(items = categoriesList){index, item ->
            PublicCategories(categories = item, viewModel)
        }
    }
}