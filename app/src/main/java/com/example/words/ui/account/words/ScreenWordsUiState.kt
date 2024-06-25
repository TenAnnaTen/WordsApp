package com.example.words.ui.account.words

import android.provider.UserDictionary.Words
import com.example.words.data.model.Word

data class ScreenWordsUiState(
    val title: String = "",
    val listWords: MutableList<Word> = mutableListOf()
)
