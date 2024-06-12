package com.example.words.ui.account.categories

import com.example.words.data.model.Categories

data class CategoriesUiState(
    val list: MutableList<Categories> = mutableListOf()
)
