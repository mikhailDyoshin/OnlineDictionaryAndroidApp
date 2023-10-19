package com.example.onlinedictionaryandroidappproject.data.storage

import com.example.onlinedictionaryandroidappproject.data.storage.models.WordStorageModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface DictionaryApi : DictionaryStorage {

    @GET
    override suspend fun getWord(@Url word: String): Response<List<WordStorageModel>>

}