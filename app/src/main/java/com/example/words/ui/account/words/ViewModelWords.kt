package com.example.words.ui.account.words

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.words.data.model.Word
import com.example.words.data.repository.WordsRepository
import kotlinx.coroutines.launch

class ViewModelWords: ViewModel() {

    val wordsRepository = WordsRepository()

    var wordsListResponse: MutableList<Word> by mutableStateOf(mutableListOf())

    private val _expandedCards = mutableStateMapOf<Int, Boolean>()
    val expandedCards = _expandedCards

    var dialog by mutableStateOf(false)
        private set

    var mainLanguage by mutableStateOf("")
        private set

    var secondLanguage by mutableStateOf("")
        private set

    var transcription by mutableStateOf("")
        private set
    fun getWordsOfCategory(categoryId: Int) {
        viewModelScope.launch {
            try {
                wordsListResponse = wordsRepository.getWordsOfCategory(categoryId)
//                Log.d("MyLog", wordsListResponse.toString() + "wr")
//                Log.d("MyLog", wordsRepository.getWordsOfCategory(categoryId).toString() + "get")
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
            }
        }
    }

    fun addWord(categoryId: Int){
        viewModelScope.launch {
            try {
                val response = wordsRepository.addWord(categoryId, Word(
                    main_language = "",
                    second_language = "",
                    transcription = ""
                ))
                if (response.body()?.id != null) {
                    updateCard(response.body()?.id!!)
//                    Log.d("MyLog", "+")
                }
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
            }
            getWordsOfCategory(categoryId)
            reset()
        }
    }

    private fun reset() {
        updateEnterMainLanguage("")
        updateEnterSecondLanguage("")
        updateTranscription("")
    }

    fun updateWord(wordId: Int) {
        viewModelScope.launch {
            try {
                wordsRepository.update(
                    Word(
                        main_language = mainLanguage,
                        second_language = secondLanguage,
                        transcription = transcription
                    ),
                    wordId
                )
                updateCard(wordId)
                reset()
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
            }
        }
    }
    fun deleteWord(wordId: Int) {
        viewModelScope.launch {
            try {
                wordsRepository.deleteWord(wordId)
            } catch (e: Exception) {
                Log.d("MyLog", e.toString())
            }
        }
    }

    fun updateCard(wordId: Int) {
        _expandedCards[wordId] = _expandedCards[wordId] != true
    }

    fun updateEnterMainLanguage(string: String) {
        mainLanguage = string
    }
    fun updateEnterSecondLanguage(string: String) {
        secondLanguage = string
    }
    fun updateTranscription(string: String) {
        transcription = string
    }

    fun openCloseDialog(){
        dialog = !dialog
    }

}