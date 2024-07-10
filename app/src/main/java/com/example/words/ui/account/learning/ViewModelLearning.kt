package com.example.words.ui.account.learning

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.words.data.model.Categories
import com.example.words.data.model.Word
import com.example.words.ui.account.categories.ViewModelCategories
import com.example.words.ui.account.words.ViewModelWords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelLearning : ViewModel() {

    var isFlipped = mutableStateOf(false)

    private var hasWordSelected = false

    fun updateIsFlipped() {
        isFlipped.value = !isFlipped.value
    }

    private var _uiState = MutableStateFlow(ScreenLearningUiState())
    val uiState = _uiState.asStateFlow()

    fun getLearningWord(
        viewModelCategories: ViewModelCategories,
        viewModelWords: ViewModelWords,
        context: Context
    ) {
        if (hasWordSelected) return

        viewModelScope.launch {
            try {
                viewModelCategories.getPublicCategories()
                val categoriesList = mutableListOf<Categories>().apply {
                    addAll(viewModelCategories.uiState.value.list)
                    addAll(viewModelCategories.uiState.value.list2)
                }
                if (categoriesList.isNotEmpty()) {
                    changeWord(categoriesList, viewModelWords, context)
                }
            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(context, "Ошибка сети", Toast.LENGTH_LONG).show()
//                }
                Log.e("MyLog", "Error selecting word: ", e)
            }
        }
    }

    private suspend fun changeWord(
        listChange: MutableList<Categories>,
        viewModelWords: ViewModelWords,
        context: Context
    ) {
        val categoryId = listChange.random().id
        viewModelWords.getWordsOfCategory(categoryId = categoryId!!, context)
        if (viewModelWords.wordsListResponse.isNotEmpty()) {
            var a: Word
            do {
                a = viewModelWords.wordsListResponse.random()
            } while (_uiState.value.word.id == a.id && viewModelWords.wordsListResponse.size > 1)

            _uiState.value = ScreenLearningUiState(a)
            Log.d("MyLog", _uiState.value.toString())
            hasWordSelected = true
        }
    }

    fun refreshWord(
        viewModelCategories: ViewModelCategories,
        viewModelWords: ViewModelWords,
        context: Context
    ) {
        hasWordSelected = false
        getLearningWord(viewModelCategories, viewModelWords, context)
    }
}
