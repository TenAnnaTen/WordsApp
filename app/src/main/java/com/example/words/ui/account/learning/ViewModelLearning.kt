package com.example.words.ui.account.learning

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.words.data.model.Word
import com.example.words.ui.account.categories.ViewModelCategories
import com.example.words.ui.account.words.ViewModelWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelLearning: ViewModel() {

    var isFlipped = mutableStateOf(false)

    private var hasWordSelected = false

    fun updateIsFlipped() {
        isFlipped.value = !isFlipped.value
    }

    private var _uiState = MutableStateFlow(ScreenLearningUiState())
    val uiState = _uiState.asStateFlow()

    fun getLearningWord(
        viewModelCategories: ViewModelCategories,
        viewModelWords: ViewModelWords
    ) {

        if (hasWordSelected) return

        try {
            if (viewModelCategories.uiState.value.list.isNotEmpty()) {
                val categoryId = viewModelCategories.uiState.value.list.random().id
                viewModelWords.getWordsOfCategory(categoryId = categoryId!!)
                if (viewModelWords.wordsListResponse.isNotEmpty()) {
                    _uiState.value = ScreenLearningUiState(viewModelWords.wordsListResponse.random())
                    Log.d("MyLog", _uiState.value.toString())
                    hasWordSelected = true
                }
            }
        } catch (e: Exception) {
            Log.e("MyLog", "Error selecting word: ", e)
        }

    }

    fun refreshWord(
        viewModelCategories: ViewModelCategories,
        viewModelWords: ViewModelWords
    ) {
        hasWordSelected = false
        getLearningWord(viewModelCategories, viewModelWords)
    }

}