package com.example.words.ui.account.categories

import com.example.words.data.model.Categories

data class CategoriesUiState(
    var list: MutableList<Categories> = mutableListOf(),
    var list2: MutableList<Categories> = mutableListOf()
)
