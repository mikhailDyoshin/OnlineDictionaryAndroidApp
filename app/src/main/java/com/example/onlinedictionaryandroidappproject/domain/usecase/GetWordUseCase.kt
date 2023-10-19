package com.example.onlinedictionaryandroidappproject.domain.usecase

import com.example.onlinedictionaryandroidappproject.common.Resource
import com.example.onlinedictionaryandroidappproject.domain.models.WordDomainModel
import com.example.onlinedictionaryandroidappproject.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordUseCase @Inject constructor(private val repository: DictionaryRepository) {

    fun execute(word: String): Flow<Resource<List<WordDomainModel>>> {
        return repository.getWord(word = word)
    }

}