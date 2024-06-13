package com.example.words.data.repository

import com.example.words.data.RetrofitInstance
import com.example.words.data.model.Word
import retrofit2.Response

class WordsRepository {

    suspend fun getWordsOfCategory(categoryId: Int): MutableList<Word> {
        return RetrofitInstance.wordsService.getWordsOfCategory(categoryId)
    }

    suspend fun addWord(categoryId: Int, word: Word): Response<Word> {
        return RetrofitInstance.wordsService.addWord(word, categoryId)
    }

    suspend fun update(word: Word, wordId: Int) {
        return RetrofitInstance.wordsService.updateWord(word, wordId)
    }

    suspend fun deleteWord(wordId: Int){
        return RetrofitInstance.wordsService.deleteWord(wordId)
    }

}