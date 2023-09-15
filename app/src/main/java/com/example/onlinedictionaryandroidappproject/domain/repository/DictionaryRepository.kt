package com.example.onlinedictionaryandroidappproject.domain.repository

import com.example.onlinedictionaryandroidappproject.common.Resource
import com.example.onlinedictionaryandroidappproject.domain.models.WordDomainModel
import kotlinx.coroutines.flow.Flow


interface DictionaryRepository {

    fun getWord(word: String): Flow<Resource<List<WordDomainModel>>>

    fun getAudio(audioURL: String): Resource<String>

}