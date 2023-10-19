package com.example.onlinedictionaryandroidappproject.domain.models

import com.example.onlinedictionaryandroidappproject.data.storage.models.DefinitionsStorageModel

data class MeaningsDomainModel(
    var partOfSpeech: String? = null,
    var definitions: List<DefinitionsDomainModel> = listOf(),
    var synonyms: List<String> = listOf(),
    var antonyms: List<String> = listOf()
)
