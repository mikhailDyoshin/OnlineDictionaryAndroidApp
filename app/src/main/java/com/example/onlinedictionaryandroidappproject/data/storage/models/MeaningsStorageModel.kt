package com.example.onlinedictionaryandroidappproject.data.storage.models


data class MeaningsStorageModel(
    var partOfSpeech: String? = null,
    var definitions: List<DefinitionsStorageModel> = listOf(),
    var synonyms: List<String> = listOf(),
    var antonyms: List<String> = listOf()
)
