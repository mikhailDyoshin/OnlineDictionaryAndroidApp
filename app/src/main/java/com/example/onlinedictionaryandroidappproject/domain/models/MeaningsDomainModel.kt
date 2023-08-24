package com.example.onlinedictionaryandroidappproject.domain.models

import com.example.onlinedictionaryandroidappproject.data.storage.models.DefinitionsStorageModel

data class MeaningsDomainModel(
    var partOfSpeech: String? = null,
    var definitions: MutableList<DefinitionsDomainModel> = mutableListOf(),
    var synonyms: MutableList<String> = mutableListOf(),
    var antonyms: MutableList<String> = mutableListOf()
)
