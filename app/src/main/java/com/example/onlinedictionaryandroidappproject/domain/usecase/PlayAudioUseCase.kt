package com.example.onlinedictionaryandroidappproject.domain.usecase

import com.example.onlinedictionaryandroidappproject.common.Resource
import com.example.onlinedictionaryandroidappproject.domain.models.AudioDomainModel
import com.example.onlinedictionaryandroidappproject.domain.models.WordDomainModel
import com.example.onlinedictionaryandroidappproject.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayAudioUseCase @Inject constructor(private val repository: DictionaryRepository) {

    fun execute(audio: String): Resource<String> {
        return repository.getAudio(audioURL = audio)
    }
}