package com.example.onlinedictionaryandroidappproject.data.storage

import com.example.onlinedictionaryandroidappproject.data.storage.models.WordStorageModel
import retrofit2.Response

interface DictionaryStorage {

    suspend fun getWord(word: String): Response<List<WordStorageModel>>

}